package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.ArrayList;
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

		BSPUtils.writeVerts( verts, "C:\\Users\\Ray\\dart\\Web3GL17\\web\\q3dm17.verts");

		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");

		
		BSPUtils.writeIndices( faces, meshVerts, textures, "C:\\Users\\Ray\\dart\\Web3GL17\\web\\q3dm17.indices", skip);
		
	}

}
