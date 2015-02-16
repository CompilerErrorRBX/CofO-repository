package artillery;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import util.Node;
import util.Vector2i;
import util.Vector3f;
import artillery.Window;

public class Terrain extends Base {

	public static int mapSize = 500; // Map size.
	private int index = 0; // Don't worry about this :)
	
	private Vector2i baseOffset = new Vector2i(0, 5); // Our base offset variable.
	
	public ArrayList<Node> fractal = new ArrayList<Node>();
	// Our "roughness" constant.
	private float H = 1f; // This is the factor used to decrement our persistence value (The higher this value the smoother the terrain.)
	private int initialHeight = (int) (Math.random() * 80) + 25;
	private float zIndex = 0;
	private int fractals = 6; // Number of times to fracture our line
	private float persistence = 1f;
	public static Vector3f offset = new Vector3f(-mapSize / 2f, 0.0f, (mapSize / 2.0f) - 5); // Create an offset so that we're centered on the camera.
	
	public Terrain(float roughness, float pPersistence, float pZIndex, int pIntitialHeight, int pFractals, Vector2i pBaseOffset) {
		H = roughness;
		initialHeight = pIntitialHeight;
		fractals = pFractals;
		persistence = pPersistence;
		zIndex = pZIndex;
		baseOffset = pBaseOffset;
	}
	
	public Terrain() {}
	
	public Vector2i getTerrainAt(int x, int y) { // x and y coordinate of the tank for instance.
		Vector2i intercept = new Vector2i(x, y);
		for (int i = 1; i < fractal.size(); i++) { // Iterate through the fractal map to find a line that intersects our line over our position.
			intercept = Vector2i.getLineIntercept(new Vector2i(x, y), new Vector2i(x, 20), fractal.get(i-1).position, fractal.get(i).position); // Check for intersection.
			if (intercept != null) return intercept; // Found a line that was intersected. Return the point of intercept.
		}
		return intercept; // No line collision... Don't let the tank move.
	}
	
	public ArrayList<Node> getNearbyNodes(Vector2i at, int radius) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Node node : fractal) { // Iterate through the fractal map to find a line that intersects our line over our position.
			if (at.magnitude(node.position) <= radius) {
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	private void createFractal(int fractures, float persistence) {
		// Initialize fractal
		fractal = new ArrayList<Node>();
		int screenSize = Main.WIDTH - mapSize / 2;
		fractal.add(new Node(-150, baseOffset.y, 0)); // Add a point on the left side of the window.
		fractal.add(new Node((screenSize/2) + baseOffset.x, baseOffset.y + initialHeight, 0)); // Add a point in the center of the window shifted over by offset.x.
		fractal.add(new Node(screenSize, baseOffset.y, 0)); // Add a point on the right side of the window.
		for (int n = 0; n < fractures; n++) {
			// Create fractures.
			for (int i = 1; i < fractal.size(); i++) {
				Node node1 = fractal.get(i-1); // Get the previous point
				Node node2 = fractal.get(i); // Get the current point
				float randomness = (float) (((Math.random() * persistence * 2) - persistence)*40); // Create a randomness factor
				fractal.add(i, new Node((node1.x + node2.x)/2, (int) (((node1.y + node2.y)/2) + randomness), 0)); // Place a new point between the two other points
				// and add the randomness.
				i++; // Increment 'i' an extra time.
			}
			persistence /= Math.pow(2, H); // Reduce the persistence by our H factor.
		}
		
	}
	
	private void drawTerrainSquare(GLAutoDrawable drawable, Vector2i p1, Vector2i p2) {
		GL2 gl = drawable.getGL().getGL2();
		
		int bottom = 20;
		
		
		gl.glColor3f(0.5f, 2.5f + zIndex, 1f); // Setting color to green.
		
		gl.glBegin(GL2.GL_TRIANGLES); // Telling OpenGL we're drawing a triangle.
		
		// Drawing the first triangle.
		
//		Vector2i potentialBottom = getTerrainAt(p1.x, p1.y-1);
//		if (potentialBottom != null) bottom = potentialBottom.y;
		
		gl.glVertex3f(p1.x, 1f * zIndex, -p1.y); // Create a vertex at our first node's position.
		gl.glVertex3f(p2.x, 1f * zIndex, -p2.y); // Create a vertex at our second node's position.
		gl.glColor3f(0.5f, 1.5f + zIndex, 0.5f); // Select a darker color.
		gl.glVertex3f(p1.x, 1f * zIndex, bottom); // Go to the bottom of the screen.
	 
		// Drawing the second triangle.
		
//		potentialBottom = getTerrainAt(p2.x, p2.y);
//		if (potentialBottom != null) bottom = potentialBottom.y;
		
		gl.glColor3f(0.5f, 2.5f + zIndex, 1f); // Reset to initial color.
		
		gl.glVertex3f(p2.x, 1f * zIndex, -p2.y); // Create a vertex at our second node's position.
		gl.glColor3f(0.5f, 1.5f + zIndex, 0.5f); // Select a darker color.
		gl.glVertex3f(p2.x, 1f * zIndex, bottom); // Go to the bottom of the screen.
		gl.glVertex3f(p1.x, 1f * zIndex, bottom); // Go to the bottom of the screen.
		
		gl.glEnd(); // Telling OpenGL we're done drawing.
	}
	
	public void init(Window win, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		index = gl.glGenLists(1); // Create a draw list.
		
		createFractal(fractals, persistence); // Create our fractal map.
		
		gl.glNewList(index, GL2.GL_COMPILE); // Tell OpenGL we want to start a new draw list.
		
		gl.glEndList(); // Tell OpenGL we're done making our draw list.

		this.getCamera().set(new Vector3f(0, mapSize, 0.2f),
				new Vector3f(0, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f)); // Set the camera's position.
	}

	@Override
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glCallList(index); // Render the draw list.
		
		gl.glPushMatrix(); // Push the coordinate matrix.
		gl.glTranslatef(offset.x, offset.y, offset.z); // Translate the coordinate matrix by our offset.
		
		for (int i = 1; i < fractal.size(); i++) { // Increment through the fractal map.
			Node node1 = fractal.get(i); // Get the current node
			Node node2 = fractal.get(i-1); // Get the previous node
			
			if (zIndex == 0) {
			
				gl.glBegin(GL2.GL_QUADS);
				
				gl.glColor3f(5, 5, 5);
				gl.glVertex3f(node1.position.x, 2f, -node1.position.y);
				gl.glVertex3f(node1.position.x + 5, 2f, -node1.position.y);
				gl.glVertex3f(node1.position.x + 5, 2f, -node1.position.y + 5);
				gl.glVertex3f(node1.position.x, 2f, -node1.position.y + 5);
				
				gl.glEnd();
				
			}
			
			drawTerrainSquare(drawable, node2.position, node1.position); // Draw the terrain square between those two nodes.
		}
		
		gl.glPopMatrix(); // Return the matrix to it's original coordinate plane.
		
	}
}
