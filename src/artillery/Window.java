package artillery;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;

import artillery.Base.DrawMode;

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
	static GLU glu = new GLU();
	
	public static GLAutoDrawable drawable;
	
	private Vector2i mouseCoords = new Vector2i();
	private Vector3f mouseToWorldSpace = new Vector3f();
	
	private TextureManager textureMgr = new TextureManager();
	
	// Window size (width,height)
	Vector2i vSize2i = new Vector2i();
	
	public static final int FPS = 30; // Setting the refresh rate for our window.

	public ArrayList<Base> baseList = new ArrayList<Base>();
	public ArrayList<Base> baseEventList = new ArrayList<Base>();
	
	public Window() {
		System.out.println("Initilizing window.");
	}
	
	public void create(String title, int width, int height) {
		glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);

		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setSize(width, height);
		glWindow.setVisible(true);
		glWindow.setTitle(title);
		
		vSize2i = new Vector2i(width, height);
		
		glWindow.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				for (Base base : baseEventList) {
					base.keyPressed(event);
				}
			}

			@Override
			public void keyReleased(KeyEvent event) {
				for (Base base : baseEventList) {
					base.keyReleased(event);
				}
			}
		});
		
		glWindow.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event) {
				for (Vector2i node : Terrain.fractal) {
					//System.out.println(new Vector2i(node.x - ((int) mouseToWorldSpace.x + (Main.WIDTH/2)), node.y - ((int) mouseToWorldSpace.y) + (Main.HEIGHT/2)).magnitude());
					System.out.println(node.x + " -> " + (mouseToWorldSpace.x + (Main.WIDTH/2)));
					System.out.println(node.y + " -> " + (mouseToWorldSpace.z + (Main.HEIGHT/2)));
					if (new Vector2i(node.x - ((int) mouseToWorldSpace.x + (Main.WIDTH/2)), node.y - ((int) mouseToWorldSpace.z) - (Main.HEIGHT/2)).magnitude() < 25) {
						System.out.println("Move node.");
					}
				}
				for (Base base : baseEventList) {
					if(!base.mouseClicked(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mouseDragged(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mouseEntered(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mouseExited(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				mouseCoords = new Vector2i(event.getX(), event.getY());
				for (Base base : baseEventList) {
					if(!base.mouseMoved(event)) {
						return;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mousePressed(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mouseReleased(event)) {
						return;
					}
				}
			}

			@Override
			public void mouseWheelMoved(MouseEvent event) {
				for (Base base : baseEventList) {
					if(!base.mouseWheelMoved(event)) {
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
		
		FPSAnimator animator = new FPSAnimator(glWindow, FPS);
		animator.start();
	}
	
	public Vector2i getSize() {
		return vSize2i;
	}
	
	public Vector3f ScreenSpaceToOpenGL(int x, int y, float depth, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		 
	    int[] viewport = new int[4];
	    double[] modelview = new double[16];
	    double[] projection = new double[16];
	    float winY;
	    double wcoord[] = new double[4];// wx, wy, wz;// returned xyz coords
	    FloatBuffer buffer = FloatBuffer.allocate(4);
	 
	    gl.glGetDoublev( GL2.GL_MODELVIEW_MATRIX, modelview, 0);
	    gl.glGetDoublev( GL2.GL_PROJECTION_MATRIX, projection, 0);
	    gl.glGetIntegerv( GL2.GL_VIEWPORT, viewport, 0);
	 
	    winY = (float)viewport[3] - (float)y;
	 
	    gl.glReadPixels(x, (int) (winY), 1, 1, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, buffer);

	    glu.gluUnProject((double) x, (double) y, buffer.array()[0],
	              modelview, 0,
	              projection, 0,
	              viewport, 0, 
	              wcoord, 0);
	 
	    return new Vector3f((float) wcoord[0], (float) wcoord[1], (float) wcoord[2]);
	}

	public void addBase(Base obj, int eventIndex) {
		if (!baseList.contains(obj)) {
			obj.setWindow(this);
			obj.setTextureManager(textureMgr);
			
			baseList.add(obj);
			baseEventList.add(eventIndex, obj);
		}
	}
	
	public void addBase(Base obj) {
		if (!baseEventList.contains(obj)) {
			
			obj.setWindow(this);
			obj.setTextureManager(textureMgr);
			
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
		
		mouseToWorldSpace = ScreenSpaceToOpenGL(mouseCoords.x, mouseCoords.y, 1.0f, drawable);
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		for (Base base : baseList) {
			
			if(base.getDrawMode() == DrawMode.DM_3D)
            {
				float aspect = ((float) vSize2i.x / (float) vSize2i.y);

				gl.glMatrixMode(GL2.GL_PROJECTION);
				gl.glLoadIdentity();
				glu.gluPerspective(55, aspect, 0.1, 1000);
				gl.glMatrixMode(GL2.GL_MODELVIEW);
                gl.glLoadIdentity() ;
            }

            if(base.getDrawMode() == DrawMode.DM_2D)
            {
                gl.glLoadIdentity(); // Clear transformations that might affect this
                gl.glMatrixMode(GL2.GL_PROJECTION);
                gl.glLoadIdentity();
                glu.gluOrtho2D(0.0, vSize2i.x, 0.0, vSize2i.y);
                gl.glMatrixMode(GL2.GL_MODELVIEW);
                gl.glLoadIdentity();
            }
			
			gl.glPushMatrix();
			
			if(base.getDrawMode() == DrawMode.DM_3D) {
				glu.gluLookAt(
						base.getCamera().getEye().x, base.getCamera().getEye().y, base.getCamera().getEye().z, 
						base.getCamera().getTarget().x, base.getCamera().getTarget().y, base.getCamera().getTarget().z, 
						base.getCamera().getUp().x, base.getCamera().getUp().y, base.getCamera().getUp().z);
			}
			
			// Render base object
			base.render(drawable);
			
			gl.glPopMatrix();
		}
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable pDrawable) {
		drawable = pDrawable;
		
		// Initialize base objects
		for (Base base : baseList) {
			base.init(this, drawable);
		};
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		
		vSize2i = new Vector2i(width, height);

		float aspect = ((float) width / (float) height);
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		try {
			glu.gluPerspective(55, aspect, 0.1, 1000);
		} catch (GLException e) {
			System.out.println("Failed to resize, " + aspect);
		}
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);

		gl.glShadeModel(GL2.GL_SMOOTH);
	
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glClearDepth(1.0f);

		gl.glLineWidth(2.0f);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_BLEND);
		
		gl.glEnable( GL.GL_TEXTURE_2D );
	}
}
