package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.q3.map.Plane;

import com.google.gson.Gson;

public class BSPWriter {

	public static byte[] int2ByteArray (int value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
	}

	public static byte[] char2ByteArray (char value) {
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putChar(value).array();
	}

	public static byte[] float2ByteArray (float value) {
		// .order(ByteBuffer.LITTE_ENDIAN)
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
	}
	
	public static void writeObjectAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}

	public static void writeArrayAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("],[", "],\n[");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}
	
	
	public static void writeLeafsAsJSON(Leaf[] leafs, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(leafs);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("leafs written");
	}

	public static void writePlanesAsJSON(Plane[] planes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(planes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("planes written");
	}

	public static void writeEntitiesAsJSON(Map<String, List<Map<String, String>>> entitites, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(entitites);
		json = json.replace("],\"", "],\n\"");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("entitites written");
	}

	public static void writeNodesAsJSON(Node[] nodes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(nodes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("nodes written");
	}

	public static void writeVerts(List<Vertex> vertexes, String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.position.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.position.y) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.position.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("verts written");
	}
	
	public static void writeIndices(Face[] faces, List<Integer> meshVerts, Texture[] textures, String filename) throws IOException {
		
		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");

		
		
		try ( FileOutputStream fos = new FileOutputStream(filename)) {
			
			for (Face face : faces) {
				
				if( skip.contains( textures[face.texture].name))
					continue;
				for(int k = 0; k < face.n_meshverts; ++k) {
					int i = face.vertex + meshVerts.get(face.meshvert + k);
					fos.write( BSPWriter.char2ByteArray( (char)i) );
                }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("indices written");
	}
}
