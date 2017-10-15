package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Web3GL17\\web\\";
	
	public static void main(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		BSPWriter bspWriter = new BSPWriter( basePath);
		
		bspWriter.writeArrayAsJSON( bsp.getLeafbrushes(), "q3dm17.leafbrushes");
		
		bspWriter.writeArrayAsJSON( bsp.getBrushSides(), "q3dm17.brushsides");

		bspWriter.writeArrayAsJSON( bsp.getBrushes(), "q3dm17.brushes");

		bspWriter.writeObjectAsJSON( bsp.getLeafs(), "q3dm17.leafs");
		
		bspWriter.writePlanesAsJSON( bsp.getPlanes(), "q3dm17.planes");
		
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
		
		bspWriter.writeNodesAsJSON( bsp.getNodes(), "q3dm17.nodes");
		
		
		writeBasics(bsp, bspWriter);
		
	}

	public static void writeBasics(BSPReader bsp, BSPWriter bspWriter) throws IOException {
		Face[] faces = bsp.getFaces();
		Texture[] textures = bsp.getTextures();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		bspWriter.writeVerts( verts, "q3dm17.verts");

		bspWriter.writeIndices( faces, meshVerts, textures, "q3dm17.indices");
	}

}
