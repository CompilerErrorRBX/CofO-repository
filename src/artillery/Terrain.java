<<<<<<< HEAD
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
=======
package artillery;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import artillery.Vector3f;
import artillery.Window;

public class Terrain extends Base {

	private int mapSize = 100;
	private int index = 0;
	
	private Vector2i baseOffset = new Vector2i(0, 5);
	
	private ArrayList<Vector2i> fractal = new ArrayList<Vector2i>();
	
	private void createFractal(int fractures, float persistence) {
		// Initialize fractal
		fractal = new ArrayList<Vector2i>();
		// Add a point on the left side of the window.
		fractal.add(new Vector2i(0, baseOffset.y));
		// Add a point on the right side of the window.
		fractal.add(new Vector2i(mapSize, baseOffset.y));
		for (int n = 0; n < fractures; n++) {
			// Create fractures.
			int nodes = fractal.size();
			for (int i = 1; i < fractal.size(); i++) {
				Vector2i node1 = fractal.get(i-1);
				Vector2i node2 = fractal.get(i);
				System.out.println((Math.random() * persistence) * 2 - persistence);
				fractal.add(i, new Vector2i((node1.x + node2.x)/2, (int) (((node1.y + node2.y)/2) + (((Math.random() * persistence) * 2) - persistence))));
				i++;
			}
			persistence /= 2;
		}
		for (Vector2i vec : fractal) {
			System.out.println(vec);
		}
	}
	
	public void init(Window win, GLAutoDrawable drawable) {
		System.out.println("Initilizing map");
		GL2 gl = drawable.getGL().getGL2();

		Vector3f offset = new Vector3f(-mapSize / 2f, 0.0f,
				(mapSize / 2.0f) - 5);

		index = gl.glGenLists(1);
		
		createFractal(5, 1);
		
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
>>>>>>> origin/untested
package artillery;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import artillery.Vector3f;
import artillery.Window;

public class Terrain extends Base {

	private int mapSize = 100;
	private int index = 0;
	
	private Vector2i baseOffset = new Vector2i(0, 5);
	
	private ArrayList<Vector2i> fractal = new ArrayList<Vector2i>();
	// Our "roughness" constant.
	private float H = 0.5f;
	private int initialHeight = 25;
	
	private void createFractal(int fractures, float persistence) {
		// Initialize fractal
		fractal = new ArrayList<Vector2i>();
		// Add a point on the left side of the window.
		fractal.add(new Vector2i(0, baseOffset.y));
		// Add a point on the right side of the window.
		fractal.add(new Vector2i(mapSize, baseOffset.y));
		fractal.add(new Vector2i(mapSize/2, baseOffset.y + initialHeight));
		for (int n = 0; n < fractures; n++) {
			// Create fractures.
			for (int i = 1; i < fractal.size(); i++) {
				Vector2i node1 = fractal.get(i-1);
				Vector2i node2 = fractal.get(i);
				float randomness = (float) (((Math.random() * persistence * 2) - persistence)*10);
				fractal.add(i, new Vector2i((node1.x + node2.x)/2, (int) (((node1.y + node2.y)/2) + randomness)));
				i++;
			}
			persistence /= Math.pow(2, H);
		}
	}
	
	public void init(Window win, GLAutoDrawable drawable) {
		System.out.println("Initilizing map");
		GL2 gl = drawable.getGL().getGL2();

		Vector3f offset = new Vector3f(-mapSize / 2f, 0.0f,
				(mapSize / 2.0f) - 5);

		index = gl.glGenLists(1);
		
		createFractal(5, 1);
		
		gl.glNewList(index, GL2.GL_COMPILE);
		for (int i = 0; i < fractal.size(); i++) {
			Vector2i node = fractal.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(offset.x, offset.y, offset.z);

			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(5, 5, 5);
			// gl.glTexCoord2f(0, 0);
			gl.glVertex3f(node.x - .5f, 0, -node.y - .5f);
			// gl.glTexCoord2f(1, 0);
			gl.glVertex3f(node.x + .5f, 0, -node.y - .5f);
			// gl.glTexCoord2f(1, 1);
			gl.glVertex3f(node.x + .5f, 0, -node.y + .5f);
			// gl.glTexCoord2f(0, 1);
			gl.glVertex3f(node.x - .5f, 0, -node.y + .5f);
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
