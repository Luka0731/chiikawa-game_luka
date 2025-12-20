package renderer;

import cjsgameengine.Window;
import util.AssetPool;
import util.CJSMath;
import util.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


/**
 * DebugDraw is responsible for rendering temporary 2D lines for development and debugging purposes.
 * It uses a simple batching system to draw lines efficiently using OpenGL. It is separated to the
 * whole render system built in other classes.
 */
public class DebugDraw {
    private static final int MAX_LINES = 500;
    private static final int POSITION_SIZE = 3;
    private static final int COLOR_SIZE = 3;
    private static final int POSITION_OFFSET = 0;
    private static final int COLOR_OFFSET = POSITION_SIZE * Float.BYTES;
    private static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE;
    private static final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
    private static final int VERTEX_AMOUNT_PER_LINE = 2;
    private static final int LINE_SIZE = VERTEX_AMOUNT_PER_LINE * VERTEX_SIZE;

    private static final List<DebugDrawLine> lines = new ArrayList<>();

    private static final float[] vertexArray = new float[MAX_LINES * LINE_SIZE];
    private static final Shader shader = AssetPool.addOrGetShader("assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean wasStarted = false;

    public static void start() {
        // generate the vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create the vbo and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long)vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // enable the vertex array attributes
        glVertexAttribPointer(0, POSITION_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POSITION_OFFSET);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glLineWidth(4.0f);
    }

    public static void beginFrame() {
        if (!wasStarted) {
            start();
            wasStarted = true;
        }

        // remove deadlines
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw() {
        if (lines.size() <= 0) return;

        int index = 0;
        for (DebugDrawLine line : lines) {
            for (int i=0; i < 2; i++) {
                Vector2f position = i == 0 ? line.getFromPosition() : line.getToPosition();
                Vector3f color = line.getColor();

                // load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                // load the position
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * LINE_SIZE));

        // use our shader
        shader.useShader();
        shader.uploadMat4f("uProjection", Window.getCurrentScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getCurrentScene().getCamera().getViewMatrix());

        // bind the vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // draw the batch
        glDrawArrays(GL_LINES, 0, lines.size() * LINE_SIZE); // bresenham's line algorithm to draw this stuff

        // disable location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detachShader();
    }


    // |--- add 2D line methods ---|
    // todo: add constants for common colors

    public static void add2DLine(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
        if (lines.size() >= MAX_LINES) return;
        lines.add(new DebugDrawLine(from, to, color, lifetime));
    }

    public static void add2DLine(Vector2f from, Vector2f to, Vector3f color) {
        add2DLine(from, to, color, 1);
    }

    public static void add2DLine(Vector2f from, Vector2f to) {
        add2DLine(from, to, new Vector3f(0, 1, 0), 1);
    }


    // |--- add circle methods ---|

    public static void addCircle(Vector2f center, float radius, Vector3f color, int lifetime) {
        Vector2f[] points = new Vector2f[Settings.DebugDraw.CIRCLE_SEGMENTS];
        float increment = 360.0f / points.length;
        float currentAngle = 0;

        for (int i = 0; i < points.length; i++) {
            Vector2f tmp = new Vector2f(radius, 0);
            CJSMath.rotate(tmp, currentAngle, new Vector2f());
            points[i] = new Vector2f(tmp).add(center);

            if (i > 0) {
                add2DLine(points[i - 1], points[i], color, lifetime);
            }
            currentAngle += increment;
        }

        add2DLine(points[points.length - 1], points[0], color, lifetime);
    }

    public static void addCircle(Vector2f center, float radius, Vector3f color) {
        addCircle(center, radius, color, 1);
    }

    public static void addCircle(Vector2f center, float radius) {
        addCircle(center, radius, new Vector3f(0, 1, 0), 1);
    }


    // |--- add rectangle methods ---|

    public static void addRectangle(Vector2f center, Vector2f dimensions, float rotation, Vector3f color, int lifetime) {
        Vector2f min = new Vector2f(center).sub(new Vector2f(dimensions).div(2.0f));
        Vector2f max = new Vector2f(center).add(new Vector2f(dimensions).div(2.0f));

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, max.y), new Vector2f(max.x, min.y)
        };

        if (rotation != 0.0f) {
            for (Vector2f vertex : vertices) {
                CJSMath.rotate(vertex, rotation, center);
            }
        }

        add2DLine(vertices[0], vertices[1], color, lifetime);
        add2DLine(vertices[1], vertices[2], color, lifetime);
        add2DLine(vertices[2], vertices[3], color, lifetime);
        add2DLine(vertices[3], vertices[0], color, lifetime);
    }

    public static void addRectangle(Vector2f center, Vector2f dimensions, float rotation, Vector3f color) {
        addRectangle(center, dimensions, rotation, color, 1);
    }

    public static void addRectangle(Vector2f center, Vector2f dimensions, float rotation) {
        addRectangle(center, dimensions, rotation, new Vector3f(0, 1, 0), 1);
    }
}