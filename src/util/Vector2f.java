package util;

public class Vector2f {
	final float EPSILON = 0.005f;  // error tolerance for check
	public float x = 0, y = 0;
	
	private boolean FLOAT_EQ(float x, float v) {
		return ((v) - EPSILON) < (x) && (x) < ((v) + EPSILON);
	}
	
	public Vector2f() {}
	public Vector2f(float x, float y) {
	    this.x = x;
	    this.y = y;
	}
	
	public Vector2f(Vector2f vec) {
	    this.x = vec.x;
	    this.y = vec.y;
	}
	
	public static float dot(Vector2f a, Vector2f b) {
		return a.x * b.x + a.y * b.y;
	}
	
	public static Vector2f cross(Vector2f v, float a, Vector2f out) {
		out.x = v.y * a;
		out.y = v.x * -a;
		return out;
	}

	public static Vector2f cross(float a, Vector2f v, Vector2f out) {
		out.x = v.y * -a;
		out.y = v.x * a;
		return out;
	}

	public static float cross(Vector2f a, Vector2f b) {
		return a.x * b.y - a.y * b.x;
	}
	
	void setValues(float x, float y) {
	    this.x = x;
	    this.y = y;
	}
	 
	float lerp(float a, float b, float t) { 
		return a + t * (b - a); 
	}
	
	Vector2f lerp(Vector2f v2, float alpha) {
		return new Vector2f(lerp(this.x, v2.x, alpha), lerp(this.y, v2.y, alpha));
	}
	
	public void normalize() {
		float fLength = this.magnitude();
	    if (fLength > 0) {
	    	x /= fLength;
	    	y /= fLength;
	    }
	}
	
	public Vector2f unit() {
		Vector2f vec = new Vector2f(x, y);
		float fLength = vec.magnitude();
	    if (fLength > 0) {
	    	vec.x /= fLength;
	    	vec.y /= fLength;
	    }
		return vec;
	}
	 
	boolean isZero() {
	    return ( FLOAT_EQ(0.0f, this.magnitude()) );
	}
	 
	float magnitude() {
	    return (float) ( Math.sqrt( x*x + y*y ) );
	}
	
	public float magnitudeSq() {
	    return (float) ( x*x + y*y );
	}
	
	float magnitudeSq(Vector2f v2) {
	    return (float) ( Math.pow(x-v2.x, 2) + Math.pow(y-v2.x, 2) );
	}
	
	public Vector2f subtract(Vector2f v) {
		return new Vector2f( this.x - v.x,  this.y - v.y);
	}
	
	public Vector2f subtract(Vector2f v, Vector2f out) {
		out.x = x - v.x;
		out.y = y - v.y;
		return out;
	}
	
	public Vector2f subtracti(Vector2i v) {
		return new Vector2f( this.x - v.x,  this.y - v.y);
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f( this.x + v.x,  this.y + v.y);
	}
	
	public Vector2f addi(Vector2i v) {
		return new Vector2f(this.x + v.x,  this.y + v.y);
	}
	
	public Vector2f addi(float v) {
		return new Vector2f(this.x + v,  this.y + v);
	}
	
	public Vector2f add(float v) {
		return new Vector2f(this.x + v,  this.y + v);
	}
	
	public Vector2f addsi(Vector2f v, float s) {
		return adds(v, s, this);
	}

	public Vector2f adds(Vector2f v, float s, Vector2f out) {
		out.x = x + v.x * s;
		out.y = y + v.y * s;
		return out;
	}

	public Vector2f adds(Vector2f v, float s) {
		return adds(v, s, new Vector2f());
	}
	
	public Vector2f multiply(Vector2f v) {
		return new Vector2f( this.x * v.x,  this.y * v.y);
	}
	
	public Vector2f multiply(float v) {
		return new Vector2f( this.x * v,  this.y * v);
	}
	
	public Vector2i toVector2i() {
		return new Vector2i((int) x, (int) y);
	}
	
	float magnitude(Vector2f rhs) {
	    return this.subtract(rhs).magnitude();
	}
	

	public Vector2f negi() {
		return neg(this);
	}

	public Vector2f neg(Vector2f out) {
		out.x = -x;
		out.y = -y;
		return out;
	}
	
	public Vector2f neg() {
		return neg(new Vector2f());
	}
	
	static Vector2f getLineIntercept(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
		float d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
		if (d == 0) return null; // return null if parallel.
		Vector2f intercept = new Vector2f(
				((p3.x - p4.x) * (p1.x * p2.y - p1.y * p2.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x)) / d,
				((p3.y - p4.y) * (p1.x * p2.y - p1.y * p2.x) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x)) / d
				);
		//if (intercept.magnitude(p1) + intercept.magnitude(p2) > p1.magnitude(p2)) return null; // Intercept point is not within line segment.
		if (intercept.magnitude(p3) + intercept.magnitude(p4) > p3.magnitude(p4)) return null; // Intercept point is not within line segment.
		return intercept;
	}
	
	void getValues(float x, float y) {
	    x = this.x;
	    y = this.y;
	}
	public float cross(Vector2f v1) {
		return (this.x*v1.y) - (this.y*v1.x);
	}
	public Vector2f cross(Vector2f v, float a) {
		return new Vector2f(v.x * -a, v.y * a);
	}
	public Vector2f cross(float a, Vector2f v) {
		return this.cross(a, v);
	}
	
	public float dot(Vector2f v) {
		return this.x*v.x + this.y*v.y;
	}
	 
	boolean equals(Vector2f v) {
		return this.x == v.x && this.y== v.y;
	}
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public void set(float x2, float y2) {
		x = x2;
		y = y2;
	}
	
	public Vector2f set(Vector2f v) {
		x = v.x;
		y = v.y;
		return this;
	}

	public static Vector2f[] arrayOf(int length) {
		Vector2f[] array = new Vector2f[length];
		while (--length >= 0) {
			array[length] = new Vector2f();
		}
		return array;
	}
}
