package utility;

import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;

public class CFrame {
	public Vector3f p = new Vector3f();
	public Vector3f lookVector = new Vector3f();
	private Matrix3f matrix = new Matrix3f();
	public CFrame() {
		p = new Vector3f();
	}
	public CFrame(float x, float y, float z) {
		p = new Vector3f(x, y, z);
	}
	public CFrame(float x, float y, float z, float qx, float qy, float qz, float qw) {
		p = new Vector3f(x, y, z);
	}
	public CFrame(float x, float y, float z, float R00, float R01, float R02, float R10, float R11, float R12, float R20, float R21, float R22) {
		p = new Vector3f(x, y, z);
		matrix.m00 = R00;
		matrix.m10 = R01;
		matrix.m20 = R02;
		matrix.m01 = R10;
		matrix.m11 = R11;
		matrix.m21 = R12;
		matrix.m02 = R20;
		matrix.m12 = R21;
		matrix.m22 = R22;
	}
	public CFrame(Vector3f v1, Vector3f v2) {
		p = v1;
		matrix.fromStartEndVectors(v1, v2);
	}
	public CFrame(Vector3f vec) {
		p = vec;
	}

	public String toString() {
		return "CFrame - [" + p.x + ", " + p.y + ", " + p.z + ", " + matrix.toString() + "]";
	}
}
