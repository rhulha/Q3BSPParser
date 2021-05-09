package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.raysforge.bsp.q3.PartsWriter;
import net.raysforge.bsp.q3.PartsWriterJson;
import net.raysforge.bsp.q3.Q3BSP;
import net.raysforge.bsp.q3.SkipItem;
import net.raysforge.bsp.q3.model.Vertex;
import net.raysforge.gltf.model.Accessor;
import net.raysforge.gltf.model.Attribute;
import net.raysforge.gltf.model.Buffer;
import net.raysforge.gltf.model.BufferView;
import net.raysforge.gltf.model.Mesh;
import net.raysforge.gltf.model.Node;
import net.raysforge.gltf.model.Primitive;
import net.raysforge.gltf.model.Scene;

public class BSP2GLTF_colored {
	private File outputDirectory;
	private String bspFileNameSansExt;
	private GlTF glTF;
	private Scene scene;
	private Q3BSP q3bsp;
	private PartsWriter partsWriter;
	private int meshCounter=0;
	private BufferView bufferViewVerts;
	private BufferView bufferViewNormals;
	private BufferView bufferViewColors;
	private Accessor pos;
	private Accessor normal;
	private int componentType;
	private Accessor color;

	public BSP2GLTF_colored(File bspFile, File outputDirectory, boolean colorsAsFloats) throws IOException {
		this.outputDirectory = outputDirectory;
		bspFileNameSansExt = bspFile.getName().replaceFirst("[.][^.]+$", "");
		
		q3bsp = new Q3BSP(bspFile);
		q3bsp.flipYZ();
		q3bsp.scaleXYZ(0.038); // this is the official Q3 conversion ratio https://www.quake3world.com/forum/viewtopic.php?f=10&t=50384
		q3bsp.tessellateAllPatchFaces(12);
		q3bsp.changeColors();

		partsWriter = new PartsWriter(q3bsp, outputDirectory); 
		scene = new Scene();
		glTF = new GlTF(scene);

		PartsWriterJson partsWriterJson = new PartsWriterJson(outputDirectory);
		partsWriterJson.writeEntitiesAsJSON( q3bsp.entities, "q3dm17.ents");
		partsWriterJson.writeObjectAsJSON( q3bsp.shaders, "q3dm17.textures");
		partsWriter.writeBasics(bspFileNameSansExt, true);

		bufferViewVerts = getBufferAndView(".verts", 12);
		bufferViewNormals = getBufferAndView(".normals", 12);
		bufferViewColors = getBufferAndView(".colors", colorsAsFloats ? 12 : 3);

		pos = new Accessor(bufferViewVerts, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		// TODO: FIXME.
		//pos.min[0] = (int) Vertex.min.x-11; // this is a workaround, without -11 and +11 the level disappears on the edges. I seem to have a bug.
		//pos.min[1] = (int) Vertex.min.y-11;
		//pos.min[2] = (int) Vertex.min.z-11;
		//pos.max[0] = (int) Vertex.max.x+11;
		//pos.max[1] = (int) Vertex.max.y+11;
		//pos.max[2] = (int) Vertex.max.z+11;
		System.out.println("Vertex.min: " + Vertex.min);
		System.out.println("Vertex.max: " + Vertex.max);
		
		normal = new Accessor(bufferViewNormals, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		componentType = colorsAsFloats ? GltfConstants.GL_FLOAT : GltfConstants.GL_BYTE;
		color = new Accessor(bufferViewColors, componentType, 0, q3bsp.vertices.size(), "VEC3");
	}

	// suffix can be used to differentiate between different mesh indices in the same scene.
	// example: getIndexAccessor( indexCount, ".0" );
	private Accessor getIndexAccessor(int indexCount, String suffix) 	{
		BufferView bufferViewIndex =  getBufferAndView(suffix+".indices", -1);
		return new Accessor(bufferViewIndex, GltfConstants.GL_UNSIGNED_SHORT, 0, indexCount, "SCALAR");
	}

	private BufferView getBufferAndView(String ext, int byteStride) {
		File file = new File(outputDirectory, bspFileNameSansExt + ext);
		Buffer buffer = new Buffer(bspFileNameSansExt + ext, (int)file.length());
		return new BufferView(buffer, 0, (int)file.length(), byteStride);
	}

	public void addMeshFromBSP(Map<String, SkipItem> skipList, boolean skipListIsIncludeList) throws IOException {
		
		Attribute attr_p = new Attribute("POSITION", pos);
		Attribute attr_n = new Attribute("NORMAL", normal);
		//Attribute attr_t = new Attribute("TEXCOORD_0", t);
		Attribute attr_c = new Attribute("COLOR_0", color);
		
		Primitive prim = new Primitive();

		String suffix = "."+meshCounter;
		// 16714 verts written (4bytes*3xyz*num_written)
		// 54612 indices written (currently short = 2 bytes)
		int indexCount = partsWriter.writeIndexes( bspFileNameSansExt + suffix + ".indices", skipList, skipListIsIncludeList);

		Accessor i = getIndexAccessor(indexCount, suffix);

		meshCounter++;
		
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		prim.addAttribute(attr_n);
		prim.addAttribute(attr_c);
		
		Mesh mesh = new Mesh(prim);
		//mesh.setMaterial(0);
		Node node = new Node(mesh);
		scene.nodes.add(node);
	}

	public void writeGLTF() throws IOException {
		//int indexCount = writeBSPParts(q3bsp, skipList, skipListIsIncludeList);
		glTF.write(new File(outputDirectory, bspFileNameSansExt + ".gltf"));
		
	}
}
