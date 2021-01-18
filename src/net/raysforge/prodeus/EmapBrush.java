package net.raysforge.prodeus;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.q2.bsp.Vertex;

public class EmapBrush {

	int parent = -1;
	int layer = -1;
	String pos = "0,0,0";
	
	public List<Vertex> points = new ArrayList<Vertex>();
	
	List<Map.Entry<Integer, Integer>> edges; // ignored for now
	
	public List<EmapFace> faces = new ArrayList<EmapFace>(); 
	
	public List<Vertex> getPoints() {
		return points;
	}

	public List<EmapFace> getFaces() {
		return faces;
	}

	public void addPoint(float x, float y, float z) {
		points.add(new Vertex(x,y,z));
	}

	public void addPoint(double x, double y, double z) {
		points.add(new Vertex((float)x,(float)y,(float)z));
	}
}
