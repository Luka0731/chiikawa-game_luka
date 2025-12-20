package cjs2dphysicsengine.primitives;

import org.joml.Vector2f;

public class Line {
    private final Vector2f fromPosition;
    private final Vector2f toPosition;

    public Line(Vector2f fromPosition, Vector2f toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }


    // |--- getters & setters ---|

    public Vector2f getFromPosition() {
        return fromPosition;
    }

    public Vector2f getToPosition() {
        return toPosition;
    }

    public float getLengthSquared() {
        return new Vector2f(toPosition).sub(fromPosition).lengthSquared();
    }
}
