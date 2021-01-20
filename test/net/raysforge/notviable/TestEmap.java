package net.raysforge.notviable;

import net.raysforge.prodeus.Emap;
import net.raysforge.prodeus.EmapBrush;
import net.raysforge.prodeus.EmapFace;
import net.raysforge.prodeus.ProdeusFolders;

public class TestEmap {

	public static void main(String[] args) {
		
		
		Emap emap = new Emap();
		
		EmapBrush brush = new EmapBrush();
		
		brush.addPoint(-100,0,100);
		brush.addPoint(0,0,200);
		brush.addPoint(100,0,100);
		brush.addPoint(100,0,-100);
		brush.addPoint(-100,0,-100);
		
		EmapFace face = new EmapFace(0, "");
		face.points.add(0);
		//face.points.add(1);
		face.points.add(2);
		face.points.add(3);
		face.points.add(4);
		brush.faces.add(face);
		
		emap.addBrush(brush);
		
		emap.writeEMap(ProdeusFolders.Maps+"gore.emap");
	}

}
