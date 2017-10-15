package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Locale;

public class Main {
	
	
	public void objWriter() throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		Vertex[] vertexes = bsp.getVertexes();
		Face[] faces = bsp.getFaces();
		
		try ( FileWriter fw = new FileWriter("q3dm17new.obj")) {
			
			for (Face face : faces) {
				if( face.type == Face.mesh) {
					//System.out.println(face.vertex + ": " + face.n_vertexes);
					//System.out.println(face.meshvert + ": " + face.n_meshverts);
					for( int i=0; i < face.n_vertexes; i++) {
						Vertex v = vertexes[face.vertex+i];
						float[] p = v.position;
						fw.write("v " + p[0] +" "+p[1]+" "+p[2]+"\r\n");
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
	
	public void verifyValues() {
		/*
		Plane[] planes = bsp.getPlanes();
		for (Plane plane : planes) {
			String format = decimalFormat.format(plane.normal.x);
			if( format.equals("-0"))
				format = "0";
			System.out.println("XXX: " + format + " q3bsp.js:133");
		}
		*/
		/*
		Vertex[] vertexes = bsp.getVertexes();
		for (Vertex v : vertexes) {
			String s = f(v.position[0]) + ' ' + f(v.position[1]) + ' ' + f(v.position[2]) + " # " + f(v.texcoord[0]) + ' ' + f(v.texcoord[1]) + " # " + f(v.normal[0]) + ' ' + f(v.normal[1]) + ' ' + f(v.normal[2]);
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
		*/
		/*
		int[] meshverts = bsp.getMeshverts();
		for (int i : meshverts) {
			System.out.print(i + ",");
		}
		*/
		/*
		Face[] faces = bsp.getFaces();
		for (Face fc : faces) {
			// message: 'XXX: ' + f.type + ' ' + f.shader + ' ' + f.vertex + ' # ' + f.vertCount + ' ' + f.meshVert + ' # ' + f.meshVertCount + ' ' + f.lightmap + ' ' + f.size[0] + ' ' + f.size[1]
			String s = fc.type + " " + fc.texture + " " + fc.vertex + " # " + fc.n_vertexes + " " + fc.meshvert + " # " + fc.n_meshverts + " " + fc.lm_index + " " + fc.patch_size[0] + " " + fc.patch_size[1];
			System.out.println("XXX: " + s + " q3bsp.js:133");
		}
		*/
	}
	
	public float ba2f( byte[] b) {
		return ByteBuffer.wrap(b).getFloat();
	}
	
	public static byte[] float2ByteArray (float value) {
		// .order(ByteBuffer.LITTE_ENDIAN)
		return ByteBuffer.allocate(4).putFloat(value).array();
	}
	
	public static void writeVerts(ReadBSP bsp) throws IOException {
		Vertex[] vertexes = bsp.getVertexes();
		
		try ( FileOutputStream fos = new FileOutputStream("q3dm17.verts")) {
			
			for (Vertex vertex : vertexes) {
				fos.write( float2ByteArray( vertex.position[0]) );
				fos.write( float2ByteArray( vertex.position[1]) );
				fos.write( float2ByteArray( vertex.position[2]) );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeIndices(ReadBSP bsp) throws IOException {
		Face[] faces = bsp.getFaces();
		
		try ( FileOutputStream fos = new FileOutputStream("q3dm17.indices")) {
			
			for (Face face : faces) {
				fos.write( float2ByteArray( vertex.position[0]) );
				fos.write( float2ByteArray( vertex.position[1]) );
				fos.write( float2ByteArray( vertex.position[2]) );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		
		writeVerts(bsp);

		//Face[] faces = bsp.getFaces();
		
			
		
		//bsp.getNodes();
		
		
	}
}
