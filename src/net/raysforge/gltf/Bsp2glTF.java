package net.raysforge.gltf;

import java.io.IOException;
import java.util.List;

import net.raysforge.q3.bsp.Q3BSPReader;
import net.raysforge.q3.bsp.Q3LumpTypes;
import net.raysforge.q3.bsp.Vertex;

public class Bsp2glTF {

	public static void main(String[] args) throws IOException {
		Q3BSPReader bsp = new Q3BSPReader("q3dm17.bsp", Q3LumpTypes.size);
		
		List<Integer> drawIndexes = bsp.getDrawIndexes();
		List<Vertex> drawVerts = bsp.getDrawVerts();
		
		Buffer buffer = new Buffer("Box0.bin", 648);
		
		BufferView bufferViewPos = new BufferView(buffer, 0, 576, 12);
		
		Accessor pos = new Accessor(bufferViewPos, GltfConstants.GL_FLOAT, 0, 24, "VEC3");
		Accessor normal = new Accessor(bufferViewPos, GltfConstants.GL_FLOAT, 288, 24, "VEC3");

		Attribute attr_p = new Attribute("POSITION", pos);
		Attribute attr_n = new Attribute("NORMAL", normal);
		//Attribute attr_t = new Attribute("TEXCOORD_0", t);
	
		BufferView bufferViewIndex = new BufferView(buffer, 576, 72);

		Primitive prim = new Primitive();
		Accessor i = new Accessor(bufferViewIndex, GltfConstants.GL_UNSIGNED_SHORT, 0, 36, "SCALAR");
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		prim.addAttribute(attr_n);
		
		Mesh mesh = new Mesh(prim);
		//mesh.setMaterial(0);
		Node node = new Node(mesh);
		Scene scene = new Scene(node);
		GlTF glTF = new GlTF(scene);
		
		
		glTF.write("test.gltf");

	}
}
