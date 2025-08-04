package ch.noseryoung.renderer;

import ch.noseryoung.chiikawa2dphysicsengine.primitives.Line;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * This class is a simple data model that represents a line in 2D space.
 * It stores information such as start and end points, color, and lifetimeInFrames.
 *
 * The actual rendering and management logic is handled by the DebugDraw class.
 */
// todo: now that there is line in the physics engine, change stuff up to use this
public class DebugDrawLine extends Line {
    private final Vector3f color;
    private int lifetimeInFrames;

    public DebugDrawLine(Vector2f fromPosition, Vector2f toPosition, Vector3f color, int lifetimeInFrames) {
        super(fromPosition, toPosition);
        this.color = color;
        this.lifetimeInFrames = lifetimeInFrames;
    }

    public DebugDrawLine(Vector2f fromPosition, Vector2f toPosition) {
        super(fromPosition, toPosition);
        color = new Vector3f(0.0f, 0.0f, 0.0f);
        lifetimeInFrames = 1;
    }

    public int beginFrame() {
        lifetimeInFrames--;
        return lifetimeInFrames;
    }


    // |--- getters & setters ---|

    public Vector3f getColor() {
        return color;
    }
}