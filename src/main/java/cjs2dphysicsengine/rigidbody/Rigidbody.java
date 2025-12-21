package cjs2dphysicsengine.rigidbody;

import components.Component;
import org.joml.Vector2f;


public class Rigidbody extends Component {
    private Vector2f position;
    private float rotation; // in degrees  // todo: more efficient ways then degrease

    private Vector2f linearVelocity = new Vector2f();
    private float angularVelocity = 0.0f;
    private float linearDamping = 0.0f;
    private float angularDamping = 0.0f;

    private boolean fixedRotation = false;

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

    public float getRotation() {
        return rotation;
    }

    public void setTransform(Vector2f position, float rotation) {
        this.position.set(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.position.set(position);
    }
}