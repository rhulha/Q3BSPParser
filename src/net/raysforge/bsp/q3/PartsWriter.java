package net.raysforge.bsp.q3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.bsp.q3.model.Vertex;

public class PartsWriter {

	private Q3BSP q3bsp;
	private File basePath;

	public PartsWriter(Q3BSP q3bsp, File basePath) {
		this.q3bsp = q3bsp;
		this.basePath = basePath;
	}

	public void writeBasics(String fileNameSansExt, boolean colorsAsFloats) throws IOException {
		writeVerts( fileNameSansExt + ".verts");
		writeNormals( fileNameSansExt + ".normals");
		writeTexCoords( fileNameSansExt + ".texCoords");
		writeLmCoords( fileNameSansExt + ".lmCoords");
		if( colorsAsFloats )
			writeColorsAsFloats( fileNameSansExt + ".colors");
		else
			writeColorsAsBytes( fileNameSansExt + ".colors");
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

	public static byte colorFloat2ColorByte (float value) {
		return (byte)(value*255);
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
	
	public int writeIndexes(String filename, Map<String, SkipItem> skip, boolean skipListIsIncludeList) throws IOException {
		int counter=0;
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			
			for (Surface surface : q3bsp.surfaces) {
				
				SkipItem skipItem = skip.get( q3bsp.shaders[surface.shaderNum].shader);
				
				if( skipListIsIncludeList ) {
					if( skip.containsKey( q3bsp.shaders[surface.shaderNum].shader) ) {
						if( skipItem.patchOnly == (surface.surfaceType == Surface.patch)) {
							counter = writeFace(counter, fos, surface);
						}
					}
				} else {
					if( skip.containsKey( q3bsp.shaders[surface.shaderNum].shader) ) {
						if( skipItem.patchOnly == (surface.surfaceType == Surface.patch)) {
							// skip it
						} else {
							counter = writeFace(counter, fos, surface);
						}
					} else {
						counter = writeFace(counter, fos, surface);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(counter + " indices written (currently short = 2 bytes)");
		return counter;
	}

	private int writeFace(int counter, FileOutputStream fos, Surface surface) throws IOException {
		for(int k = 0; k < surface.numIndexes; ++k) {
			int i = surface.firstVert + q3bsp.indexes.get(surface.firstIndex + k);
			fos.write( char2ByteArray( (char)i) );
			counter++;
		}
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

	public void writeColorsAsFloats( String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( float2ByteArray( (float)vertex.color.x)  );
				fos.write( float2ByteArray( (float)vertex.color.y)  );
				fos.write( float2ByteArray( (float)vertex.color.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("colors written");
	}

	// This methods seems to be buggy. Not sure why. 
	public void writeColorsAsBytes( String filename) {
		try ( FileOutputStream fos = new FileOutputStream(new File(basePath, filename))) {
			for (Vertex vertex : q3bsp.vertices) {
				fos.write( colorFloat2ColorByte( (float)vertex.color.x) & 0xff );
				fos.write( colorFloat2ColorByte( (float)vertex.color.y) & 0xff );
				fos.write( colorFloat2ColorByte( (float)vertex.color.z) & 0xff );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("colors written");
	}
}
