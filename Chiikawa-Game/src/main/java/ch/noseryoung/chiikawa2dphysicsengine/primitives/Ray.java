package ch.noseryoung.chiikawa2dphysicsengine.primitives;

import org.joml.Vector2f;

public class Ray {
    private Vector2f origin;
    private Vector2f direction;
    // todo: add maxLength

    public Ray(Vector2f origin, Vector2f direction) {
        this.origin = origin;
        this.direction = direction;
        this.direction.normalize();
    }


    // |--- getters & setters ---|

    public Vector2f getOrigin() {
        return this.origin;
    }

    public Vector2f getDirection() {
        return this.direction;
    }
}
