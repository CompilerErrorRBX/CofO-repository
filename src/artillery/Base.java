package artillery;

import javax.media.opengl.GLAutoDrawable;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

public class Base {
	
	private Window window; 
	private Camera camera;
	private TextureManager textureMgr;
	
	public enum DrawMode {
		DM_3D,
		DM_2D
	}
	
	private DrawMode drawMode = DrawMode.DM_3D;
	
	public Base() {
		camera = new Camera();
	}
	// Keyboard Events
	public boolean keyPressed(KeyEvent key) {return true;}
	public boolean keyReleased(KeyEvent key) {return true;}
	
	// Mouse Events
	public boolean mouseClicked(MouseEvent event) {return true;}
	public boolean mouseDragged(MouseEvent event) {return true;}
	public boolean mouseEntered(MouseEvent event) {return true;}
	public boolean mouseExited(MouseEvent event) {return true;}
	public boolean mouseMoved(MouseEvent event) {return true;}
	public boolean mousePressed(MouseEvent event) {return true;}
	public boolean mouseReleased(MouseEvent event) {return true;}
	public boolean mouseWheelMoved(MouseEvent event) {return true;}
	
	// Main methods
	public void render(GLAutoDrawable drawable) {};
	public void init(Window win, GLAutoDrawable drawable) {};
	
	public DrawMode getDrawMode() {return drawMode;}
	public void setDrawMode(DrawMode mode) {drawMode = mode;}
	
	// Getters
	public Window getWindow() { 
		return window; 
	}
	public Camera getCamera() {
		return camera;
	}
	
	// Setters
	public void setWindow(Window win) { 
		window = win; 
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public TextureManager getTextureManager() {
		return textureMgr;
	}
	public void setTextureManager(TextureManager textureManager) {
		this.textureMgr = textureManager;
	}
}
