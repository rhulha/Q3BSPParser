package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

import net.raysforge.bsp.q3.PartsWriter;
import net.raysforge.bsp.q3.PartsWriterJson;
import net.raysforge.bsp.q3.Q3BSP;

public class Bsp2glTF {

	public static void convert(File bspFile, File outputDirectory) throws IOException {
		
		Q3BSP q3bsp = new Q3BSP(bspFile);
		
		q3bsp.flipYZ();
		q3bsp.tessellateAllPatchFaces();
		q3bsp.changeColors();
		
		PartsWriter partsWriter = new PartsWriter(outputDirectory); 
		PartsWriterJson partsWriterJson = new PartsWriterJson(outputDirectory);
		partsWriterJson.writeEntitiesAsJSON( q3bsp.entities, "q3dm17.ents");
		partsWriterJson.writeObjectAsJSON( q3bsp.shaders, "q3dm17.textures");
		q3bsp.writeBasics(partsWriter);
		

		// List<Integer> drawIndexes = bsp.getDrawIndexes();
		// List<Vertex> drawVerts = bsp.getDrawVerts();
		
//		16714 verts written (4bytes*3xyz*num_written)
	
//		54612 indices written (currently short = 2 bytes)
		
		String nameSansExt = bspFile.getName().replaceFirst("[.][^.]+$", "");
		
		File verts_file = new File(outputDirectory, nameSansExt + ".verts");
		File indices_file = new File(outputDirectory, nameSansExt + ".indices");
		
		Buffer verts_buffer = new Buffer(nameSansExt + ".verts", (int)verts_file.length());
		Buffer indices_buffer = new Buffer(nameSansExt + ".indices", (int)indices_file.length());
		
		BufferView bufferViewVerts = new BufferView(verts_buffer, 0, (int)verts_file.length(), 12);
		
		//Accessor normal = new Accessor(bufferViewPos, GltfConstants.GL_FLOAT, 0, 24, "VEC3");
		Accessor pos = new Accessor(bufferViewVerts, GltfConstants.GL_FLOAT, 0, 16714, "VEC3");

		Attribute attr_p = new Attribute("POSITION", pos);
		//Attribute attr_n = new Attribute("NORMAL", normal);
		//Attribute attr_t = new Attribute("TEXCOORD_0", t);
	
		BufferView bufferViewIndex = new BufferView(indices_buffer, 0, (int)indices_file.length());

		Primitive prim = new Primitive();
		Accessor i = new Accessor(bufferViewIndex, GltfConstants.GL_UNSIGNED_SHORT, 0, 54612, "SCALAR");
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		//prim.addAttribute(attr_n);
		
		Mesh mesh = new Mesh(prim);
		//mesh.setMaterial(0);
		Node node = new Node(mesh);
		Scene scene = new Scene(node);
		GlTF glTF = new GlTF(scene);
		
		
		glTF.write(new File(outputDirectory, nameSansExt + ".gltf"));

	}
}
