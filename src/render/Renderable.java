package render;

import game.Main;

import javax.media.opengl.GLAutoDrawable;

import render.Camera;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

public interface Renderable {
	Camera camera = Main.camera;
	
	public enum DrawMode {
		DM_3D,
		DM_2D
	}
	
	DrawMode drawMode = DrawMode.DM_3D;
	
	// Keyboard Events
	public boolean keyPressed(KeyEvent key);
	public boolean keyReleased(KeyEvent key);
	
	// Mouse Events
	public boolean mouseClicked(MouseEvent event);
	public boolean mouseDragged(MouseEvent event);
	public boolean mouseEntered(MouseEvent event);
	public boolean mouseExited(MouseEvent event);
	public boolean mouseMoved(MouseEvent event);
	public boolean mousePressed(MouseEvent event);
	public boolean mouseReleased(MouseEvent event);
	public boolean mouseWheelMoved(MouseEvent event);
	
	// Main methods
	public void render(GLAutoDrawable drawable);
	public void init(Window win, GLAutoDrawable drawable);
}
