package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
	

	public static void writeFile( String fn, byte[] bytes) {
		try ( FileOutputStream fos = new FileOutputStream( fn)) {
			fos.write( bytes );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		BSPWriter bspWriter = new BSPWriter( basePath);
		List<Vertex> verts = bsp.getVertexes();
		Face[] faces = bsp.getFaces();
		List<Integer> meshVerts = bsp.getMeshVerts();

		for (Face face : faces) {
			if( face.type == 2)
				Tessellate.tessellate(face, verts, meshVerts, 10);
		}

		bspWriter.writeNormals(verts, "q3dm17.normals");
		
	}
	
	public static void main0(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.brushes", bsp.getLump(LumpTypes.Brushes));
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.brushsides", bsp.getLump(LumpTypes.Brushsides));
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.leafbrushes", bsp.getLump(LumpTypes.Leafbrushes));
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.leafs", bsp.getLump(LumpTypes.Leafs));
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.nodes", bsp.getLump(LumpTypes.Nodes));
		writeFile( "C:\\Users\\Ray\\dart\\BSPParser\\bin\\from.java.planes", bsp.getLump(LumpTypes.Planes));
		
		
	}
	
	public static void main1(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		Texture[] textures = bsp.getTextures();
		BSPWriter bspWriter = new BSPWriter( basePath);
		bspWriter.writeObjectAsJSON( textures, "q3dm17.textures");
		
	}
	
	public static void main2(String[] args) throws IOException {

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
