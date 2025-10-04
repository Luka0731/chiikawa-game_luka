package ch.noseryoung.chiikawa2dphysicsengine.rigidbody;

import ch.noseryoung.components.Component;
import org.joml.Vector2f;


public class Rigidbody extends Component {
    private Vector2f position;
    private float rotation; // in degrees

    private Vector2f linearVelocity;
    private float angularVelocity;
    private float linearDamping;
    private float angularDamping;

    private boolean fixedRotation;

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