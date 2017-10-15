package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
	
	public void obsolete( BSPReader bsp, BSPWriter bspWriter) throws IOException {
		bspWriter.writeArrayAsJSON( bsp.getLeafbrushes(), "q3dm17.leafbrushes");
		bspWriter.writeArrayAsJSON( bsp.getBrushSides(), "q3dm17.brushsides");
		bspWriter.writeArrayAsJSON( bsp.getBrushes(), "q3dm17.brushes");
		bspWriter.writeObjectAsJSON( bsp.getLeafs(), "q3dm17.leafs");
		bspWriter.writePlanesAsJSON( bsp.getPlanes(), "q3dm17.planes");
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
		bspWriter.writeNodesAsJSON( bsp.getNodes(), "q3dm17.nodes");
	}
	
	public static void writeFile( String fn, byte[] bytes) {
		try ( FileOutputStream fos = new FileOutputStream( fn)) {
			fos.write( bytes );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		Texture[] textures = bsp.getTextures();

		Face[] faces = bsp.getFaces();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");

		for (Face face : faces) {
			if( face.type == 2) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, verts, meshVerts, 10);
			}
		}

		try ( FileWriter fw = new FileWriter("q3dm17.obj")) {
			
			fw.write("o q3dm17\n");
			
			for (Vertex vertex : verts) {
				fw.write( "v "+vertex.position.x+" "+vertex.position.y+" "+vertex.position.z +"\n");
			}
			for (Face face : faces) {
				if( skip.contains( textures[face.texture].name))
					continue;
				for(int k = 0; k < face.n_meshverts; k+=3) {
					int i1 = face.vertex + meshVerts.get(face.meshvert + k)+1;
					int i2 = face.vertex + meshVerts.get(face.meshvert + k+1)+1;
					int i3 = face.vertex + meshVerts.get(face.meshvert + k+2)+1;
					fw.write( "f "+i1+" "+i2+" "+i3 +"\n");
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main2(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		BSPWriter bspWriter = new BSPWriter( basePath);
		
		writeBasics(bsp, bspWriter);

		writeFile( basePath + "q3dm17.brushes", bsp.getLump(LumpTypes.Brushes));
		writeFile( basePath + "q3dm17.brushsides", bsp.getLump(LumpTypes.Brushsides));
		writeFile( basePath + "q3dm17.leafbrushes", bsp.getLump(LumpTypes.Leafbrushes));
		writeFile( basePath + "q3dm17.leafs", bsp.getLump(LumpTypes.Leafs));
		writeFile( basePath + "q3dm17.nodes", bsp.getLump(LumpTypes.Nodes));
		writeFile( basePath + "q3dm17.planes", bsp.getLump(LumpTypes.Planes));
		
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
	}

	public static void writeBasics(BSPReader bsp, BSPWriter bspWriter) throws IOException {
		Face[] faces = bsp.getFaces();
		List<Vertex> verts = bsp.getVertexes();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, verts, meshVerts, 10);
			}
		}

		bspWriter.writeVerts( verts, "q3dm17.verts");

		Texture[] textures = bsp.getTextures();
		bspWriter.writeObjectAsJSON( textures, "q3dm17.textures");

		bspWriter.writeIndices( faces, meshVerts, textures, "q3dm17.indices");
		bspWriter.writeNormals( verts, "q3dm17.normals");

	}

}
