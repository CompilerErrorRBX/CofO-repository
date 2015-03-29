package objects;

import game.Main;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import render.Renderable;
import render.Window;
import utility.CellularAutomata;
import utility.Vector2f;
import utility.Vector3f;

public class Terrain implements Renderable {
	protected ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	protected ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();
	protected ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
	protected ArrayList<Vector3f[]> faces = new ArrayList<Vector3f[]>();
	public CellularAutomata map;
	private Container parent = Main.game;
	public Terrain(int sizeX, int sizeZ, int depth, float aliveChance, Random random) {
		map = new CellularAutomata(sizeX, sizeZ, aliveChance, random);
		normals.add(new Vector3f(0,1,0));
		for (int x = 0; x < map.cellMap.length; x++) {
			for (int y = 0; y < map.cellMap[0].length; y++) {
				vertices.add(new Vector3f(x, map.cellMap[x][y].value == 1 ? 0 : -depth, y));
			}
		}
		for (int i = 0; i < vertices.size(); i++) {
			Vector3f[] face = new Vector3f[3];
			face[0] = new Vector3f(i,0,0);
			face[1] = new Vector3f(i+1,0,0);
			face[2] = new Vector3f(i+map.cellMap.length,0,0);
			faces.add(face);
		}
		Main.window.addBase(this);
	}
	@Override
	public void render(GLAutoDrawable drawable) {
		if (parent == null) return; // Don't display if it doesn't have a parent.
		
		GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(GL2.GL_TRIANGLES);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		
//		for (int i = 0; i < faces.size(); i++) {
//			for (int j = 0; j < faces.get(i).length; j++) { 
//				Vector3f thisVert = vertices.get((int) (faces.get(i)[j].x-1));
//				Vector3f thisNorm = normals.get((int) (faces.get(i)[j].z-1));
//				if (faces.get(i)[j].y > 0) { 
//					Vector2f texCoord = textureCoords.get((int) (faces.get(i)[j].y-1));
//					gl.glTexCoord2f(texCoord.x, texCoord.y);
//				}
//				gl.glNormal3f(thisNorm.x, thisNorm.y, thisNorm.z);
//				gl.glVertex3f((thisVert.x), (thisVert.y), (thisVert.z));
//			}
//		}
		gl.glPopMatrix();
		gl.glEnd();
	}
	@Override
	public boolean keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseWheelMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void init(Window win, GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
