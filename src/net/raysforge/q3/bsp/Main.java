package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
	
	public void obsolete( BSPReader bsp, BSPWriter bspWriter) throws IOException {
		bspWriter.writeArrayAsJSON( bsp.getLeafBrushes(), "q3dm17.leafbrushes");
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
	
	public static void main1(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
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

		try ( FileWriter fw = new FileWriter("q3dm17.obj")) {
			
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

	public static void main4(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		byte[] lump = bsp.getLump(LumpTypes.Lightmaps);
		
		System.out.println(lump.length / (128*128*3));
		
		Shader[] textures = bsp.getShaders();
		for (Shader texture : textures) {
			System.out.println(texture.shader);
		}
	}
	
	public static void main22(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		BSPWriter bspWriter = new BSPWriter( basePath);
		
		writeBasics(bsp, bspWriter);

		writeFile( basePath + "q3dm17.brushes", bsp.getLump(LumpTypes.Brushes));
		writeFile( basePath + "q3dm17.brushsides", bsp.getLump(LumpTypes.BrushSides));
		writeFile( basePath + "q3dm17.leafbrushes", bsp.getLump(LumpTypes.LeafBrushes));
		writeFile( basePath + "q3dm17.leafs", bsp.getLump(LumpTypes.Leafs));
		writeFile( basePath + "q3dm17.nodes", bsp.getLump(LumpTypes.Nodes));
		writeFile( basePath + "q3dm17.planes", bsp.getLump(LumpTypes.Planes));
		
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
	}

	public static void writeBasics(BSPReader bsp, BSPWriter bspWriter) throws IOException {
		Surface[] faces = bsp.getSurfaces();
		List<Vertex> verts = bsp.getDrawVerts();
		List<Integer> meshVerts = bsp.getDrawIndexes();

		for (Surface face : faces) {
			if( face.surfaceType == 2) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, verts, meshVerts, 10);
			}
		}

		bspWriter.writeVerts( verts, "q3dm17.verts");

		Shader[] textures = bsp.getShaders();
		bspWriter.writeObjectAsJSON( textures, "q3dm17.textures");

		bspWriter.writeIndices( faces, meshVerts, textures, "q3dm17.indices");
		bspWriter.writeNormals( verts, "q3dm17.normals");
		bspWriter.writeTexCoords( verts, "q3dm17.texCoords");
		bspWriter.writeLmCoords( verts, "q3dm17.lmCoords");
		bspWriter.writeColors( verts, "q3dm17.colors");

	}

}
