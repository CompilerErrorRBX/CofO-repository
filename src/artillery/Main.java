package artillery;

import artillery.Window;

public class Main {
	private static final int PATCH_VERSION = 0;
	private static final int MINOR_VERSION = 1;
	private static final int MAJOR_VERSION = 0;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	public static void main(String[] args) {
		String version = "v" +  MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
		
		Window window = new Window();
		
		Terrain terrain = new Terrain();
		
		window.addBase(terrain);
		window.create("Artillery " + version, WIDTH, HEIGHT);
		
	}
}
