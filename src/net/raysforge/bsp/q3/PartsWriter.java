package net.raysforge.bsp.q3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class PartsWriter {

	private String basePath;

	public PartsWriter(String basePath) {
		this.basePath = basePath;
	}

	public static byte[] int2ByteArray (int value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
	}

	public static byte[] char2ByteArray (char value) {
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putChar(value).array();
	}

	public static byte[] float2ByteArray (float value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
	}
	
	public void writeVerts(List<Vertex> vertexes, String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : vertexes) {
				fos.write( PartsWriter.float2ByteArray( (float)vertex.xyz.x) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.xyz.y) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.xyz.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(vertexes.size() + " verts written (4bytes*3xyz*num_written)");
	}
	
	public void writeIndexes(List<String> skip, Surface[] surfaces, List<Integer> indexes, Shader[] shaders, String filename) throws IOException {
		long counter=0;
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			
			for (Surface surface : surfaces) {
				
				if( skip.contains( shaders[surface.shaderNum].shader))
					continue;
				for(int k = 0; k < surface.numIndexes; ++k) {
					int i = surface.firstVert + indexes.get(surface.firstIndex + k);
					fos.write( PartsWriter.char2ByteArray( (char)i) );
					counter++;
                }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(counter + " indices written (currently short = 2 bytes)");
	}

	public void writeNormals(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : vertexes) {
				fos.write( PartsWriter.float2ByteArray( (float)vertex.normal.x) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.normal.y) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.normal.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("normals written");
	}

	public void writeTexCoords(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : vertexes) {
				fos.write( PartsWriter.float2ByteArray( (float)vertex.st.x) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.st.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}

	public void writeLmCoords(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : vertexes) {
				fos.write( PartsWriter.float2ByteArray( (float)vertex.lightmap.x) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.lightmap.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}

	public void writeColors(List<Vertex> vertexes, String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : vertexes) {
				fos.write( PartsWriter.float2ByteArray( (float)vertex.color.x) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.color.y) );
				fos.write( PartsWriter.float2ByteArray( (float)vertex.color.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}
}
