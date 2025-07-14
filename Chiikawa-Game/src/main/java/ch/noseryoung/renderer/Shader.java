package ch.noseryoung.renderer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int shaderProgramID;
    private String vertexShaderSource;
    private String fragmentShaderSource;
    private String shaderFilePath;
    private boolean beingUsed = false;

    public Shader (String shaderFilePath) {
        this.shaderFilePath = shaderFilePath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(shaderFilePath)));

            // Pattern to match "#type <shaderType>" followed by shader code
            Pattern pattern = Pattern.compile("#type\\s+(vertex|fragment)\\s*(.*?)((?=#type)|\u0000)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source + "\u0000"); // add sentinel to simplify regex
            while (matcher.find()) {
                String type = matcher.group(1).trim();
                String shaderCode = matcher.group(2).trim();

                if (type.equals("vertex")) {
                    vertexShaderSource = shaderCode;
                } else if (type.equals("fragment")) {
                    fragmentShaderSource = shaderCode;
                } else {
                    throw new IOException("Unexpected shader type: '" + type + "'.");
                }
            }
            if (vertexShaderSource == null || fragmentShaderSource == null) {
                throw new IOException("Error: Both vertex and fragment shader must be defined.");
            }
        } catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open shader file '" + shaderFilePath + "'.";
        }
    }

    public void compileAndLinkShader() {
        int vertexID, fragmentID;

        // |--- compiling ---|
        // load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // pass the shader source code to the gpu
        glShaderSource(vertexID, vertexShaderSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + shaderFilePath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // first load and compile the vertex shaderAdd commentMore actions
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSource);
        glCompileShader(fragmentID);

        // check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + shaderFilePath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // |--- Linking ---|
        // link shaders and check for errorsAdd commentMore actions
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + shaderFilePath + "'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    public void useShader() {
        if (!beingUsed) {
            glUseProgram(shaderProgramID); // bind shader program
            beingUsed = true;
        }
    }

    public void detachShader() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vector4f) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        glUniform4f(varLocation, vector4f.x, vector4f.y, vector4f.z, vector4f.w);
    }

    public void uploadFloat(String varName, float value) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        glUniform1f(varLocation, value);
    }

    public void uploadInt(String varName, int value) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        glUniform1i(varLocation, value);
    }
    // todo: make more upload methods (vec3f, vec2f, mat3f, mat2f)

    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[] slots) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        useShader();
        glUniform1iv(varLocation, slots);
    }
}
