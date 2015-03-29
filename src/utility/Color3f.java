package utility;

public class Color3f {
	public float r = 0, g = 0, b = 0;
	public Color3f()
	{
	    this.set(1.0f, 1.0f, 1.0f);
	}

	public Color3f(float r, float g, float b)
	{
	    this.set(r, g, b);
	}

	public void set(float r, float g, float b)
	{
	    this.r = r;
	    this.g = g;
	    this.b = b;
	}

	public Color3f add(Color3f c2) {
		return new Color3f(this.r + c2.r, this.b + c2.b, this.g + c2.g);
	}
	public Color3f subtract(Color3f c2) {
		return new Color3f(this.r - c2.r, this.b - c2.b, this.g - c2.g);
	}

}
