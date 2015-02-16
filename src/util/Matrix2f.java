package util;

public class Matrix2f {

	public float m00, m01;
	public float m10, m11;

	public Matrix2f() {
	}

	public Matrix2f(float radians) {
		set(radians);
	}

	public Matrix2f(float a, float b, float c, float d) {
		set(a, b, c, d);
	}

	public void set(float radians) {
		float c = (float) Math.cos(radians);
		float s = (float) Math.sin(radians);

		m00 = c;
		m01 = -s;
		m10 = s;
		m11 = c;
	}

	public void set(float a, float b, float c, float d) {
		m00 = a;
		m01 = b;
		m10 = c;
		m11 = d;
	}

	public void set(Matrix2f m) {
		m00 = m.m00;
		m01 = m.m01;
		m10 = m.m10;
		m11 = m.m11;
	}

	public void absi() {
		abs(this);
	}

	public Matrix2f abs() {
		return abs(new Matrix2f());
	}

	public Matrix2f abs(Matrix2f out) {
		out.m00 = Math.abs(m00);
		out.m01 = Math.abs(m01);
		out.m10 = Math.abs(m10);
		out.m11 = Math.abs(m11);
		return out;
	}
	
	public Vector2f getAxisX(Vector2f out) {
		out.x = m00;
		out.y = m10;
		return out;
	}
	
	public Vector2f getAxisX() {
		return getAxisX(new Vector2f());
	}
	
	public Vector2f getAxisY(Vector2f out) {
		out.x = m01;
		out.y = m11;
		return out;
	}

	public Vector2f getAxisY() {
		return getAxisY(new Vector2f());
	}

	public void transposei() {
		float t = m01;
		m01 = m10;
		m10 = t;
	}

	public Matrix2f transpose(Matrix2f out) {
		out.m00 = m00;
		out.m01 = m10;
		out.m10 = m01;
		out.m11 = m11;
		return out;
	}

	public Matrix2f transpose() {
		return transpose(new Matrix2f());
	}

	public Vector2f muli(Vector2f v) {
		return mul(v.x, v.y, v);
	}

	public Vector2f mul(Vector2f v, Vector2f out) {
		return mul(v.x, v.y, out);
	}

	public Vector2f mul(Vector2f v) {
		return mul(v.x, v.y, new Vector2f());
	}

	public Vector2f mul(float x, float y, Vector2f out) {
		out.x = m00 * x + m01 * y;
		out.y = m10 * x + m11 * y;
		return out;
	}

	public void muli(Matrix2f x) {
		set(m00 * x.m00 + m01 * x.m10, m00 * x.m01 + m01 * x.m11, m10 * x.m00 + m11 * x.m10, m10 * x.m01 + m11 * x.m11);
	}

	public Matrix2f mul(Matrix2f x, Matrix2f out) {
		out.m00 = m00 * x.m00 + m01 * x.m10;
		out.m01 = m00 * x.m01 + m01 * x.m11;
		out.m10 = m10 * x.m00 + m11 * x.m10;
		out.m11 = m10 * x.m01 + m11 * x.m11;
		return out;
	}

	public Matrix2f mul(Matrix2f x) {
		return mul(x, new Matrix2f());
	}
	public String toString() {
		return "(" + m00 + ", " + m01 + ", " + m10 + ", " + m11 + ")";
	}
}
