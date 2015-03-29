package game;

import java.io.File;
import java.util.Random;

import objects.BasePart;
import objects.Container;
import objects.Mesh;
import objects.Script;
import objects.Terrain;
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
	public static final Random caveRandom = new Random(seed);
	
	public static void main(String[] args) {
		String version = "v" +  MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
		
		camera.lookAt(0.1f, 0.1f, 10f, 0, 0, 0, 0, 1.0f, 0);
		
		BasePart floor = new BasePart(game);
		floor.color = new Color3f(5, 5, 5);
		floor.position = new Vector3f(0, -8, 0);
		new Mesh("3DModels/Plane.obj", floor);
		
		BasePart bp = new BasePart(game);
		bp.color = new Color3f(5, 0, 0);
		bp.position = new Vector3f(-3, 0, 0);
		new Mesh("3DModels/Dice.obj", bp);
		
		BasePart bp2 = new BasePart(game);
		bp2.color = new Color3f(0, 5, 0);
		bp2.position = new Vector3f(3, 0, 0);
		new Mesh("3DModels/MonkeyHead.obj", bp2);
		
		BasePart bp3 = new BasePart(game);
		bp3.color = new Color3f(0, 5, 5);
		bp3.position = new Vector3f(0, 0, 0);
		new Mesh("3DModels/Sphere.obj", bp3);

		Script script = new Script(new File("scripts/test.lua"));
		
		Terrain terrain = new Terrain(128, 128, 1, 0.4f, caveRandom);
		
		//System.out.println(new CFrame(new CFrame(1, 7, 4).p, new CFrame(10, 43, 7).p));
		
		window.create(version, WIDTH, HEIGHT);
	}
}
