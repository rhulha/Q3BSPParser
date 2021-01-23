package net.raysforge.bsp.q3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJExporter {
	
	// "q3dm17.obj"
	public static void export(Q3BSPReader bsp, String exportFileName) throws IOException {
		Shader[] textures = bsp.getShaders();

		Surface[] faces = bsp.getSurfaces();
		List<Vertex> verts = bsp.getDrawVerts();
		List<Integer> meshVerts = bsp.getDrawIndexes();

		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");

		for (Surface face : faces) {
			if( face.surfaceType == 2) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, verts, meshVerts, 10);
			}
		}

		try ( FileWriter fw = new FileWriter(exportFileName)) {
			
			fw.write("o q3dm17\n");
			
			for (Vertex vertex : verts) {
				fw.write( "v "+vertex.xyz.x+" "+vertex.xyz.y+" "+vertex.xyz.z +"\n");
			}
			for (Surface face : faces) {
				if( skip.contains( textures[face.shaderNum].shader))
					continue;
				for(int k = 0; k < face.numIndexes; k+=3) {
					int i1 = face.firstVert + meshVerts.get(face.firstIndex + k)+1;
					int i2 = face.firstVert + meshVerts.get(face.firstIndex + k+1)+1;
					int i3 = face.firstVert + meshVerts.get(face.firstIndex + k+2)+1;
					fw.write( "f "+i1+" "+i2+" "+i3 +"\n");
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
