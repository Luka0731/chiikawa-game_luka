package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.Component;
import org.joml.Vector3f;
import org.joml.Vector4f;

// this clas is for testing rn
public class Rigidbody extends Component {

    private int colliderType = 0;
    private float friction = 0.8f;
    private Vector3f velocity = new Vector3f(0, 0.5f, 0);
    public transient Vector4f tmp = new  Vector4f(0, 0, 0, 0);
}
