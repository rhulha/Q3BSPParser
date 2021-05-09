package net.raysforge.gltf.model;

import java.util.ArrayList;
import java.util.List;

import net.raysforge.gltf.GltfConstants;

public class Primitive {
	
	public List<Attribute> attributes = new ArrayList<Attribute>();  
	
	public Accessor indices;
	public Material material;
	public int mode = GltfConstants.GL_TRIANGLES;
	
	public void addAttribute(Attribute attr) {
		attributes.add(attr);
		
	}

	public void setIndices(Accessor a) {
		indices=a;
	}

	public void setMaterial(Material material) {
		this.material=material;
		
	}

}
