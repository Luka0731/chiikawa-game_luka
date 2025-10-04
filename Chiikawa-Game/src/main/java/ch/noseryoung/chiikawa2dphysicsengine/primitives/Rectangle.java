package ch.noseryoung.chiikawa2dphysicsengine.primitives;

import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.Rigidbody;
import ch.noseryoung.util.ChiikawaMath;
import org.joml.Vector2f;

public class Rectangle {
    private Vector2f size;
    private Vector2f halfSize;
    private Rigidbody rigidbody;

    public Rectangle() {
        size = new Vector2f(-1.0f, 1.0f);
        halfSize = new Vector2f(20);
        rigidbody = new Rigidbody();
    }

    public Rectangle(Vector2f min, Vector2f max, Rigidbody rigidbody) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(size).div(2.0f);
        this.rigidbody = rigidbody;
    }


    // |--- getters & setters ---|

    public Vector2f getLocalMin() {
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y),
                new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y),
                new Vector2f(max.x, max.y)
        };
        if (rigidbody.getRotation() != 0.0f) {
            for (Vector2f vert : vertices) {
                // rotates point(Vector2f) about center(Vector2f) by rotating(float in degrees)
                ChiikawaMath.rotate(vert, rigidbody.getRotation(), rigidbody.getPosition());
            }
        }
        return vertices;
    }

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public Rigidbody getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(Rigidbody rigidbody) {
        this.rigidbody = rigidbody;
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
        halfSize.set(size.x / 2.0f, size.y / 2.0f);
    }
}