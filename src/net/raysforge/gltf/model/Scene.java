package net.raysforge.gltf.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	public List<Node> nodes = new ArrayList<Node>();
	
	public Scene() {
	}

	public Scene(Node node) {
		nodes.add(node);
	}

	public void addNode(Node n) {
		nodes.add(n);
		
	}


}
