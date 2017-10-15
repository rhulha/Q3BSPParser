package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import net.raysforge.q3.map.Point;

public class BSPUtils {

	static {
		Locale.setDefault(new Locale("en", "US"));
	}
	static DecimalFormat decimalFormat=new DecimalFormat("#.###################");
	public static String f ( double d)
	{
		String format = decimalFormat.format(d);
		if( format.equals("-0"))
			format = "0";
		return format;
	}

	
	public static void writeVerts(List<Vertex> vertexes, String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(filename)) {
			for (Vertex vertex : vertexes) {
				fos.write( BSPUtils.float2ByteArray( (float)vertex.position.x) );
				fos.write( BSPUtils.float2ByteArray( (float)vertex.position.y) );
				fos.write( BSPUtils.float2ByteArray( (float)vertex.position.z) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeIndices(Face[] faces, List<Integer> meshVerts, String filename) throws IOException {
		try ( FileOutputStream fos = new FileOutputStream(filename)) {
			
			for (Face face : faces) {
				for(int k = 0; k < face.n_meshverts; ++k) {
					int i = face.vertex + meshVerts.get(face.meshvert + k);
					fos.write( BSPUtils.char2ByteArray( (char)i) );
                }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public float ba2f( byte[] b) {
		return ByteBuffer.wrap(b).getFloat();
	}
	
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

	public void verifyValues() {
		/*
		Plane[] planes = bsp.getPlanes();
		for (Plane plane : planes) {
			String format = f(plane.normal.x);
			System.out.println("XXX: " + format + " q3bsp.js:133");
		}
		Vertex[] vertexes = bsp.getVertexes();
		for (Vertex v : vertexes) {
			String s = v.toString();
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
		int[] meshverts = bsp.getMeshverts();
		for (int i : meshverts) {
			System.out.print(i + ",");
		}
		Face[] faces = bsp.getFaces();
		for (Face fc : faces) {
			String s = fc.toString();
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
		*/
	}

	
	// this method is broken and using the wrong vertex indexes. writeIndices is better
	public static void objWriter() throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		List<Vertex> vertexes = bsp.getVertexes();
		Face[] faces = bsp.getFaces();
		
		try ( FileWriter fw = new FileWriter("q3dm17new.obj")) {
			
			for (Face face : faces) {
				if( face.type == Face.mesh) {
					//System.out.println(face.vertex + ": " + face.n_vertexes);
					//System.out.println(face.meshvert + ": " + face.n_meshverts);
					for( int i=0; i < face.n_vertexes; i++) {
						Vertex v = vertexes.get(face.vertex+i);
						Point p = v.position;
						fw.write("v " + p.x +" "+p.y+" "+p.z+"\r\n");
					}
				}
			}
			
			int x=1;
			for (Face face : faces) {
				if( face.type == Face.mesh) {
					for( int i=0; i < face.n_vertexes; i+=3) {
						fw.write("f " + x +" "+(x+1)+" "+(x+2)+"\r\n");
						x+=3;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
