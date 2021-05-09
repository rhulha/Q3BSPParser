package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import net.raysforge.bsp.q3.PartsWriter;
import net.raysforge.bsp.q3.Q3BSP;
import net.raysforge.bsp.q3.SkipItem;
import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.gltf.model.Accessor;
import net.raysforge.gltf.model.Attribute;
import net.raysforge.gltf.model.Buffer;
import net.raysforge.gltf.model.BufferView;
import net.raysforge.gltf.model.Image;
import net.raysforge.gltf.model.Material;
import net.raysforge.gltf.model.Mesh;
import net.raysforge.gltf.model.Node;
import net.raysforge.gltf.model.Primitive;
import net.raysforge.gltf.model.Scene;
import net.raysforge.gltf.model.Texture;

public class BSP2GLTF_textured {
	private File outputDirectory;
	private String bspFileNameSansExt;
	private GlTF glTF;
	private Scene scene;
	private Q3BSP q3bsp;
	private PartsWriter partsWriter;
	private int meshCounter=0;
	private BufferView bufferViewVerts;
	private BufferView bufferViewNormals;
	private BufferView bufferViewUV;
	private Accessor pos;
	private Accessor normal;
	private Accessor uv;
	private HashMap<String, String> fileNameMap;
	
	public BSP2GLTF_textured(File bspFile, File outputDirectory, HashMap<String, String> fileNameMap) throws IOException {
		
		this.outputDirectory = outputDirectory;
		this.fileNameMap = fileNameMap;
		bspFileNameSansExt = bspFile.getName().replaceFirst("[.][^.]+$", "");
		
		q3bsp = new Q3BSP(bspFile);
		q3bsp.flipYZ();
		q3bsp.scaleXYZ(0.038); // this is the official Q3 conversion ratio https://www.quake3world.com/forum/viewtopic.php?f=10&t=50384
		q3bsp.tessellateAllPatchFaces(12);
		
		partsWriter = new PartsWriter(q3bsp, outputDirectory); 
		scene = new Scene();
		glTF = new GlTF(scene);
		
		partsWriter.writeBasics(bspFileNameSansExt, true);
		
		bufferViewVerts = getBufferAndView(".verts", 12);
		bufferViewNormals = getBufferAndView(".normals", 12);
		bufferViewUV = getBufferAndView(".texCoords", 8);
		
		pos = new Accessor(bufferViewVerts, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		normal = new Accessor(bufferViewNormals, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC3");
		uv = new Accessor(bufferViewUV, GltfConstants.GL_FLOAT, 0, q3bsp.vertices.size(), "VEC2");
		
		TreeSet<String> set = new TreeSet<>();
		
		for (Surface face : q3bsp.surfaces) {
			String texture = q3bsp.shaders[face.shaderNum].shader;
			set.add(texture);
		}
		
		for(String tex : set) {
			System.out.println(tex);
			if(fileNameMap.containsKey(tex)) {
				addMeshForTexture(tex);
			} else {
				System.out.println("Warning: this texture has no file in the file system: " + tex);
			}
		}
		
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

	
public void addMeshForTexture(String texture) throws IOException {
		
		Attribute attr_p = new Attribute("POSITION", pos);
		Attribute attr_n = new Attribute("NORMAL", normal);
		Attribute attr_t = new Attribute("TEXCOORD_0", uv);
		
		Primitive prim = new Primitive();

		String suffix = "."+meshCounter;
		// 16714 verts written (4bytes*3xyz*num_written)
		// 54612 indices written (currently short = 2 bytes)
		
		Map<String, SkipItem> include = new HashMap<String, SkipItem>();
		include.put(texture, new SkipItem(texture, false, -1, false));
		int indexCount = partsWriter.writeIndexes( bspFileNameSansExt + suffix + ".indices", include, true);

		Accessor i = getIndexAccessor(indexCount, suffix);

		meshCounter++;
		
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		prim.addAttribute(attr_n);
		prim.addAttribute(attr_t);
		
		if( fileNameMap.get(texture) != null) {
			Image image = new Image(fileNameMap.get(texture));
			Texture texObj = new Texture(image);
			Material mat = new Material(texObj);
			prim.setMaterial(mat);
		} else {
			System.out.println("Warning: this texture has no file in the file system: " + texture);
		}
		
		Mesh mesh = new Mesh(prim);
		
		Node node = new Node(mesh);
		scene.nodes.add(node);
	}

	public void writeGLTF() throws IOException {
		//int indexCount = writeBSPParts(q3bsp, skipList, skipListIsIncludeList);
		glTF.write(new File(outputDirectory, bspFileNameSansExt + ".gltf"));
		
	}	
}
