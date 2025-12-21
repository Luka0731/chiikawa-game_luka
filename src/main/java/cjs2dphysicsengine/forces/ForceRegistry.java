package cjs2dphysicsengine.forces;

import cjs2dphysicsengine.rigidbody.Rigidbody;

import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    public void add(Rigidbody rigidbody, ForceGenerator forceGenerator) {
        ForceRegistration fr = new ForceRegistration(forceGenerator, rigidbody);
        registry.add(fr);
    }

    public void remove(Rigidbody rigidbody, ForceGenerator forceGenerator) {
        ForceRegistration fr = new ForceRegistration(forceGenerator, rigidbody);
        registry.remove(fr);
    }

    public void clear() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration forceRegistration : registry) {
            forceRegistration.forceGenerator.updateForce(forceRegistration.rigidbody, dt);
        }
    }

    public void zeroForces() {
        for (ForceRegistration forceRegistration : registry) {
            // TODO: IMPLEMENT ME
            //forceRegistration.rigidbody.zeroForces();
        }
    }
}
