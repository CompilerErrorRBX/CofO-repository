package utility;


public class Vector2i {
	public int x = 0, y = 0;

	public Vector2i() {
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setValues(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void normalize() {
		float fLength = this.magnitude();
	    if (fLength > 0) {
	    	x /= fLength;
	    	y /= fLength;
	    }
	}
	
	public Vector2i unit() {
		Vector2i vec = new Vector2i(x, y);
		float fLength = vec.magnitude();
	    if (fLength > 0) {
	    	vec.x /= fLength;
	    	vec.y /= fLength;
	    }
		return vec;
	}

	public int magnitude() {
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

	public Vector2i multiply(int v) {
		return new Vector2i(this.x * v, this.y * v);
	}
	
	public int magnitude(Vector2i rhs) {
		return this.subtract(rhs).magnitude();
	}
	
	public static Vector2i getLineIntercept(Vector2i p1, Vector2i p2, Vector2i p3, Vector2i p4) {
		int d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
		if (d == 0) return null;
		Vector2i intercept = new Vector2i(
				((p3.x - p4.x) * ((p1.x * p2.y) - (p1.y * p2.x)) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x)) / d,
				((p3.y - p4.y) * ((p1.x * p2.y) - (p1.y * p2.x)) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x)) / d
				);
		if (intercept.magnitude(p3) + intercept.magnitude(p4) > p3.magnitude(p4)) return null;
		return intercept;
	}

	public void getValues(int x, int y) {
		x = this.x;
		y = this.y;
	}

	public int cross(Vector2i v1) {
		return (int) ((this.x * v1.y) - (this.y * v1.x));
	}
	
	public static int cross(Vector2i v1, Vector2i v2) {
		return (int) ((v1.x * v2.y) - (v1.y * v2.x));
	}

	public int dot(Vector2i v) {
		return this.x * v.x + this.y * v.y;
	}

	public static int dot(Vector2i v1, Vector2i v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	boolean equals(Vector2i v) {
		return this.x == v.x && this.y == v.y;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
