
package net.raysforge.prodeus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.raysforge.q3.map.Brush;
import net.raysforge.q3.map.MapParser;
import net.raysforge.q3.map.Point;

public class TestMap2Emap {

	public static void main(String[] args) throws URISyntaxException, IOException {

		// Q2Map2Prodeus.map2prodeus("D:\\GameDev\\Tests\\base1.map",
		// "D:\\GameDev\\Tests\\base1.emap");
		TestMap2Emap.map2prodeus("D:\\GameDev\\Tests\\base1.map", ProdeusMapFolder.IS+"base1.emap");
	}

	public static void map2prodeus(String mapFile, String prodeusFile) throws URISyntaxException, IOException {

		Emap emap = new Emap();

		MapParser mapParser = new MapParser(mapFile);

		List<Brush> brushList = mapParser.getAllBrushes();
		List<Brush> brushList2Delete = new ArrayList<Brush>();
		for (Brush q2_brush : brushList) {
			if( brushHasCrazyFaces(q2_brush))
				brushList2Delete.add(q2_brush);
		}
		
		// remove faces with more than 6 points to help prodeus maybe
		for (Brush q2_brush : brushList2Delete) {
			brushList.remove(q2_brush);
		}
		
		for (Brush q2_brush : brushList) {
			EmapBrush brush = new EmapBrush();
			List<List<Point>> faces = q2_brush.getPolygons();
			for (List<Point> points : faces) {
				EmapFace face = new EmapFace();
				for (Point p : points) {
					brush.addPoint(p.x/20, p.y/20, p.z/20);
					face.points.add(brush.points.size()-1);
				}
				brush.faces.add(face);
			}
			emap.addBrush(brush);
			System.out.println("emap.brush.points.size: " + brush.points.size());
		}


		emap.writeMap(prodeusFile);

	}

	private static boolean brushHasCrazyFaces(Brush q2_brush) {
		for (List<Point> points : q2_brush.getPolygons()) {
			if(points.size()>6)
				return true;
		}
		return false;
	}

}
