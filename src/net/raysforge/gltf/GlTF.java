package net.raysforge.gltf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
	
	public void writeObjectBegin(Writer fw, String key) throws IOException {
		fw.write('"'+key+'"'+":{");
	}
	
	public void writeStr(Writer fw, String key, String val) throws IOException {
		fw.write('"'+key+"\":\""+val+'"');
	}

	public void writeNbr(Writer fw, String key, int val) throws IOException {
		fw.write('"'+key+"\":"+val);
	}

	public void writeFloat(Writer fw, String key, float val) throws IOException {
		fw.write('"'+key+"\":"+val);
	}

	GlTFInternalCache cache = new GlTFInternalCache();

	public void write(File file) throws IOException {
		cache = new GlTFInternalCache();
		
		StringWriter buffers = new StringWriter(); 
		writeArrayBegin(buffers, "buffers");

		StringWriter bufferViews = new StringWriter(); 
		writeArrayBegin(bufferViews, "bufferViews");

		StringWriter accessors = new StringWriter(); 
		writeArrayBegin(accessors, "accessors");

		StringWriter materials = new StringWriter(); 
		writeArrayBegin(materials, "materials");

		StringWriter textures = new StringWriter(); 
		writeArrayBegin(textures, "textures");

		StringWriter images = new StringWriter(); 
		writeArrayBegin(images, "images");

		try(FileWriter fw = new FileWriter(file))
		{
		
			fw.write("{");
			
			for (int s = 0; s < scenes.size(); s++) {
				Scene scene = scenes.get(s);
				for (int n = 0; n < scene.nodes.size(); n++) {
					Node node = scene.nodes.get(n);
					cache.add(node.mesh);
					// TODO nodes cache
					
					if( node.children.size() > 0 )
						throw new RuntimeException("only one Mesh allowed at the moment.");
					
					for (int p = 0; p < node.mesh.primitives.size(); p++) {
						Primitive primitive = node.mesh.primitives.get(p);
						
						if(primitive.material!=null)
							cacheMaterial(materials, textures, images, primitive.material);
	
						cacheAccessor(buffers, bufferViews, accessors, primitive.indices);
	
						for (int a = 0; a < primitive.attributes.size(); a++) {
							Attribute attribute = primitive.attributes.get(a);
							cacheAccessor(buffers, bufferViews, accessors, attribute.accessor);
	
						}
					}
				}
			}
			
			removeLastComma(buffers);
			removeLastComma(bufferViews);
			removeLastComma(accessors);
			removeLastComma(materials);
			removeLastComma(textures);
			removeLastComma(images);
			
			buffers.write("],");
			bufferViews.write("],");
			accessors.write("],");
			materials.write("],");
			textures.write("],");
			images.write("]");

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
			fw.write(materials.toString());
			fw.write(textures.toString());
			fw.write(images.toString());
			
			fw.write("}");
		
		}
	}

	private void removeLastComma(StringWriter sw) {
		sw.getBuffer().setLength(sw.getBuffer().lastIndexOf(","));
	}

	private void cacheMaterial(StringWriter materials, StringWriter textures, StringWriter images, Material material) throws IOException {
		Texture texture = material.pbr_baseColorTexture;
		Image image = texture.image;

		if( !cache.contains(image) ) {
			cache.add(image);
			images.write("{");
			writeStr(images, "uri", image.uri);
			images.write("},");
		}
		if( !cache.contains(texture) ) {
			cache.add(texture);
			textures.write("{");
			writeNbr(textures, "source", cache.getIndex(image));
			textures.write("},");
		}
		if( !cache.contains(material) ) {
			cache.add(material);
			materials.write("{");
			 writeObjectBegin(materials, "pbrMetallicRoughness");
			  writeFloat(materials, "metallicFactor", material.pbr_metallicFactor);
			  materials.write(",");
			  writeFloat(materials, "roughnessFactor", material.pbr_roughnessFactor);
			  materials.write(",");
			   writeObjectBegin(materials, "baseColorTexture");
			   writeNbr(materials, "index", cache.getIndex(texture));
			   //materials.write(",");
			   //writeNbr(textures, "texCoord", cache.getIndex(texture));
			   materials.write("}"); // baseColorTexture
			  materials.write("}"); // pbrMetallicRoughness
			//materials.write(",");
			materials.write("},");
		}
		
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
			if( accessor.min[0] != 0 || accessor.min[1] != 0 || accessor.min[2] != 0
					|| accessor.max[0] != 0 || accessor.max[1] != 0 || accessor.max[2] != 0) {
				writeArrayBegin(accessors, "min");
				accessors.write(""+accessor.min[0]+","+accessor.min[1]+","+accessor.min[2]+"]");
				accessors.write(",");
				writeArrayBegin(accessors, "max");
				accessors.write(""+accessor.max[0]+","+accessor.max[1]+","+accessor.max[2]+"]");
				accessors.write(",");
			}
			writeNbr(accessors, "componentType", accessor.componentType);
			accessors.write(",");
			writeStr(accessors, "type", accessor.type);
			accessors.write("},");
		}
	}

	private void writeMeshesBlock(FileWriter fw) throws IOException {
		writeArrayBegin(fw, "meshes");
		for (int m = 0; m < cache.getSize(Mesh.class); m++) {
			if(m>0)
				fw.write(",");
			fw.write("{");
			Mesh mesh = cache.get(Mesh.class, m);
			
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
					//System.out.println(attr.type + ": " + cache.getIndex(attr.accessor));
					
				}
				fw.write("},");
				
				writeNbr(fw, "indices", cache.getIndex(primitive.indices));
				fw.write(",");
				if(primitive.material!=null) {
					writeNbr(fw, "material", cache.getIndex(primitive.material)); 
					fw.write(",");
				}
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

	
}
