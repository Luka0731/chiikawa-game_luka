package cjs2dphysicsengine.primitives;

import org.joml.Vector2f;

public class RaycastResult {
    private Vector2f point;
    private Vector2f normal; // from where it bounces off
    private float t;
    private boolean hasHit;

    public RaycastResult() {
        point = new Vector2f();
        normal = new Vector2f();
        t = -1;
        hasHit = false;
    }

    public void init(Vector2f point, Vector2f normal, float t, boolean hasHit) {
        this.point.set(point);
        this.normal.set(normal);
        this.t = t;
        this.hasHit = hasHit;
    }

    public static void reset(RaycastResult result) {
        if (result != null) {
            result.point.zero();
            result.normal.set(0, 0);
            result.t = -1;
            result.hasHit = false;
        }
    }
}