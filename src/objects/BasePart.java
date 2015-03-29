package objects;

import game.Main;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import render.Renderable;
import render.Window;
import utility.Color3f;
import utility.Vector2f;
import utility.Vector3f;

public class BasePart extends Container implements Renderable {
	public String name = "Cube";
	public Vector3f position = new Vector3f(); // Set initial position to 0, 0, 0;
	public Color3f color = new Color3f(5, 5, 5); // Default color is white.
	public float transparency = 0.0f;
	public Vector3f rotation = new Vector3f(0, (float) Math.PI/4, 0); // Rotation should be expressed in radians.
	
	public BasePart(Container pParent) {
		this.setParent(pParent); // this.setParent comes from Container.
		init();
	}
	public BasePart() {
		init();
	}
	
	public void setName(String str) {
		name = str;
	}
	
	public void init() {
		new Mesh("3DModels/Cube.obj", this);
		Main.window.addBase(this);
	}

	public Container[] getChildren() {
		return (Container[]) this.children.toArray();
	}
	
	@Override
	public boolean keyPressed(KeyEvent key) {
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent key) {return false;}

	@Override
	public boolean mouseClicked(MouseEvent event) {return false;}

	@Override
	public boolean mouseDragged(MouseEvent event) {return false;}

	@Override
	public boolean mouseEntered(MouseEvent event) {return false;}

	@Override
	public boolean mouseExited(MouseEvent event) {return false;}

	@Override
	public boolean mouseMoved(MouseEvent event) {return false;}

	@Override
	public boolean mousePressed(MouseEvent event) {return false;}

	@Override
	public boolean mouseReleased(MouseEvent event) {return false;}

	@Override
	public boolean mouseWheelMoved(MouseEvent event) {return false;}

	@Override
	public void render(GLAutoDrawable drawable) {
		if (parent == null) return; // Don't display if it doesn't have a parent.
		
		for (Container child : children) {
			child.parentRenderModifier();
		}
		
		GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor4f(color.r, color.g, color.b, 1 - transparency);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		//gl.glPushMatrix();
		
		gl.glRotatef(rotation.x, 1, 0, 0);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.z, 0, 0, 1);
		
		for (int i = 0; i < faces.size(); i++) {
			for (int j = 0; j < faces.get(i).length; j++) { 
				Vector3f thisVert = vertices.get((int) (faces.get(i)[j].x-1));
				Vector3f thisNorm = normals.get((int) (faces.get(i)[j].z-1));
				if (faces.get(i)[j].y > 0) { 
					Vector2f texCoord = textureCoords.get((int) (faces.get(i)[j].y-1));
					gl.glTexCoord2f(texCoord.x, texCoord.y);
				}
				gl.glNormal3f(thisNorm.x, thisNorm.y, thisNorm.z);
				gl.glVertex3f((position.x + thisVert.x), (position.y + thisVert.y), (position.z + thisVert.z));
			}
		}
		//gl.glPopMatrix();
		gl.glEnd();
	}

	@Override
	public void init(Window win, GLAutoDrawable drawable) {
	}
}
