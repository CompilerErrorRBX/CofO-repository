package artillery;

import physics.Mathp;
import physics.Physics2D;
import util.Vector2f;
import util.Vector2i;
import artillery.Window;

public class Main {
	private static final int PATCH_VERSION = 0;
	private static final int MINOR_VERSION = 1;
	private static final int MAJOR_VERSION = 0;
	public static final int WIDTH = 800; // Screen width
	public static final int HEIGHT = 800; // Screen height
	
	public static Terrain terrain; 
	
	public static Physics2D physics;
	
	public static void main(String[] args) {
		String version = "v" +  MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
		
		int maximumHeight = 65;
		int bckgrndTerrainShift = (int) ((Math.random() * maximumHeight) * 2) - maximumHeight; // Creating a random number between -maximumHeight and maximumHeight
		
		Window window = new Window(); // Create a new window instance.
		
		physics = new Physics2D(Mathp.DT, 10); // Initialize physics.
		
		terrain = new Terrain(1f, 1f, 0, bckgrndTerrainShift, 3, new Vector2i(0, 70)); // Create new terrain instance.
		Terrain bckgrndTerrain = new Terrain(1f, 1f, -0.5f, bckgrndTerrainShift + 10, 5, new Vector2i(0, 100)); // Create the background terrain.
		Terrain bckgrndTerrain2 = new Terrain(1f, 1f, -0.8f, bckgrndTerrainShift + 15, 5, new Vector2i(0, 110)); // Create the background terrain.
		
		Sky sky = new Sky();
		
		Player player = new Player();
		
		Instance block2 = new Instance(new Vector2f(200, 150), new Vector2f(20, 5), new Vector2f(-20, 5), new Vector2f(-20, -5), new Vector2f(20, -5));
		block2.setAnchored(true);
		
		Instance block = new Instance(new Vector2f(200, 300), new Vector2f(5, 5), new Vector2f(-5, 5), new Vector2f(-5, -5), new Vector2f(5, -5));
		
		// We add a Base to the window of type Terrain. 
		// Terrain is a subclass of Base and all mouse 
		// and keyboard events are passed through the
		// window class into the Base class.
		window.addBase(sky);
		window.addBase(bckgrndTerrain2);
		window.addBase(bckgrndTerrain);
		window.addBase(terrain);
		window.addBase(player);
		window.addBase(block2);
		window.addBase(block);
		
		window.create("Artillery " + version, WIDTH, HEIGHT); // Create the window with a title and version. Give it a width and height. 
		
	}
}
