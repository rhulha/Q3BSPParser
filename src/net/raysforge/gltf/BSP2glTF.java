package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

import net.raysforge.bsp.q3.PartsWriter;
import net.raysforge.bsp.q3.PartsWriterJson;
import net.raysforge.bsp.q3.Q3BSP;
import net.raysforge.gltf.model.Accessor;
import net.raysforge.gltf.model.Attribute;
import net.raysforge.gltf.model.Buffer;
import net.raysforge.gltf.model.BufferView;
import net.raysforge.gltf.model.Mesh;
import net.raysforge.gltf.model.Node;
import net.raysforge.gltf.model.Primitive;
import net.raysforge.gltf.model.Scene;

public class BSP2glTF {
	private File bspFile;
	private File outputDirectory;
	private String bspFileNameSansExt;

	public BSP2glTF(File bspFile, File outputDirectory) {
		this.bspFile = bspFile;
		this.outputDirectory = outputDirectory;
		bspFileNameSansExt = bspFile.getName().replaceFirst("[.][^.]+$", "");
	}

	public void convert() throws IOException {
		Q3BSP q3bsp = new Q3BSP(bspFile);
		int indexCount = writeBSPParts(q3bsp);
		writeGLTF(q3bsp, indexCount);
	}

	private int writeBSPParts(Q3BSP q3bsp) throws IOException {
		q3bsp.flipYZ();
		q3bsp.scaleYZ(0.038); // this is the official Q3 conversion ratio https://www.quake3world.com/forum/viewtopic.php?f=10&t=50384
		q3bsp.tessellateAllPatchFaces(24);
		q3bsp.changeColors();
		
		PartsWriter partsWriter = new PartsWriter(q3bsp, outputDirectory); 
		PartsWriterJson partsWriterJson = new PartsWriterJson(outputDirectory);
		partsWriterJson.writeEntitiesAsJSON( q3bsp.entities, "q3dm17.ents");
		partsWriterJson.writeObjectAsJSON( q3bsp.shaders, "q3dm17.textures");

		// 16714 verts written (4bytes*3xyz*num_written)
		// 54612 indices written (currently short = 2 bytes)
		int indexCount = partsWriter.writeBasics(bspFileNameSansExt);
		return indexCount;
	}

	private void writeGLTF(Q3BSP q3bsp, int indexCount) throws IOException {
		BufferView bufferViewVerts = getBufferAndView(".verts", 12);
		BufferView bufferViewNormals = getBufferAndView(".normals", 12);
		BufferView bufferViewColors = getBufferAndView(".colors", 12);
		
		Accessor normal = new Accessor(bufferViewNormals, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		Accessor pos = new Accessor(bufferViewVerts, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		Accessor color = new Accessor(bufferViewColors, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		

		Attribute attr_p = new Attribute("POSITION", pos);
		Attribute attr_n = new Attribute("NORMAL", normal);
		//Attribute attr_t = new Attribute("TEXCOORD_0", t);
		Attribute attr_c = new Attribute("COLOR_0", color);
		
		Primitive prim = new Primitive();
		Accessor i = getIndexAccessor(indexCount);
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		prim.addAttribute(attr_n);
		prim.addAttribute(attr_c);
		
		Mesh mesh = new Mesh(prim);
		//mesh.setMaterial(0);
		Node node = new Node(mesh);
		Scene scene = new Scene(node);
		GlTF glTF = new GlTF(scene);

		glTF.write(new File(outputDirectory, bspFileNameSansExt + ".gltf"));
	}

	private Accessor getIndexAccessor(int indexCount) 	{
		BufferView bufferViewIndex =  getBufferAndView(".indices", -1);
		return new Accessor(bufferViewIndex, GltfConstants.GL_UNSIGNED_SHORT, 0, indexCount, "SCALAR");
	}

	private BufferView getBufferAndView(String ext, int byteStride) {
		File file = new File(outputDirectory, bspFileNameSansExt + ext);
		Buffer buffer = new Buffer(bspFileNameSansExt + ext, (int)file.length());
		return new BufferView(buffer, 0, (int)file.length(), byteStride);
	}
}
