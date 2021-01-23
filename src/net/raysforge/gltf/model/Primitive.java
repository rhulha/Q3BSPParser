package net.raysforge.gltf.model;

import java.util.ArrayList;
import java.util.List;

import net.raysforge.gltf.GltfConstants;

public class Primitive {
	
	public List<Attribute> attributes = new ArrayList<Attribute>();  
	
	public Accessor indices;
	public int material=-1;
	public int mode = GltfConstants.GL_TRIANGLES;
	
	public void addAttribute(Attribute attr) {
		attributes.add(attr);
		
	}

	public void setIndices(Accessor a) {
		indices=a;
	}

}
