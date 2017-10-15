package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		
		Face[] faces = bsp.getFaces();
		Texture[] textures = bsp.getTextures();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		BSPUtils.writeVerts( verts, "q3dm17.verts");
		BSPUtils.writeIndices( faces, meshVerts, textures, "q3dm17.indices");
		
	}

}
