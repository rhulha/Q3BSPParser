package net.raysforge.q3.bsp;

import static net.raysforge.q3.bsp.DecimalFormater.f;

import java.io.IOException;
import java.util.List;

import net.raysforge.q3.map.Plane;

public class BSPVerifier {

	public static void verifyValues(ReadBSP bsp) throws IOException {
		Plane[] planes = bsp.getPlanes();
		for (Plane plane : planes) {
			String format = f(plane.normal.x);
			System.out.println("XXX: " + format + " q3bsp.js:133");
		}
		List<Vertex> vertexes = bsp.getVertexes();
		for (Vertex v : vertexes) {
			String s = v.toString();
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
		List<Integer> meshverts = bsp.getMeshVerts();
		for (int i : meshverts) {
			System.out.print(i + ",");
		}
		Face[] faces = bsp.getFaces();
		for (Face fc : faces) {
			String s = fc.toString();
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
	}

}
