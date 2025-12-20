package cjs2dphysicsengine.primitives;

import components.Component;
import org.joml.Vector2f;

public class Collider extends Component {
    protected Vector2f offset = new Vector2f();

    // public abstract float getInertiaTensor(float mass); // todo: implement this
}
