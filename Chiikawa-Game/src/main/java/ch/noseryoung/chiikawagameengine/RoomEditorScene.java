package ch.noseryoung.chiikawagameengine;

import java.awt.event.KeyEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RoomEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    public RoomEditorScene() {}

    private float[] vertexArray = {
            // 1: position,       2: color
            0.5f,  -0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f, // bottom right
            -0.5f, 0.5f,  0.0f,   0.0f, 1.0f, 0.0f, 1.0f, // top left
            0.5f,  0.5f,  0.0f,   0.0f, 0.0f, 1.0f, 1.0f, // top right
            -0.5f, -0.5f, 0.0f,   1.0f, 0.0f, 1.0f, 1.0f  // bottom left
    };

    // important: must be counter-clockwise order
    private int[] elementArray = {
            /*
               x     x

               x     x
            */
            2, 1, 0, // top right triangle
            0, 1, 3  // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    @Override
    public void init() {
        // compile and linking the shaders

        // load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // pass the shader source code to the gpu
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);


        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // First load and compile the vertex shaderAdd commentMore actions
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Link shaders and check for errorsAdd commentMore actions
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        // |--- generating VAO, VBO and EBO and sending it to the GPU ---|
        vaoID = glGenVertexArrays(); // OpenGL its way to make a VAO and giving it an id
        glBindVertexArray(vaoID); // everything that happens after this line, will be happening to the vertex array

        // create a float buffer of vertices for OpenGL
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip(); // now putting the float buffer in and sorts it right with the flip method

        // create the vbo and upload vertex buffer
        vboID = glGenBuffers(); // making a new buffer
        glBindBuffer(GL_ARRAY_BUFFER, vboID); // sais now everything that were doing is for the buffer a line above
        // now were saying with the parameters:
        // 1: were working with an array buffer, 2: the buffer to send, 3: it won't change
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add the vertex attribute pointers (making so the gpu read the vertexArray)
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {
        glUseProgram(shaderProgram); // bind shader program
        glBindVertexArray(vaoID); // bind the VAO

        // enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0); // binde nothing

        glUseProgram(0); // don't use a program
    }
}
