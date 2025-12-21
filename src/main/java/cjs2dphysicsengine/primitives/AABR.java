package cjs2dphysicsengine.primitives;

import cjs2dphysicsengine.rigidbody.Rigidbody;
import org.joml.Vector2f;

/**
 * Axis Aligned Bounding Rectangle
 *
 * This basically means a not rotated rectangle, because it's aligned to the y and x.
 */
public class AABR {
    private Vector2f size;
    private Vector2f halfSize;
    private Rigidbody rigidbody;

    public AABR() {
        this.halfSize = new Vector2f(20).div(2.0f);
        size = new Vector2f(halfSize);
        rigidbody = new Rigidbody();
    }

    public AABR(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(size).div(2.0f);
        this.rigidbody = new Rigidbody();
    }

    public AABR(Vector2f min, Vector2f max, Rigidbody rigidbody) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(size).div(2.0f);
        this.rigidbody = rigidbody;
    }


    // |--- getters & setters ---|

    public Vector2f getMin() {
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getMax() {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Rigidbody getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(Rigidbody rigidbody) {
        this.rigidbody = rigidbody;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
        this.halfSize.set(size.x / 2.0f, size.y / 2.0f);
    }
}