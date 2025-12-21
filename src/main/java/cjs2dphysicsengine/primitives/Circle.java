package cjs2dphysicsengine.primitives;

import cjs2dphysicsengine.rigidbody.Rigidbody;
import org.joml.Vector2f;

public class Circle {
    private float radius;
    private Rigidbody rigidbody;

    public Circle() {
        radius = 1.0f;
        rigidbody = new Rigidbody();
    }

    public Circle(float radius, Rigidbody rigidbody) {
        this.radius = radius;
        this.rigidbody = rigidbody;
    }

    // |--- getters & setters ---|

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }

    public void setCenter(Vector2f center) {
        this.rigidbody.setTransform(center);
    }

    public Rigidbody getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(Rigidbody rigidbody) {
        this.rigidbody = rigidbody;
    }
}