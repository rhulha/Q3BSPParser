package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
	
	public static void test() throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		byte[] lump = bsp.getLump(LumpTypes.Lightmaps);
		
		System.out.println(lump.length / (128*128*3));
		
		Shader[] textures = bsp.getShaders();
		for (Shader texture : textures) {
			System.out.println(texture.shader);
		}
	}
	
	public static void main(String[] args) throws IOException {

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
		List<Vertex> vertexes = bsp.getDrawVerts();
		List<Integer> indexes = bsp.getDrawIndexes();

		for (Surface face : faces) {
			if( face.surfaceType == 2) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, vertexes, indexes, 10);
			}
		}

		bspWriter.writeVerts( vertexes, "q3dm17.verts");

		Shader[] textures = bsp.getShaders();
		bspWriter.writeObjectAsJSON( textures, "q3dm17.textures");

		bspWriter.writeIndexes( faces, indexes, textures, "q3dm17.indices");
		bspWriter.writeNormals( vertexes, "q3dm17.normals");
		bspWriter.writeTexCoords( vertexes, "q3dm17.texCoords");
		bspWriter.writeLmCoords( vertexes, "q3dm17.lmCoords");
		bspWriter.writeColors( vertexes, "q3dm17.colors");

	}


	public static void writeFile( String fn, byte[] bytes) {
		try ( FileOutputStream fos = new FileOutputStream( fn)) {
			fos.write( bytes );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
