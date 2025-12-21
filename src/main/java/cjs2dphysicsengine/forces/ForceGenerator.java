package cjs2dphysicsengine.forces;

import cjs2dphysicsengine.rigidbody.Rigidbody;

public interface ForceGenerator {
    void updateForce(Rigidbody rigidbody, float dt);
}