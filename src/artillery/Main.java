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
		
		int bckgrndTerrainShift = (int) ((Math.random() * 30) * 2) - 30; // Creating a random number between -mapSize/2 and mapSize/2
		
		System.out.println(bckgrndTerrainShift);
		
		Window window = new Window(); // Create a new window instance.
		
		Terrain terrain = new Terrain(1f, 1f, 0, bckgrndTerrainShift, 6, new Vector2i(0, 5)); // Create new terrain instance.
		Terrain bckgrndTerrain = new Terrain(1f, 1f, -0.5f, bckgrndTerrainShift + 10, 6, new Vector2i(0, 40)); // Create the background terrain.
		Terrain bckgrndTerrain2 = new Terrain(1f, 1f, -0.8f, bckgrndTerrainShift + 15, 6, new Vector2i(0, 60)); // Create the background terrain.
		
		Sky sky = new Sky();
		
		Player player = new Player();
		
		// We add a Base to the window of type Terrain. 
		// Terrain is a subclass of Base and all mouse 
		// and keyboard events are passed through the
		// window class into the Base class.
		window.addBase(sky);
		window.addBase(bckgrndTerrain2);
		window.addBase(bckgrndTerrain);
		window.addBase(terrain);
		window.addBase(player);
		
		window.create("Artillery " + version, WIDTH, HEIGHT); // Create the window with a title and version. Give it a width and height. 
		
	}
}
