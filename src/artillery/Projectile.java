package artillery;

public class Projectile {

	private int initialVelocityXI = 0;
	private int initialVelocityYI = 0;
	private int initialHeightI = 0;
	private float initialVelocityXF = 0;
	private float initialVelocityYF = 0;
	private float initialHeightF = 0;
	
	
	private int accelerationYi = 0;
	private int accelerationXi = 0;
	private float accelerationYf = 0.0f;
	private float accelerationXf = 0.0f;
	
	//Meters/Second
	public final static int EARTH_GRAVITY_INT = 10;
	public final static float EARTH_GRAVITY_FLOAT = 9.8f;
	
	private int timei = 0;
	private float timef = 0.0f;
	
	
	//Setters
	public int getInitialVelocityXI() {
		return initialVelocityXI;
	}
	public int getInitialVelocityYI() {
		return initialVelocityYI;
	}
	public int getInitialHeightI() {
		return initialHeightI;
	}
	public float getInitialVelocityXF() {
		return initialVelocityXF;
	}
	public float getInitialVelocityYF() {
		return initialVelocityYF;
	}
	public float getInitialHeightF() {
		return initialHeightF;
	}
	public int getAccelerationYi() {
		return accelerationYi;
	}
	public int getAccelerationXi() {
		return accelerationXi;
	}
	public float getAccelerationYf() {
		return accelerationYf;
	}
	public float getAccelerationXf() {
		return accelerationXf;
	}
	public int getTimei() {
		return timei;
	}
	public float getTimef() {
		return timef;
	}
	
	
	//Getters
	public void setTimef(float timef) {
		this.timef = timef;
	}
	public void setTimei(int timei) {
		this.timei = timei;
	}
	public void setAccelerationXf(float accelerationXf) {
		this.accelerationXf = accelerationXf;
	}
	public void setAccelerationYf(float accelerationYf) {
		this.accelerationYf = accelerationYf;
	}
	public void setAccelerationXi(int accelerationXi) {
		this.accelerationXi = accelerationXi;
	}
	public void setAccelerationYi(int accelerationYi) {
		this.accelerationYi = accelerationYi;
	}
	public void setInitialHeightF(float initialHeightF) {
		this.initialHeightF = initialHeightF;
	}
	public void setInitialVelocityYF(float initialVelocityYF) {
		this.initialVelocityYF = initialVelocityYF;
	}
	public void setInitialVelocityXF(float initialVelocityXF) {
		this.initialVelocityXF = initialVelocityXF;
	}
	public void setInitialHeightI(int initialHeightI) {
		this.initialHeightI = initialHeightI;
	}
	public void setInitialVelocityYI(int initialVelocityYI) {
		this.initialVelocityYI = initialVelocityYI;
	}
	public void setInitialVelocityXI(int initialVelocityXI) {
		this.initialVelocityXI = initialVelocityXI;
	}
	
	
	
}
