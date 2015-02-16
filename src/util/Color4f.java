package util;

public class Color4f {
	public float r = 0, g = 0, b = 0, a = 0;
	public Color4f()
	{
	    this.set(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public Color4f(float r, float g, float b, float a)
	{
	    this.set(r, g, b, a);
	}

	public void set(float r, float g, float b, float a)
	{
	    this.r = r;
	    this.g = g;
	    this.b = b;
	    this.a = a;
	}

	public Color4f add(Color4f c2) {
		return new Color4f(this.r + c2.r, this.b + c2.b, this.g + c2.g, this.a + c2.a);
	}
	public Color4f subtract(Color4f c2) {
		return new Color4f(this.r - c2.r, this.b - c2.b, this.g - c2.g, this.a - c2.a);
	}

}
