package artillery;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import physics.Body;
import util.Color3f;
import util.Polygon;
import util.Vector2f;
import util.Vector3f;

public class Instance extends Base {
	public Color3f color = new Color3f(5, 5, 5);
	public float transparency = 0;
	private Vector2f[] vertices;
	private Vector3f offset = Terrain.offset;
	private Body body;
	private boolean anchored = false; // Is object static?

	public Instance(Vector2f position, Vector2f... verts) {
		// Create vertices
		vertices = Vector2f.arrayOf(verts.length);
		for (int i = 0; i < verts.length; i++) {
			vertices[i].set(verts[i].x, verts[i].y);
		}
		// Create shape
		body = Main.physics.add(new Polygon(vertices), (int) position.x, (int) position.y);
		body.setStatic(anchored);
	}
	
	public void setColor(Color3f newColor) {
		color = newColor;
	}
	
	public void setTransparency(float trans) {
		transparency = trans;
	}
	
	public Vector2f getPosition() {
		return body.position;
	}
	
	public void setAnchored(boolean anchor) {
		anchored = anchor;
		body.setStatic(anchored);
	}
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		//this.setTextureManager(new TextureManager());
		
		this.getCamera().set(new Vector3f(0, Terrain.mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f)); // Set the camera's position.
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix(); // Push the coordinate matrix.
		gl.glTranslatef(offset.x, offset.y, offset.z); // Translate the coordinate matrix by our offset.
		
		gl.glBegin(GL2.GL_POLYGON);

		gl.glColor4f(color.r, color.g, color.b, 1 - transparency);
		
		for (int i = 0; i < vertices.length; i++) {
			Vector2f vertex = vertices[i];
			gl.glVertex3f(body.position.x + vertex.x, 1, -(body.position.y + vertex.y));
		}
		
		gl.glEnd();
		
		
	}
	
}
