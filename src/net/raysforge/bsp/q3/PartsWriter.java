package net.raysforge.bsp.q3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.bsp.q3.model.Vertex;

public class PartsWriter {

	private Q3BSP q3bsp;
	private File basePath;

	public PartsWriter(Q3BSP q3bsp, File basePath) {
		this.q3bsp = q3bsp;
		this.basePath = basePath;
	}

	public int writeBasics(String fileNameSansExt, List<String> skip, boolean skipListIsIncludeList) throws IOException {
		int indexesCount = writeIndexes( skip, fileNameSansExt + ".indices", skipListIsIncludeList);
		writeVerts( fileNameSansExt + ".verts");
		writeNormals( fileNameSansExt + ".normals");
		writeTexCoords( fileNameSansExt + ".texCoords");
		writeLmCoords( fileNameSansExt + ".lmCoords");
		writeColors( fileNameSansExt + ".colors");
		return indexesCount;
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
	
	public void writeVerts(String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.xyz.x) );
				fos.write( float2ByteArray( (float)vertex.xyz.y) );
				fos.write( float2ByteArray( (float)vertex.xyz.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(q3bsp.vertices.size() + " verts written (4bytes*3xyz*num_written)");
	}
	
	public int writeIndexes(List<String> skip, String filename, boolean skipListIsIncludeList) throws IOException {
		int counter=0;
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			
			for (Surface surface : q3bsp.surfaces) {
				
				if( skipListIsIncludeList != skip.contains( q3bsp.shaders[surface.shaderNum].shader))
					continue;
				for(int k = 0; k < surface.numIndexes; ++k) {
					int i = surface.firstVert + q3bsp.indexes.get(surface.firstIndex + k);
					fos.write( char2ByteArray( (char)i) );
					counter++;
                }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(counter + " indices written (currently short = 2 bytes)");
		return counter;
	}

	public void writeNormals(String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.normal.x) );
				fos.write( float2ByteArray( (float)vertex.normal.y) );
				fos.write( float2ByteArray( (float)vertex.normal.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("normals written");
	}

	public void writeTexCoords(String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.st.x) );
				fos.write( float2ByteArray( (float)vertex.st.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texCoords written");
	}

	public void writeLmCoords(String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.lightmap.x) );
				fos.write( float2ByteArray( (float)vertex.lightmap.y) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("lightmap texCoords written");
	}

	public void writeColors( String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.color.x) );
				fos.write( float2ByteArray( (float)vertex.color.y) );
				fos.write( float2ByteArray( (float)vertex.color.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("colors written");
	}
}
