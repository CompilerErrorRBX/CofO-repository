package artillery;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import util.Vector2i;
import util.Vector3f;

public class Shell extends Base {
	private float power = 20;
	private Vector2i velocity = new Vector2i();
	private Projectile force;
	public Shell(Vector2i velo) {
		force = new Projectile(velo);
		velocity = velo;
	}
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		System.out.println("Initialize Player");
		
		this.setTextureManager(new TextureManager());
	
		this.getCamera().set(new Vector3f(0, Terrain.mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f)); // Set the camera's position.
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		System.out.println(force.getPositionf());
	}
}
