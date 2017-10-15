package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

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
	}

}
