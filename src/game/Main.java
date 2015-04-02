package game;

import java.io.File;

import objects.BasePart;
import objects.Container;
import objects.Mesh;
import objects.Script;
import render.Camera;
import render.TextureManager;
import render.Window;
import utility.Color3f;
import utility.Vector3f;

public class Main {
	private static final int PATCH_VERSION = 0;
	private static final int MINOR_VERSION = 1;
	private static final int MAJOR_VERSION = 0;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public static final int FPS = 60;

	public static final Container game = new Container();
	
	public static Camera camera = new Camera();
	public static TextureManager textureLoader = new TextureManager();
	
	public static final Window window = new Window();
	
	public static Vector3f worldSpaceOffset = new Vector3f();
	
	public static final int seed = (int) Math.abs(Math.random()*10000); // A random number to use with our pseudo-random number generator.
	
	public static void main(String[] args) {
		game.name = "Game";
		String version = "v" +  MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
		
		camera.lookAt(0.1f, 0.1f, 10f, 0, 0, 0, 0, 1.0f, 0);
		
		BasePart floor = new BasePart(game);
		floor.color = new Color3f(5, 5, 5);
		floor.position = new Vector3f(0, -8, 0);
		new Mesh("3DModels/Plane.obj", floor);
		
		window.create(version, WIDTH, HEIGHT);
		
		new Script(new File("scripts/test.lua"));
	}
}
