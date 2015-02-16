package physics;

import util.Vector2f;

public class Manifold {
	public Body A;
	public Body B;
	public float penetration;
	public final Vector2f normal = new Vector2f();
	public final Vector2f[] contacts = { new Vector2f(), new Vector2f() };
	public int contactCount;
	public float e;
	public float df;
	public float sf;

	public Manifold(Body a, Body b) {
		A = a;
		B = b;
	}

	public void solve() {
		Collision.instance.handleCollision(this, A, B);
	}

	public void initialize() {
		// Calculate average restitution
		e = Math.min(A.restitution, B.restitution);

		// Calculate static and dynamic friction
		sf = (float) Math.sqrt(A.staticFriction * A.staticFriction);
		df = (float) Math.sqrt(A.dynamicFriction * A.dynamicFriction);

		for (int i = 0; i < contactCount; ++i) {
			// Calculate radii from COM to contact
			Vector2f ra = contacts[i].subtract(A.position);
			Vector2f rb = contacts[i].subtract(B.position);

			Vector2f rv = B.velocity.add(Vector2f.cross(B.angularVelocity, rb, new Vector2f())).subtract(A.velocity)
					.subtract(Vector2f.cross(A.angularVelocity, ra, new Vector2f()));

			// Determine if we should perform a resting collision or not
			// The idea is if the only thing moving this object is gravity,
			// then the collision should be performed without any restitution
			if (rv.magnitudeSq() < Mathp.RESTING) {
				e = 0.0f;
			}
		}
	}

	public void applyImpulse() {
		// Early out and positional correct if both objects have infinite mass
		if (Mathp.equal(A.invMass + B.invMass, 0)) {
			infiniteMassCorrection();
			return;
		}

		for (int i = 0; i < contactCount; ++i) {
			// Calculate radii from COM to contact
			Vector2f ra = contacts[i].subtract(A.position);
			Vector2f rb = contacts[i].subtract(B.position);

			// Relative velocity
			Vector2f rv = B.velocity.add(Vector2f.cross(B.angularVelocity, rb, new Vector2f())).subtract(A.velocity)
					.subtract(Vector2f.cross(A.angularVelocity, ra, new Vector2f()));

			// Relative velocity along the normal
			float contactVel = Vector2f.dot(rv, normal);

			// Do not resolve if velocities are separating
			if (contactVel > 0) {
				return;
			}

			float raCrossN = Vector2f.cross(ra, normal);
			float rbCrossN = Vector2f.cross(rb, normal);
			float invMassSum = A.invMass + B.invMass + (raCrossN * raCrossN) * A.invInertia + (rbCrossN * rbCrossN) * B.invInertia;

			// Calculate impulse scalar
			float j = -(1.0f + e) * contactVel;
			j /= invMassSum;
			j /= contactCount;

			// Apply impulse
			Vector2f impulse = normal.multiply(j);
			A.applyImpulse(impulse.neg(), ra);
			B.applyImpulse(impulse, rb);

			// Friction impulse
			rv = B.velocity.add(Vector2f.cross(B.angularVelocity, rb, new Vector2f())).subtract(A.velocity)
					.subtract(Vector2f.cross(A.angularVelocity, ra, new Vector2f()));

			Vector2f t = new Vector2f(rv);
			t.addsi(normal, -normal.dot(rv));
			t.normalize();

			// j tangent magnitude
			float jt = -Vector2f.dot(rv, t);
			jt /= invMassSum;
			jt /= contactCount;

			// Don't apply tiny friction impulses
			if (Mathp.equal(jt, 0.0f)) {
				return;
			}

			// Coulumb's law
			Vector2f tangentImpulse;
			if (Math.abs(jt) < j * sf) {
				tangentImpulse = t.multiply(jt);
			} else {
				tangentImpulse = t.multiply(j).multiply(-df);
			}

			// Apply friction impulse
			A.applyImpulse(tangentImpulse.neg(), ra);
			B.applyImpulse(tangentImpulse, rb);
		}
	}

	public void positionalCorrection() {
		float correction = StrictMath.max(penetration
				- Mathp.PENETRATION_ALLOWANCE, 0.0f)
				/ (A.invMass + B.invMass) * Mathp.PENETRATION_CORRETION;

		A.position.addsi(normal, -A.invMass * correction);
		B.position.addsi(normal, B.invMass * correction);
	}

	public void infiniteMassCorrection() {
		A.velocity.set(0, 0);
		B.velocity.set(0, 0);
	}

}
