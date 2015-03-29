/**
 * Purpose: To handle textures for Mining Empire
 * 
 * File: TextureManager.java
 * Created: January 19, 2015
 * Author: Daniel Martin (dlmartin921@gmail.com)
 * 
 */

package render;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureManager {
	
	private class Resource {
		Texture texture;
		String filePath;
		String fileType;
		boolean mipMap;
	}
	
	private ArrayList<Resource> list = new ArrayList<Resource>();
	
	/**
	 * Remove texture from manager by filePath
	 * 
	 * @param filePath
	 */
	public void removeTexture(String filePath) {
		for(Resource r : list) {
			if(r.filePath == filePath) {
				list.remove(r);
			}
		}
	}
	
	/**
	 * This function will load a unique texture from a resource stream
	 * 
	 * @param filePath
	 * @param fileType
	 * @param mipMap
	 * @return Texture
	 */
	public Texture textureFromResourceStream(String filePath, String fileType, boolean mipMap) {
		for(Resource r : list) {
			if(r.filePath == fileType && r.fileType == fileType && r.mipMap == mipMap) {
				return r.texture;
			}
		}
	
		InputStream stream = getClass().getResourceAsStream(filePath);
		try {
			Texture tex = TextureIO.newTexture(
					TextureIO.newTextureData(GLProfile.getDefault(), stream, mipMap, fileType));
			
			Resource r = new Resource();
			r.texture = tex;
			r.filePath = filePath;
			r.fileType = fileType;
			r.mipMap = mipMap;
			list.add(r);
			
			return tex;
		} catch (GLException | IllegalArgumentException | IOException e) {
			return null;
		}
	}
	
	/**
	 * This function will load a unique texture from file
	 * 
	 * @param filePath
	 * @param fileType
	 * @param mipMap
	 * @return Texture
	 */
	public Texture textureFromFile(String filePath, String fileType, boolean mipMap) {
		for(Resource r : list) {
			if(r.filePath == fileType && r.fileType == fileType && r.mipMap == mipMap) {
				return r.texture;
			}
		}
	
		File file = new File(filePath); 
		try {
			Texture tex = TextureIO.newTexture(
					TextureIO.newTextureData(GLProfile.getDefault(), file, mipMap, fileType));
			
			Resource r = new Resource();
			r.texture = tex;
			r.filePath = filePath;
			r.fileType = fileType;
			r.mipMap = mipMap;
			list.add(r);
			
			return tex;
		} catch (GLException | IllegalArgumentException | IOException e) {
			return null;
		}
	}
}
