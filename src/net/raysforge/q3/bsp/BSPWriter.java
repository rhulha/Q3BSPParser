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

	private String basePath;

	public BSPWriter(String basePath) {
		this.basePath = basePath;
	}

	/*
	public void example( BSPReader bsp, BSPWriter bspWriter) throws IOException {
		bspWriter.writeArrayAsJSON( bsp.getLeafBrushes(), "q3dm17.leafbrushes");
		bspWriter.writeArrayAsJSON( bsp.getBrushSides(), "q3dm17.brushsides");
		bspWriter.writeArrayAsJSON( bsp.getBrushes(), "q3dm17.brushes");
		bspWriter.writeObjectAsJSON( bsp.getLeafs(), "q3dm17.leafs");
		bspWriter.writePlanesAsJSON( bsp.getPlanes(), "q3dm17.planes");
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
		bspWriter.writeNodesAsJSON( bsp.getNodes(), "q3dm17.nodes");
	}
	*/

	public static byte[] int2ByteArray (int value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
	}

	public static byte[] char2ByteArray (char value) {
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putChar(value).array();
	}

	public static byte[] float2ByteArray (float value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
	}
	
	public void writeObjectAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}

	public void writeArrayAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("],[", "],\n[");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}
	
	
	public void writeLeafsAsJSON(Leaf[] leafs, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(leafs);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("leafs written");
	}

	public void writePlanesAsJSON(Plane[] planes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(planes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("planes written");
	}

	public void writeEntitiesAsJSON(Map<String, List<Map<String, String>>> entitites, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(entitites);
		json = json.replace("],\"", "],\n\"");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("entitites written");
	}

	public void writeNodesAsJSON(Node[] nodes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(nodes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(basePath + filename)) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("nodes written");
	}

	public void writeVerts(List<Vertex> vertexes, String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.xyz.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.xyz.y) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.xyz.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("verts written");
	}
	
	public void writeIndexes(Surface[] surfaces, List<Integer> indexes, Shader[] shaders, String filename) throws IOException {
		
		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");

		
		
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			
			for (Surface face : surfaces) {
				
				if( skip.contains( shaders[face.shaderNum].shader))
					continue;
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					fos.write( BSPWriter.char2ByteArray( (char)i) );
                }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("indices written");
	}

	public void writeNormals(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.normal.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.normal.y) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.normal.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("normals written");
	}

	public void writeTexCoords(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.st.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.st.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}

	public void writeLmCoords(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.lightmap.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.lightmap.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}

	public void writeColors(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(basePath + filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPWriter.float2ByteArray( (float)vertex.color.x) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.color.y) );
				fos.write( BSPWriter.float2ByteArray( (float)vertex.color.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}
}