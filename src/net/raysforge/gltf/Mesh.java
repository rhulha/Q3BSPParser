package net.raysforge.gltf;

import java.util.ArrayList;
import java.util.List;

public class Mesh {

	public List<Primitive> primitives = new ArrayList<Primitive>();
	
	public Mesh(Primitive prim) {
		primitives.add(prim);
	}


}
