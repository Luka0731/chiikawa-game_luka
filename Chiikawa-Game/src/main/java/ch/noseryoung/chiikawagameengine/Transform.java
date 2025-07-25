package ch.noseryoung.chiikawagameengine;

import org.joml.Vector2f;

/**
 * Every game object contains an instance of this class.
 * It is used to manage the position, rotation, and scale of the object.
 */
public class Transform {
    public Vector2f position;
    public Vector2f scale;
    // todo: add rotation

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(position), new Vector2f(scale));
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transform)) return false;
        Transform that = (Transform) o;
        return that.position.equals(this.position) && that.scale.equals(this.scale);
    }
}