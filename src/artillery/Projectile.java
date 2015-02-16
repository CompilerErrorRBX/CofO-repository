package artillery;

import java.util.Date;

import util.Vector2f;
import util.Vector2i;

public class Projectile {

	private int initialVelocityXI = 0;
	private int initialVelocityYI = 0;
	private int initialHeightI = 0;
	private float initialVelocityXF = 0;
	private float initialVelocityYF = 0;
	private float initialHeightF = 0;
	private Date timeCreated = new Date();
	private float angle;
	
	private int accelerationYi = -EARTH_GRAVITY_INT;
	private int accelerationXi = 0;
	private float accelerationYf = -EARTH_GRAVITY_FLOAT;
	private float accelerationXf = 0.0f;
	
	//Meters/(Second^2)
	public static final int EARTH_GRAVITY_INT = 10;
	public static final float EARTH_GRAVITY_FLOAT = 9.8f;
	public static final int MERCURY_GRAVITY_INT = 4;
	public static final float MERCURY_GRAVITY_FLOAT = 3.61f;
	public static final int VENUS_GRAVITY_INT = 9;
	public static final float VENUS_GRAVITY_FLOAT = 8.83f;
	public static final int MARS_GRAVITY_INT = 4;
	public static final float MARS_GRAVITY_FLOAT = 3.75f;
	public static final int JUPITER_GRAVITY_INT = 26;
	public static final float JUPITER_GRAVITY_FLOAT = 26.0f;
	public static final int SATURN_GRAVITY_INT = 11;
	public static final float SATURN_GRAVITY_FLOAT = 11.2f;
	public static final int URANUS_GRAVITY_INT = 11;
	public static final float URANUS_GRAVITY_FLOAT = 10.5f;
	public static final int NEPTUNE_GRAVITY_INT = 13;
	public static final float NEPTUNE_GRAVITY_FLOAT = 13.3f;
	public static final int PLUTO_GRAVITY_INT = 1;
	public static final float PLUTO_GRAVITY_FLOAT = .61f;
	
	//Constructors
	public Projectile() {
		
	}
	
	public Projectile(Vector2i initialVelocity) {
		initialVelocityXI = initialVelocity.x;
		initialVelocityYI = initialVelocity.y;
	}
	
	public Projectile(Vector2f initialVelocity) {
		initialVelocityXF = initialVelocity.x;
		initialVelocityYF = initialVelocity.y;
	}
	
	//Getters
	/** @return The initial X velocity of the projectile in an int form. */
	public int getInitialVelocityXI() {
		return initialVelocityXI;
	}
	/** @return The initial Y velocity of the projectile in an int form. */
	public int getInitialVelocityYI() {
		return initialVelocityYI;
	}
	/** @return The initial height of the projectile in int form. */
	public int getInitialHeightI() {
		return initialHeightI;
	}
	/** @return The initial X velocity of the projectile in float form. */
	public float getInitialVelocityXF() {
		return initialVelocityXF;
	}
	/** @return The initial Y velocity of the projectile in float form. */
	public float getInitialVelocityYF() {
		return initialVelocityYF;
	}
	/** @return The initial height of the projectile in float form. */
	public float getInitialHeightF() {
		return initialHeightF;
	}
	/** @return The Y acceleration of the projectile in int form. */
	public int getAccelerationYi() {
		return accelerationYi;
	}
	/** @return The X acceleration of the projectile in int form. */
	public int getAccelerationXi() {
		return accelerationXi;
	}
	/** @return The Y acceleration of the projectile in float form. */
	public float getAccelerationYf() {
		return accelerationYf;
	}
	/** @return The X acceleration of the projectile in float form. */
	public float getAccelerationXf() {
		return accelerationXf;
	}
	/** @return The launch angle of the projectile in radians. */
	public float getAngleRadians() {
		return angle;
	}
	/** @return The launch angle of the projectile in degrees. */
	public float getAngleDegrees() {
		return (float) (angle * (180/Math.PI));
	}
	/** 
	 * This will give the displacement of the projectile in meters. More math may be required
	 * to translate into an appropriate number of pixels.
	 * 
	 * Since this will return the float position cast as an int, the exact position may be slightly off.
	 * It is recommended to use the getPositionf function.
	 * 
	 * @return The displacement of the projectile in meters. 
	 * */
	public Vector2i getPositioni() {
		float t = timeCreated.getTime() - new Date().getTime();
		Vector2i position = new Vector2i();
		position.x = (int) (initialVelocityXF * t * Math.cos(angle));
		position.y = (int) (initialVelocityYF * t * Math.sin(angle) - 0.5 * EARTH_GRAVITY_FLOAT * t * t);
		return position;
	}
	/** @return The displacement of the projectile in meters. */
	// Can you get the slope of the tangent line at this point so that we can also get an accurate angle for the shell?
	// Mainly to make collision detection easier.
	public Vector2f getPositionf() {
		float t = timeCreated.getTime() - new Date().getTime();
		Vector2f position = new Vector2f();
		position.x = (float) (initialVelocityXF * t * Math.cos(angle));
		position.y = (float) (initialVelocityYF * t * Math.sin(angle) - 0.5 * EARTH_GRAVITY_FLOAT * t * t);
		return position;
	}
	
	//Setters
	/** @param accelerationXf The acceleration along the X-axis. */
	public void setAccelerationXf(float accelerationXf) {
		this.accelerationXf = accelerationXf;
	}
	/** @param accelerationYf The acceleration along the X-axis. */
	public void setAccelerationYf(float accelerationYf) {
		this.accelerationYf = accelerationYf;
	}
	/** @param accelerationXi The acceleration along the Y-axis. */
	public void setAccelerationXi(int accelerationXi) {
		this.accelerationXi = accelerationXi;
	}
	/** @param accelerationYi The acceleration along the Y-axis. */
	public void setAccelerationYi(int accelerationYi) {
		this.accelerationYi = accelerationYi;
	}
	/** @param initialHeightF The initial height of the projectile. */
	public void setInitialHeightF(float initialHeightF) {
		this.initialHeightF = initialHeightF;
	}
	/** @param initialVelocityYF The initial velocity along the Y-axis. */
	public void setInitialVelocityYF(float initialVelocityYF) {
		this.initialVelocityYF = initialVelocityYF;
	}
	/** @param initialVelocityXF The initial velocity along the X-axis. */
	public void setInitialVelocityXF(float initialVelocityXF) {
		this.initialVelocityXF = initialVelocityXF;
	}
	/** @param initialHeightI The initial height of the projectile. */
	public void setInitialHeightI(int initialHeightI) {
		this.initialHeightI = initialHeightI;
	}
	/** @param initialVelocityYI The initial velocity along the Y-axis. */
	public void setInitialVelocityYI(int initialVelocityYI) {
		this.initialVelocityYI = initialVelocityYI;
	}
	/** @param initialVelocityXI The initial velocity along the X-axis. */
	public void setInitialVelocityXI(int initialVelocityXI) {
		this.initialVelocityXI = initialVelocityXI;
	}	
	/** @param rAngle The angle in radians. */
	public void setAngleRadians(float rAngle) {
		angle = rAngle;
	}
	/** @param dAngle The angle in degrees. */
	public void setAngleDegrees(float dAngle) {
		// There is a Math.deg method built in just so you know :)
		angle = (float) ((dAngle * Math.PI)/180);
	}

	//H = [Initial Velocity^2 * sin(Angle In Radians (Double))] / 2 * Gravity
	public float deriveHeight(float gravity) {
		return (float) ((Math.pow(initialVelocityYF, 2.0) * Math.sin(angle)) / 2 * gravity) + initialHeightI;
	}
	
}
