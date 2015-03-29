package objects;

import java.util.ArrayList;

import utility.Vector2f;
import utility.Vector3f;

public class Container {
	public Container parent = null;
	public String name = "";
	protected ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	protected ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();
	protected ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
	protected ArrayList<Vector3f[]> faces = new ArrayList<Vector3f[]>();
	protected ArrayList<Container> children = new ArrayList<Container>();
	
	protected Texture texture;
	
	public void setParent(Container pParent) {
		if (pParent == null) {
			for (int i = 0; i < 0; i++) {
				Container obj = (Container) children.get(i);
				obj.setParent(null); 
			}
			return;
		}
		if (this.parent != null) {
			this.parent.children.remove(this);
		}
		parentModifier();
		pParent.children.add(this);
		this.parent = pParent;
	}
	
	protected void parentModifier() {
		
	}
	
	protected void parentRenderModifier() {
		
	}
}
