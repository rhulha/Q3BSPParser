package net.raysforge.prodeus;

public class TestEmap {

	public static void main(String[] args) {
		
		
		Emap emap = new Emap();
		
		EmapBrush brush = new EmapBrush();
		
		brush.addPoint(-100,0,100);
		brush.addPoint(0,0,200);
		brush.addPoint(100,0,100);
		brush.addPoint(100,0,-100);
		brush.addPoint(-100,0,-100);
		
		EmapFace face = new EmapFace();
		face.points.add(0);
		//face.points.add(1);
		face.points.add(2);
		face.points.add(3);
		face.points.add(4);
		brush.faces.add(face);
		
		emap.addBrush(brush);
		
		emap.writeMap(ProdeusMapFolder.IS+"gore.emap");
	}

}
