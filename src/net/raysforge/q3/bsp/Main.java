package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class Main {
	
	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		
		Node[] nodes = bsp.getNodes();
		Gson gson = new Gson();
		String json = gson.toJson(nodes);
		json = json.replace("},{", "},\n{");
		System.out.println(json);
		
		
		//writeBasics(bsp);
		
	}

	public static void writeBasics(ReadBSP bsp) throws IOException {
		Face[] faces = bsp.getFaces();
		Texture[] textures = bsp.getTextures();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		BSPUtils.writeVerts( verts, "C:\\Users\\Ray\\dart\\Web3GL17\\web\\q3dm17.verts");

		BSPUtils.writeIndices( faces, meshVerts, textures, "C:\\Users\\Ray\\dart\\Web3GL17\\web\\q3dm17.indices");
	}

}
