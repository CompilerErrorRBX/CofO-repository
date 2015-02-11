package artillery;

import artillery.Window;

public class Main {
	private static final int PATCH_VERSION = 0;
	private static final int MINOR_VERSION = 1;
	private static final int MAJOR_VERSION = 0;
	public static final int WIDTH = 800; // Screen width
	public static final int HEIGHT = 800; // Screen height
	
	public static void main(String[] args) {
		String version = "v" +  MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
		
		Window window = new Window(); // Create a new window instance.
		
		Terrain terrain = new Terrain(); // Create new terrain instance.
		
		Player player = new Player();
		
		// We add a Base to the window of type Terrain. 
		// Terrain is a subclass of Base and all mouse 
		// and keyboard events are passed through the
		// window class into the Base class.
		window.addBase(terrain);
		window.addBase(player);
		
		window.create("Artillery " + version, WIDTH, HEIGHT); // Create the window with a title and version. Give it a width and height. 
		
	}
}
