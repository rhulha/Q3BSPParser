package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

import net.raysforge.gltf.model.Accessor;
import net.raysforge.gltf.model.Attribute;
import net.raysforge.gltf.model.Buffer;
import net.raysforge.gltf.model.BufferView;
import net.raysforge.gltf.model.Mesh;
import net.raysforge.gltf.model.Node;
import net.raysforge.gltf.model.Primitive;
import net.raysforge.gltf.model.Scene;

public class CreateBox {

	public static void main(String[] args) throws IOException {
		Buffer buffer = new Buffer("Box0.bin", 648);

		BufferView bufferViewPos = new BufferView(buffer, 0, 576, 12);

		Accessor normal = new Accessor(bufferViewPos, GltfConstants.GL_FLOAT, 0, 24, "VEC3");
		Accessor pos = new Accessor(bufferViewPos, GltfConstants.GL_FLOAT, 288, 24, "VEC3");

		Attribute attr_p = new Attribute("POSITION", pos);
		Attribute attr_n = new Attribute("NORMAL", normal);
		// Attribute attr_t = new Attribute("TEXCOORD_0", t);

		BufferView bufferViewIndex = new BufferView(buffer, 576, 72);

		Primitive prim = new Primitive();
		Accessor i = new Accessor(bufferViewIndex, GltfConstants.GL_UNSIGNED_SHORT, 0, 36, "SCALAR");
		prim.setIndices(i);
		prim.addAttribute(attr_p);
		prim.addAttribute(attr_n);

		Mesh mesh = new Mesh(prim);
		// mesh.setMaterial(0);
		Node node = new Node(mesh);
		Scene scene = new Scene(node);
		GlTF glTF = new GlTF(scene);

		glTF.write(new File("D:\\GameDev\\Tests\\gltf\\box.gltf"));
	}

}
