package artillery;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Sky extends Base {
	private Vector3f offset = new Vector3f(-Terrain.mapSize / 2.0f, 0.0f, (Terrain.mapSize / 2.0f) - 5);
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		System.out.println("Initialize Sky");
		this.getCamera().set(new Vector3f(0, Terrain.mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f)); // Set the camera's position.
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix(); // Push the coordinate matrix.
		gl.glTranslatef(offset.x, offset.y, offset.z); // Translate the coordinate matrix by our offset.
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.5f, 4f, 5);
		//gl.glTexCoord2f(0, 0);
		gl.glVertex3f(0, 0, 0);
		//gl.glTexCoord2f(1, 0);
		gl.glVertex3f(Terrain.mapSize, 0, 0);
		//gl.glTexCoord2f(1, 1);
		gl.glColor3f(0.5f, 2.5f, 3.5f);
		gl.glVertex3f(Terrain.mapSize, 0, -Terrain.mapSize);
		//gl.glTexCoord2f(0, 1);
		gl.glVertex3f(0, 0, -Terrain.mapSize);
		gl.glEnd();
		
		gl.glPopMatrix();
		
	}
}
