package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RoomEditorScene extends Scene {

    public RoomEditorScene() {}

    private float[] vertexArray = {
            // 1: position,           2: color
            100.5f,  0.5f,  0.0f,   1.0f, 0.0f, 0.0f, 1.0f, // bottom right
            0.5f,  100.5f,  0.0f,   0.0f, 1.0f, 0.0f, 1.0f, // top left
            100.5f,   100.5f,  0.0f,   0.0f, 0.0f, 1.0f, 1.0f, // top right
            0.5f, 0.5f,  0.0f,   1.0f, 0.0f, 1.0f, 1.0f  // bottom left
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

    private Shader defaultShader;

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compileAndLinkShader();

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
        camera.position.x -= dt * 50.0f;
        defaultShader.useShader();
        defaultShader.uploadMat4f("uProjectionMatrix", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uViewMatrix", camera.getViewMatrix());

        glBindVertexArray(vaoID); // bind the VAO

        // enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0); // binde nothing

        defaultShader.detachShader();
    }
}
