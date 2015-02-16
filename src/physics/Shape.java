package physics;

import util.Matrix2f;

public abstract class Shape
{

	public enum Type
	{
		Poly
	}

	public Body body;
	public float radius;
	public final Matrix2f cFrame = new Matrix2f();

	public Shape()
	{
	}

	public abstract Shape clone();

	public abstract void initialize();

	public abstract void computeMass( float density );

	public abstract void setOrient( float radians );

	public abstract Type getType();

}
