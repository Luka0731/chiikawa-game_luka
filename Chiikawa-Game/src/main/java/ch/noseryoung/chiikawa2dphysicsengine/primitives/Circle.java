package ch.noseryoung.chiikawa2dphysicsengine.primitives;

import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.Rigidbody;
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

    public void setRigidbody(Rigidbody rigidbody) {
        this.rigidbody = rigidbody;
    }
}