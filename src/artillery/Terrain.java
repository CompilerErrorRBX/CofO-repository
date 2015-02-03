package artillery;

import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import artillery.Vector3f;
import artillery.Window;

public class Terrain extends Base {

	private int mapSize = 100;
	private int index = 0;
	
	public void init(Window win, GLAutoDrawable drawable) {
		System.out.println("Initilizing map");
		GL2 gl = drawable.getGL().getGL2();

		Vector3f offset = new Vector3f(-mapSize / 2f, 0.0f,
				(mapSize / 2.0f) - 5);

		index = gl.glGenLists(1);
		gl.glNewList(index, GL2.GL_COMPILE);
		for (int i = 0; i < mapSize; i++) {
			float x = 0;

			gl.glPushMatrix();
			gl.glTranslatef(offset.x, offset.y, offset.z);

			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(5, 5, 5);
			// gl.glTexCoord2f(0, 0);
			gl.glVertex3f(i - 1f, 0, -x);
			// gl.glTexCoord2f(1, 0);
			gl.glVertex3f(i + 1f, 0, -x);
			// gl.glTexCoord2f(1, 1);
			gl.glVertex3f(i + 1f, 0, Main.HEIGHT);
			// gl.glTexCoord2f(0, 1);
			gl.glVertex3f(i - 1f, 0, Main.HEIGHT);
			gl.glEnd();

			gl.glPopMatrix();
		}
		gl.glEndList();

		this.getCamera().set(new Vector3f(0, mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f));
	}

	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glCallList(index);
	}
}
