package artillery;

public class Vector2i {
	public int x = 0, y = 0, z = 0;

	public Vector2i() {
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	void setValues(int x, int y) {
		this.x = x;
		this.y = y;
	}

	void normalize() {
		int fLength = (int) Math.sqrt(x * x + y * y);

		// Will also work for zero-sized vectors, but will change nothing
		if (fLength > 1e-08) {
			int fInvLength = (1 / fLength);
			x *= fInvLength;
			y *= fInvLength;
		}
	}

	int magnitude() {
		return (int) (Math.sqrt(x * x + y * y));
	}

	public Vector2i subtract(Vector2i v) {
		return new Vector2i(this.x - v.x, this.y - v.y);
	}

	public Vector2i add(Vector2i v) {
		return new Vector2i(this.x + v.x, this.y + v.y);
	}

	public Vector2i multiply(Vector2i v) {
		return new Vector2i(this.x * v.x, this.y * v.y);
	}

	int magnitude(Vector2i rhs) {
		return this.subtract(rhs).magnitude();
	}
	
	static Vector2i getLineIntercept(Vector2i p1, Vector2i p2, Vector2i p3, Vector2i p4) {
		int d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
		if (d == 0) return null; // return null if parallel.
		Vector2i intercept = new Vector2i(
				((p3.x - p4.x) * ((p1.x * p2.y) - (p1.y * p2.x)) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x)) / d,
				((p3.y - p4.y) * ((p1.x * p2.y) - (p1.y * p2.x)) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x)) / d
				);
		//if (intercept.magnitude(p1) + intercept.magnitude(p2) > p1.magnitude(p2)) return null; // Intercept point is not within line segment.
		if (intercept.magnitude(p3) + intercept.magnitude(p4) > p3.magnitude(p4)) return null; // Intercept point is not within line segment.
		return intercept;
	}

	void getValues(int x, int y, int z) {
		x = this.z;
		y = this.y;
		z = this.z;
	}

	int cross(Vector3f v1) {
		return (int) ((this.x * v1.y) - (this.y * v1.x));
	}

	public int dot(Vector2i v) {
		return this.x * v.x + this.y * v.y;
	}

	boolean equals(Vector2i v) {
		return this.x == v.x && this.y == v.y;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
