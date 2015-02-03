package artillery;

public class Camera {
	private Vector3f vEye = new Vector3f();
	private Vector3f vTarget = new Vector3f();
	private Vector3f vUp = new Vector3f();
	
	public Camera() {};
	public Camera(Vector3f eye, Vector3f target, Vector3f up) {
		setEye(eye);
		setTarget(target);
		setUp(up);
	}
	
	public void set(Vector3f eye, Vector3f target, Vector3f up) {
		setEye(eye);
		setTarget(target);
		setUp(up);
	}

	public Vector3f getEye() {
		return vEye;
	}

	public void setEye(Vector3f eye) {
		this.vEye = eye;
	}

	public Vector3f getTarget() {
		return vTarget;
	}

	public void setTarget(Vector3f target) {
		this.vTarget = target;
	}

	public Vector3f getUp() {
		return vUp;
	}

	public void setUp(Vector3f up) {
		this.vUp = up;
	}
}
