package render;

import game.Main;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;

import utility.Vector2i;
import utility.Vector3f;

import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class Window implements GLEventListener {	
	static GLProfile glp;
	public static GLU glu = new GLU();
	
	static GLWindow glWindow;
	
	// Window size (width,height)
	private static Vector2i vSize2i = new Vector2i();
	
	float[] lightPos = {0, 10, 10, 1f}; // light position
	Vector3f rotation = new Vector3f(0, 0, 0);
	Vector3f position = new Vector3f(0, 0, 0);

	public static float brightness = 0.1f;
	
	private static ArrayList<Renderable> baseList = new ArrayList<Renderable>();
	public ArrayList<Renderable> baseEventList = new ArrayList<Renderable>();
	
	public Window() {
		System.out.println("Initilizing window.");
	}
	
	public void create(String title, int width, int height) {
		glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);

		glWindow = GLWindow.create(caps);
		glWindow.setSize(width, height);
		glWindow.setVisible(true);
		glWindow.setTitle(title);
		
		Main.camera.installTrackball(glWindow);
		
		vSize2i = new Vector2i(width, height);
		
		glWindow.addKeyListener(new keyListener());
		
		glWindow.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseClicked(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseDragged(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseEntered(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseExited(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseMoved(event)) {
						return;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mousePressed(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseReleased(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseWheelMoved(MouseEvent event) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.mouseWheelMoved(event)) {
						return;
					}
				}
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addWindowListener(new WindowAdapter() {
			public void windowDestroyNotify(WindowEvent arg0) {
				System.exit(0);
			};
		});
		
		FPSAnimator animator = new FPSAnimator(glWindow, Main.FPS);
		animator.start();
	}
	
	public Vector2i getSize() {
		return vSize2i;
	}
	
	public Vector3f screenToWorldSpace(int x, int y, float depth, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		 
	    int[] viewport = new int[4];
	    double[] modelview = new double[16];
	    double[] projection = new double[16];
	    float winY;
	    double wcoord[] = new double[4];
	    FloatBuffer buffer = FloatBuffer.allocate(4);
	 
	    gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelview, 0);
	    gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);
	    gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
	 
	    winY = (float)viewport[3] - (float)y;
	 
	    gl.glReadPixels(x, (int) (winY), 1, 1, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, buffer);

	    glu.gluUnProject((double) x, (double) y, buffer.array()[0],
	              modelview, 0,
	              projection, 0,
	              viewport, 0, 
	              wcoord, 0);
	 
	    return new Vector3f((float) wcoord[0], (float) wcoord[1], (float) wcoord[2]);
	}

	public void addBase(Renderable obj, int eventIndex) {
		if (!baseList.contains(obj)) {
			baseList.add(obj);
			baseEventList.add(eventIndex, obj);
		}
	}
	
	public void addBase(Renderable obj) {
		if (!baseEventList.contains(obj)) {
			baseList.add(obj);
			baseEventList.add(obj);
		}
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		render(drawable);
	}
	
	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();
		
		gl.glPushAttrib(GL2.GL_ALL_ATTRIB_BITS);
		{
			drawScene(drawable);
		}
		
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, asFloatBuffer(new float[]{lightPos[0], lightPos[1], lightPos[2], lightPos[3]}));	
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable pDrawable) {
		GLAutoDrawable drawable = pDrawable;
		GL2 gl = drawable.getGL().getGL2();
	
//		generateTextureCoordinates(drawable);
//		setUpFrameBufferObject(drawable);
//		drawShadowMap(drawable);
		
		// Initialize base objects
		for (Renderable renderable : baseList) {
			renderable.init(this, drawable);
		};
	}
	
	public static String readFromStream(File file) throws IOException {
		if (file == null) {
			throw new IOException("Could not read from stream.");
		}
		StringBuffer buffer = new StringBuffer();
		Scanner scanner = new Scanner(file);
		try {
			while (scanner.hasNextLine()) {
				buffer.append(scanner.nextLine() + "\n");
			}
		} finally {
			scanner.close();
		}

		return buffer.toString();
	}

	public void initShaders(GL2 gl) throws IOException {
		int v = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		int f = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
//		int g = gl.glCreateShader(GL2.GL_GEOMETRY_SHADER_ARB);

		String vsrc = readFromStream(new File("shaders/vertex.glsl"));
		gl.glShaderSource(v, 1, new String[] { vsrc }, (int[]) null, 0);
		gl.glCompileShader(v);

//		String gsrc = readFromStream(new File("shaders/geometry.glsl"));
//		gl.glShaderSource(g, 1, new String[] { gsrc }, (int[]) null, 0);
//		gl.glCompileShader(g);
		
		String fsrc = readFromStream(new File("shaders/fragment.glsl"));
		gl.glShaderSource(f, 1, new String[] { fsrc }, (int[]) null, 0);
		gl.glCompileShader(f);

		int shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, v);
//		gl.glAttachShader(shaderprogram, g);
		gl.glAttachShader(shaderprogram, f);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);

		gl.glUseProgram(shaderprogram);

	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		
		vSize2i = new Vector2i(width, height);

		float aspect = ((float) width / (float) height);
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		try {
			glu.gluPerspective(70, aspect, 0.1, 1000);
		} catch (GLException e) {
			System.out.println("Failed to resize, " + aspect);
		}
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glClearColor(0, 0.75f, 1, 1);
		gl.glPolygonOffset(2.5f, 0);
		
		// Handle lighting
		
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[]{0.05f, 0.05f, 0.05f}));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, asFloatBuffer(new float[]{brightness, brightness, brightness}));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, asFloatBuffer(new float[]{0.05f, 0.05f, 0.05f}));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, asFloatBuffer(new float[]{0, 0, 0, 0.5f}));
		gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_DIFFUSE);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		// Handle material 
		
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, asFloatBuffer(new float[]{1.0f, 0, 0, 1.0f}));
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, asFloatBuffer(new float[]{0, 0.1f, 0.1f, 1.0f}));
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, asFloatBuffer(new float[]{0, 1.0f, 0, 1.0f}));
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, asFloatBuffer(new float[]{0.05f, 0, 0, 1.0f}));
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 5.0f);

		// Handle culling
		
		gl.glCullFace(GL2.GL_BACK);
		
		// Handle shadowing
		
		// Clamp texture coordinates because we only want one shadow.
		// Use 'TO_EDGE' to prevent the texture boarders from affecting the shadow map through linear texture filtering.
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
		
		// Enable bilinear texture filtering. This means that the color will be a weighted value of
		// the four textures elements that are closest to the center of the pixel being textured.
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_DEPTH_TEXTURE_MODE, GL2.GL_INTENSITY);
		gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, 0.5f);
		
		gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_Q, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		
		// Handle misc
		
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glClearDepth(1.0f);
		
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	private static void drawScene(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glScalef(5, 5, 5);
		gl.glTranslatef(0, -2, 0);
		
		for (Renderable renderable : baseList) {

			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();

			Main.camera.apply(gl);

			renderable.render(drawable);

		}
		gl.glPopMatrix();
		Main.camera.apply(gl);
	}
	
	private static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer = FloatBuffer.allocate(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	public class keyListener implements KeyListener {
		int seq = 0;
		float camScrollSpeed = 0.1f;
		public keyListener() {
			seq = 0;
		}
		@Override
		public void keyPressed(KeyEvent event) {
			if (0 == ( InputEvent.AUTOREPEAT_MASK & event.getModifiers())) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.keyPressed(event)) {
						return;
					}
				}
				seq++;
			}
		}
		@Override
		public void keyReleased(KeyEvent event) {
			if (0 == ( InputEvent.AUTOREPEAT_MASK & event.getModifiers())) {
				for (Renderable renderable : baseEventList) {
					if(!renderable.keyReleased(event)) {
						return;
					}
				}
				seq++;
			}
		}
	}
}
