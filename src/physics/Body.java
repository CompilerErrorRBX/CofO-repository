package physics;

import util.Vector2f;

public class Body {

	public final Vector2f position = new Vector2f();
	public final Vector2f velocity = new Vector2f();
	public final Vector2f force = new Vector2f();
	public Vector2f centerOfMass = new Vector2f();
	public float angularVelocity;
	public float torque;
	public float orient;
	public float mass, invMass, inertia, invInertia;
	public float staticFriction;
	public float dynamicFriction;
	public float restitution;
	public final Shape shape;

	public Body(Shape shape, int x, int y) {
		this.shape = shape;

		position.set(x, y);
		velocity.set(0, 0);
		angularVelocity = 0;
		torque = 0;
		orient = 0;
		force.set(0, 0);
		staticFriction = 0.5f;
		dynamicFriction = 0.3f;
		restitution = 0.2f;

		shape.body = this;
		shape.initialize();
	}

	public void applyForce(Vector2f f) {
		force.add(f);
	}

	public void applyImpulse(Vector2f impulse, Vector2f contactVector) {
		velocity.addsi(impulse, invMass);
		angularVelocity += invInertia * contactVector.cross(impulse);
	}

	public void setStatic(boolean isStatic) {
		if (!isStatic) return;
		inertia = 0.0f;
		invInertia = 0.0f;
		mass = 0.0f;
		invMass = 0.0f;
	}

	public void setOrient(float radians) {
		orient = radians;
		shape.setOrient(radians);
	}
}
