package cjs2dphysicsengine.rigidbody;

import components.Component;
import org.joml.Vector2f;


public class Rigidbody extends Component {
    private Vector2f position;
    private float rotation; // in degrees  // todo: more efficient ways then degrease

    public Rigidbody() {
        position = new Vector2f();
        rotation = 0;
    }

    public Rigidbody(Vector2f position, float rotation) {
        this.position = position;
        this.rotation = rotation;
    }


    // |--- getters & setters ---|

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}