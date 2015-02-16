package util;

public class Vector3f {
	final float EPSILON = 0.005f; // error tolerance for check
	public float x = 0, y = 0, z = 0;

	private boolean FLOAT_EQ(float x, float v) {
		return ((v) - EPSILON) < (x) && (x) < ((v) + EPSILON);
	}

	public Vector3f() {
	}

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setValues(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	float lerp(float a, float b, float t) {
		return a + t * (b - a);
	}

	Vector3f lerp(Vector3f v2, float alpha) {
		return new Vector3f(lerp(this.x, v2.x, alpha),
				lerp(this.y, v2.y, alpha), lerp(this.z, v2.z, alpha));
	}

	void normalize() {
		float fLength = (float) Math.sqrt(x * x + y * y + z * z);

		// Will also work for zero-sized vectors, but will change nothing
		if (fLength > 1e-08) {
			float fInvLength = (1.0f / fLength);
			x *= fInvLength;
			y *= fInvLength;
			z *= fInvLength;
		}
	}

	boolean isZero() {
		return (FLOAT_EQ(0.0f, this.magnitude()));
	}

	float magnitude() {
		return (float) (Math.sqrt(x * x + y * y + z * z));
	}

	public Vector3f subtract(Vector3f v) {
		return new Vector3f(this.x - v.x, this.y - v.y, this.z - v.z);
	}

	public Vector3f add(Vector3f v) {
		return new Vector3f(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	public Vector3f multiply(Vector3f v) {
		return new Vector3f(this.x * v.x, this.y * v.y, this.z * v.z);
	}
	public Vector3f multiply(float v) {
		return new Vector3f(this.x * v, this.y * v, this.z * v);
	}

	float magnitude(Vector3f rhs) {
		return this.subtract(rhs).magnitude();
	}

	void getValues(float x, float y, float z) {
		x = this.z;
		y = this.y;
		z = this.z;
	}

	Vector3f cross(Vector3f v1) {
		float vx, vy, vz;

		vx = this.y * v1.z - this.z * v1.y;
		vy = this.z * v1.x - this.x * v1.z;
		vz = this.x * v1.y - this.y * v1.x;

		return new Vector3f(vx, vy, vz);
	}

	public float dot(Vector3f v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}

	boolean equals(Vector3f v) {
		return this.x == v.x && this.y == v.y && this.z == v.z;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
}
