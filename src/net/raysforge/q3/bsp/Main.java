package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Web3GL17\\web\\";
	
	public static void main(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		BSPWriter.writeArrayAsJSON( bsp.getBrushSides(), "q3dm17.brushsides");

		BSPWriter.writeArrayAsJSON( bsp.getBrushes(), "q3dm17.brushes");

		//BSPWriter.writeObjectAsJSON( bsp.getLeafs(), "q3dm17.leafs");
		
		//BSPWriter.writePlanesAsJSON( bsp.getPlanes(), "q3dm17.planes");
		
		//BSPWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
		
		//BSPWriter.writeNodesAsJSON( bsp.getNodes(), basePath +"q3dm17.nodes");
		
		
		//writeBasics(bsp);
		
	}

	public static void writeBasics(BSPReader bsp) throws IOException {
		Face[] faces = bsp.getFaces();
		Texture[] textures = bsp.getTextures();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		BSPWriter.writeVerts( verts, basePath +"q3dm17.verts");

		BSPWriter.writeIndices( faces, meshVerts, textures, basePath +"q3dm17.indices");
	}

}
