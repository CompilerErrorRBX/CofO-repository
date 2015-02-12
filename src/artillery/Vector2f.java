package artillery;

public class Vector2f {
	final float EPSILON = 0.005f;  // error tolerance for check
	public float x = 0,y = 0,z = 0;
	
	private boolean FLOAT_EQ(float x, float v) {
		return ((v) - EPSILON) < (x) && (x) < ((v) + EPSILON);
	}
	
	public Vector2f() {}
	public Vector2f(float x, float y) {
	    this.x = x;
	    this.y = y;
	}
	 
	void setValues(float x, float y) {
	    this.x = x;
	    this.y = y;
	}
	 
	float lerp(float a, float b, float t) { return a + t * (b - a); }
	
	Vector2f lerp(Vector2f v2, float alpha) {
		return new Vector2f(lerp(this.x, v2.x, alpha), lerp(this.y, v2.y, alpha));
	}
	
	void normalize() {
	    float fLength = (float) Math.sqrt( x * x + y * y);
	 
	    // Will also work for zero-sized vectors, but will change nothing
	    if ( fLength > 1e-08 )
	    {
	        float fInvLength = (1.0f / fLength);
	        x *= fInvLength;
	        y *= fInvLength;
	    }
	}
	 
	boolean isZero() {
	    return ( FLOAT_EQ(0.0f, this.magnitude()) );
	}
	 
	float magnitude() {
	    return (float) ( Math.sqrt( x*x + y*y ) );
	}
	
	public Vector2f subtract(Vector2f v) {
		return new Vector2f( this.x - v.x,  this.y - v.y);
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f( this.x + v.x,  this.y + v.y);
	}
	
	public Vector2f multiply(Vector2f v) {
		return new Vector2f( this.x * v.x,  this.y * v.y);
	}
	 
	float magnitude(Vector2f rhs) {
	    return this.subtract(rhs).magnitude();
	}
	
	static Vector2f getLineIntercept(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
		float d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
		if (d == 0) return null; // return null if parallel.
		Vector2f intercept = new Vector2f(
				((p3.x - p4.x) * (p1.x * p2.y - p1.y * p2.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x)) / d,
				((p3.y - p4.y) * (p1.x * p2.y - p1.y * p2.x) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x)) / d
				);
		if (intercept.magnitude(p1) + intercept.magnitude(p2) > p1.magnitude(p2)) return null; // Intercept point is not within line segment.
		if (intercept.magnitude(p3) + intercept.magnitude(p4) > p3.magnitude(p4)) return null; // Intercept point is not within line segment.
		return intercept;
	}
	
	void getValues(float x, float y, float z) {
	    x = this.z;
	    y = this.y;
	    z = this.z;
	}
	float cross(Vector3f v1) {
		return (this.x*v1.y) - (this.y*v1.x);
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
}
