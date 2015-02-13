package artillery;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

public class Player extends Base {
	public Vector3f position = new Vector3f(0,0.2f,0);
	public Vector3f offset = new Vector3f(-Terrain.mapSize / 2.0f, 0.0f, (Terrain.mapSize / 2.0f) - 5);
	private Vector3f velocity = new Vector3f();
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		System.out.println("Initialize Player");
		
		this.setTextureManager(new TextureManager());
		
		Vector2i spawn = Main.terrain.getTerrainAt((int) (Math.random() * (Terrain.mapSize - 20)) + 10, Terrain.mapSize);
		position.setValues(spawn.x, position.y, spawn.y); // spawn is a 2D coordinate so we use Y for the Z axis.
	
		this.getCamera().set(new Vector3f(0, Terrain.mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f)); // Set the camera's position.
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		Vector3f moveTo = position.add(velocity);
		Vector2i actualPosition = Main.terrain.getTerrainAt((int) moveTo.x, (int) moveTo.z+15);
		if (actualPosition == null) return;
		position = new Vector3f(actualPosition.x, position.y, actualPosition.y);
		
		this.getTextureManager().textureFromFile("texture/Tank.png", "png", false).bind(gl);
		
		gl.glPushMatrix(); // Push the coordinate matrix.
		gl.glTranslatef(offset.x, offset.y, offset.z); // Translate the coordinate matrix by our offset.
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(5, 5, 5);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(position.x - 15f, position.y, -position.z);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(position.x + 15f, position.y, -position.z);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(position.x + 15f, position.y, -position.z - 30f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(position.x - 15f, position.y, -position.z - 30f);
		gl.glEnd();
		
		gl.glPopMatrix();
		
		this.getTextureManager().textureFromFile("texture/Tank.png", "png", false).destroy(gl);
		
	}
	@Override
	public boolean keyPressed(KeyEvent key) {
		System.out.println("key down");
		if (key.getKeyChar() == 'd') {
			velocity = new Vector3f(1,0,0);
		}
		if (key.getKeyChar() == 'a') {
			velocity = new Vector3f(-0.5f,0,0);
		}
		return true;
	}
	@Override
	public boolean keyReleased(KeyEvent key) {
		System.out.println("key released");
		if (key.getKeyChar() == 'd'|| key.getKeyChar() == 'a') {
			velocity = new Vector3f(0,0,0);
		}
		return true;
	}
}
