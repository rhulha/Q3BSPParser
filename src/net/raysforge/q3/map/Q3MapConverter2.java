package net.raysforge.q3.map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Q3MapConverter2 {
	

	public static void main(String[] args) throws IOException {

		try ( MapParser mapParser = new MapParser("q3dm17sample.map"); FileWriter fw = new FileWriter("q3dm17sample.obj")) {

			List<Brush> brushList = mapParser.getAllBrushes();
			
			for (Brush brush : brushList) {
				List<Plane> planes = brush.planes;
				for (Plane plane : planes) {
					plane.hashCode();
				}
			}

			fw.write( "o test\n");
			for (Brush brush : brushList) {
				List<List<Point>> polys = brush.calculatePolygons();
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
