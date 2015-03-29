package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import utility.Vector2f;
import utility.Vector3f;

public class Mesh extends Container {
	public String name = "Mesh";
	public Mesh(String filePath, Container pParent) {
		if (objToOpenGL(filePath)) { // if the filepath leads to a valid file then
			setParent(pParent);
		}
	}
	
	public void parentRenderModifier() {
		if (parent == null) return;
		this.parent.faces = this.faces;
		this.parent.vertices = this.vertices;
		this.parent.normals = this.normals;
		this.parent.textureCoords = this.textureCoords;
	}
	
	public void setParent(Container pParent) {
		if (pParent == null) {
			for (int i = 0; i < 0; i++) {
				Container obj = children.get(i);
				obj.setParent(null);
			}
		}
		if (this.parent != null) {
			this.parent.children.remove(this);
		}
		// Add this as a child to the desired parent
		this.parent = pParent;
		if (pParent == null) return;
		pParent.children.add(this);
	}
	
	private boolean objToOpenGL(String filePath) {
		try {
			Scanner reader = new Scanner(new File(filePath));
			reader.nextLine();
			reader.nextLine();
			reader.nextLine();
			try {
				while (reader.hasNext()) {
					String type = reader.next();
					if (type.startsWith("v") && type.length() == 1) { // This is a vertex.
						vertices.add(new Vector3f(Float.parseFloat(reader.next()), Float.parseFloat(reader.next()), Float.parseFloat(reader.next())));
					} else if (type.startsWith("vt")) { // This is a texture coordinate.
						textureCoords.add(new Vector2f(Float.parseFloat(reader.next()), Float.parseFloat(reader.next())));
					} else if (type.startsWith("vn")) { // This is a texture coordinate.
						normals.add(new Vector3f(Float.parseFloat(reader.next()), Float.parseFloat(reader.next()), Float.parseFloat(reader.next())));
					} else if (type.startsWith("f")) { // This is a face.
						Vector3f[] faceArray = new Vector3f[3];
						for (int i = 0; i < 3; i++) {
							String thisLine = reader.next();
							float pX = stringToFloat(thisLine.substring(0, thisLine.indexOf("/")));
							thisLine = thisLine.substring(thisLine.indexOf("/")+1, thisLine.length());
							float pY = stringToFloat(thisLine.substring(0, thisLine.indexOf("/")));
							thisLine = thisLine.substring(thisLine.indexOf("/")+1, thisLine.length());
							float pZ = stringToFloat(thisLine.substring(0, thisLine.length()));
							faceArray[i] = new Vector3f(pX, pY, pZ);
						}
						faces.add(faceArray);
					}
				}
				reader.close();
				return true;
			} catch (Exception e) {
				System.out.println("File: " + filePath + " is in an incorrect format.");
				e.printStackTrace();
				return false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File: " + filePath + " does not exist!");
			return false;
		}
	}

	private float stringToFloat(String str) {
		float f = -1;
		try {
			f = Float.parseFloat(str);
		} catch (Exception e) {
			
		}
		return f;
	}
	
}
