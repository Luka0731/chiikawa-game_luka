package ch.noseryoung.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * This class is a simple data model that represents a line in 2D space.
 * It stores information such as start and end points, color, and lifetimeInFrames.
 *
 * The actual rendering and management logic is handled by the DebugDraw class.
 */
public class Line2D {
    private final Vector2f fromPosition;
    private final Vector2f toPosition;
    private final Vector3f color;
    private int lifetimeInFrames;

    public Line2D(Vector2f fromPosition, Vector2f toPosition, Vector3f color, int lifetimeInFrames) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.color = color;
        this.lifetimeInFrames = lifetimeInFrames;
    }

    public int beginFrame() {
        lifetimeInFrames--;
        return lifetimeInFrames;
    }


    // |--- getters & setters ---|

    public Vector2f getFromPosition() {
        return fromPosition;
    }

    public Vector2f getToPosition() {
        return toPosition;
    }

    public Vector3f getColor() {
        return color;
    }
}