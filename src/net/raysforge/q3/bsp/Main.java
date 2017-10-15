package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		
		Face[] faces = bsp.getFaces();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		BSPUtils.writeVerts( verts, "q3dm17.verts");
		BSPUtils.writeIndices( faces, meshVerts, "q3dm17.indices");
		//Face[] faces = bsp.getFaces();
		
		//bsp.getNodes();
		
	}

	public static void print_NVertexes2NMeshVertsRelationship(ReadBSP bsp) throws IOException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		Face[] faces = bsp.getFaces();
		for (Face face : faces) {
			
			String key = face.n_vertexes + " " + face.n_meshverts;
			if( ! map.containsKey(key))
				map.put(key, 0);
			int i = map.get(key);
			map.put(key , ++i);
		}

		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			System.out.println(key + ": " +  map.get(key));
		}
	}
}
