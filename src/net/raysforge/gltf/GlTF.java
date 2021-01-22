package net.raysforge.gltf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GlTF {
	
	List<Scene> scenes = new ArrayList<Scene>();
	Scene scene;

	public GlTF(Scene scene) {
		this.scene = scene;
		scenes.add(scene);
	}

	public void addScene(Scene s) {
		scenes.add(s);
	}

	public void setScene(Scene s) {
		scene = s;
		
	}
	
	public void writeArrayBegin(Writer fw, String key) throws IOException {
		fw.write('"'+key+'"'+":[");
	}
	
	public void writeStr(Writer fw, String key, String val) throws IOException {
		fw.write('"'+key+"\":\""+val+'"');
	}

	public void writeNbr(Writer fw, String key, int val) throws IOException {
		fw.write('"'+key+"\":"+val);
	}

	GlTFInternalCache cache = new GlTFInternalCache();

	public void write(String fileName) throws IOException {
		cache = new GlTFInternalCache();
		
		StringWriter buffers = new StringWriter(); 
		writeArrayBegin(buffers, "buffers");

		StringWriter bufferViews = new StringWriter(); 
		writeArrayBegin(bufferViews, "bufferViews");

		StringWriter accessors = new StringWriter(); 
		writeArrayBegin(accessors, "accessors");

		FileWriter fw = new FileWriter(fileName);
		
		fw.write("{");
		
		for (int s = 0; s < scenes.size(); s++) {
			Scene scene = scenes.get(s);
			for (int n = 0; n < scene.nodes.size(); n++) {
				Node node = scene.nodes.get(n);
				cache.addMesh(node.mesh);
				// TODO nodes cache
				
				if( node.children.size() > 0 )
					throw new RuntimeException("only one Mesh allowed at the moment.");
				
				for (int p = 0; p < node.mesh.primitives.size(); p++) {
					Primitive primitive = node.mesh.primitives.get(p);

					cacheAccessor(buffers, bufferViews, accessors, primitive.indices);

					for (int a = 0; a < primitive.attributes.size(); a++) {
						Attribute attribute = primitive.attributes.get(a);
						cacheAccessor(buffers, bufferViews, accessors, attribute.accessor);

					}
				}
			}
		}
		
		buffers = removeLastComma(buffers);
		bufferViews = removeLastComma(bufferViews);
		accessors = removeLastComma(accessors);
		
		buffers.write("],");
		bufferViews.write("],");
		accessors.write("]");

		fw.write("\"asset\":{");
		writeStr(fw, "version", "2.0");
		fw.write("},");
		
		writeNbr(fw, "scene", scenes.indexOf(scene));
		fw.write(",");
		
		writeScenesBlock(fw);
		writeNodesBlock(fw);
		writeMeshesBlock(fw);
		
		fw.write(buffers.toString());
		fw.write(bufferViews.toString());
		fw.write(accessors.toString());
		
		fw.write("}");
		fw.close();
	}

	// TODO very inefficient. Fix later.
	private StringWriter removeLastComma(StringWriter sw) {
		String string = sw.toString();
		string = string.substring(0, string.lastIndexOf(","));
		StringWriter sw2 = new StringWriter();
		sw2.write(string);
		return sw2;
	}

	private void cacheAccessor(StringWriter buffers, StringWriter bufferViews, StringWriter accessors, Accessor accessor) throws IOException {
		BufferView bufferView = accessor.bufferView;
		Buffer buffer = bufferView.buffer;
		
		if( !cache.contains(buffer) ) {
			cache.add(buffer);
			buffers.write("{");
			writeStr(buffers, "uri", buffer.uri);
			buffers.write(",");
			writeNbr(buffers, "byteLength", buffer.byteLength);
			buffers.write("},");
		}
		
		if( !cache.contains(bufferView) ) {
			cache.add(bufferView);
			bufferViews.write("{");
			writeNbr(bufferViews, "buffer", cache.getIndex(bufferView.buffer));
			bufferViews.write(",");
			writeNbr(bufferViews, "byteOffset", bufferView.byteOffset);
			bufferViews.write(",");
			writeNbr(bufferViews, "byteLength", bufferView.byteLength);
			if( bufferView.byteStride > 0 ) {
				bufferViews.write(",");
				writeNbr(bufferViews, "byteStride", bufferView.byteStride);
			}
			bufferViews.write("},");
		}
		
		if( !cache.contains(accessor) ) {
			cache.add(accessor);
			accessors.write("{");
			writeNbr(accessors, "bufferView", cache.getIndex(accessor.bufferView));
			accessors.write(",");
			writeNbr(accessors, "byteOffset", accessor.byteOffset);
			accessors.write(",");
			writeNbr(accessors, "count", accessor.count);
			accessors.write(",");
			writeNbr(accessors, "componentType", accessor.componentType);
			accessors.write(",");
			writeStr(accessors, "type", accessor.type);
			accessors.write("},");
		}
	}

	private void writeMeshesBlock(FileWriter fw) throws IOException {
		writeArrayBegin(fw, "meshes");
		for (int m = 0; m < cache.meshes.size(); m++) {
			if(m>0)
				fw.write(",");
			fw.write("{");
			Mesh mesh = cache.meshes.get(m);
			
			writeArrayBegin(fw, "primitives");
			for (int p = 0; p < mesh.primitives.size(); p++) {
				if(p>0)
					fw.write(",");
				fw.write("{");
				Primitive primitive = mesh.primitives.get(p);
				
				fw.write("\"attributes\":{");
				for (int a = 0; a < primitive.attributes.size(); a++) {
					if(a>0)
						fw.write(",");
					Attribute attr = primitive.attributes.get(a);
					writeNbr(fw, attr.type, cache.getIndex(attr.accessor));
					System.out.println(attr.type + ": " + cache.getIndex(attr.accessor));
					
				}
				fw.write("},");
				
				writeNbr(fw, "indices", cache.getIndex(primitive.indices));
				fw.write(",");
				if(primitive.material>=0)
					writeNbr(fw, "material", primitive.material); // TODO use cache.getIndex(primitive.material)
				if(primitive.mode>=0)
					writeNbr(fw, "mode", primitive.mode);
				fw.write("}");
			}
			fw.write("]"); // primitives
			fw.write("}");
		}
		fw.write("],");
	}

	private void writeNodesBlock(FileWriter fw) throws IOException {
		writeArrayBegin(fw, "nodes");
		for (int n = 0; n < scene.nodes.size(); n++) {
			if(n>0)
				fw.write(",");
			fw.write("{");
			Node node = scene.nodes.get(n);
			writeNbr(fw, "mesh", cache.getIndex(node.mesh));
			fw.write("}");
		}
		fw.write("],");
	}

	private void writeScenesBlock(FileWriter fw) throws IOException {
		writeArrayBegin(fw, "scenes");
		for (int s = 0; s < scenes.size(); s++) {
			if(s>0)
				fw.write(",");
			fw.write("{");
			Scene scene = scenes.get(s);
			writeArrayBegin(fw, "nodes");
			for (int n = 0; n < scene.nodes.size(); n++) {
				Node node = scene.nodes.get(n);
				if(n>0)
					fw.write(",");
				fw.write(""+scene.nodes.indexOf(node)); // todo use cache.nodes
			}
			fw.write("]"); // nodes
			fw.write("}");
		}
		fw.write("],"); // scenes
	}

	public void writeGson(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		
		JsonObject glTF = new JsonObject();
		glTF.addProperty("scene", 0);
		
		JsonArray scenes = new JsonArray();
		glTF.add("scenes", scenes);
		
		
		fw.write(glTF.toString());
		fw.close();
	}
}