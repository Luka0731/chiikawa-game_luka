package cjs2dphysicsengine.forces;

import cjs2dphysicsengine.rigidbody.Rigidbody;

/**
 * A Wrapper Class for ForceGenerator and Rigidbody
 */
public class ForceRegistration {
    public ForceGenerator forceGenerator;
    public Rigidbody rigidbody;

    public ForceRegistration(ForceGenerator forceGenerator, Rigidbody rigidbody) {
        this.forceGenerator = forceGenerator;
        this.rigidbody = rigidbody;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != ForceRegistration.class) return false;

        ForceRegistration fr = (ForceRegistration)other;
        return fr.rigidbody == this.rigidbody && fr.forceGenerator == this.forceGenerator;
    }
}
