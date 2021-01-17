package net.raysforge.q3.map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Q3Map2Obj {
	
	public static void map2obj(String mapFile, String objFile) {
		try ( FileWriter fw = new FileWriter(objFile)) {

			MapParser mapParser = new MapParser(mapFile);
					
			List<Brush> brushList = mapParser.getAllBrushes();
			
			for (Brush brush : brushList) {
				List<Plane> planes = brush.planes;
				for (Plane plane : planes) {
					plane.hashCode();
				}
			}

			fw.write( "o test\n");
			for (Brush brush : brushList) {
				List<List<Point>> polys = brush.getPolygons();
				for (List<Point> polygon : polys) {
					for (Point p : polygon) {
						fw.write( "v " + p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
					}
				}
			}

			int vertices=1;
			for (Brush brush : brushList) {
				List<List<Point>> polys = brush.polys;
				for (List<Point> polygon : polys) {
					for (int i = 1; i < polygon.size()-1; i++) {
						fw.write( "f " + (vertices) + " " + (i+vertices) + " " + (i+vertices+1) + "\n") ;
					}
					vertices+=polygon.size();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
