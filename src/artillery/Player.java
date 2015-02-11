package artillery;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Player extends Base {
	public Vector3f position = new Vector3f(0,1f,0);
	public Vector3f offset = new Vector3f(-Terrain.mapSize / 2.0f, 0.0f, (Terrain.mapSize / 2.0f) - 5);
	//private TextureManager textureManager = new TextureManager();
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//this.setTextureManager(textureManager);
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		//textureManager.textureFromFile("texture/Tank.png", "png", false).bind(gl);
		
		gl.glPushMatrix(); // Push the coordinate matrix.
		gl.glTranslatef(offset.x, offset.y, offset.z); // Translate the coordinate matrix by our offset.
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(5, 5, 5);
		//gl.glTexCoord2f(0, 0);
		gl.glVertex3f(position.x, position.y, -position.z);
		//gl.glTexCoord2f(1, 0);
		gl.glVertex3f(position.x + 50f, position.y, -position.z);
		//gl.glTexCoord2f(1, 1);
		gl.glVertex3f(position.x + 50f, position.y, -position.z - 50f);
		//gl.glTexCoord2f(0, 1);
		gl.glVertex3f(position.x, position.y, -position.z - 50f);
		gl.glEnd();
		
		gl.glPopMatrix();
		
	}
}
