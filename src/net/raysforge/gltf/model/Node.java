package net.raysforge.gltf.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public List<Node> children = new ArrayList<Node>();
	public Mesh mesh;

	public Node(Mesh mesh) {
		this.mesh = mesh;
	}

}
