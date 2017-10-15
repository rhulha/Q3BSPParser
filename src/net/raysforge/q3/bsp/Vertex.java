package net.raysforge.q3.bsp;

import java.io.IOException;
import static net.raysforge.q3.bsp.BSPUtils.f; 

public class Vertex {
	
	/*
		float[3] position
		float[2][2] texcoord
		float[3] normal
		ubyte[4] color
	*/

	public float[] position; // Vertex position.
	public float[] texcoord; // Vertex texture coordinates. 0=surface, 1=lightmap.
	public float[] normal; // Vertex normal.

	public byte[] color; // Vertex color. RGBA.

	public final static int size = 44;

	public Vertex(float[] position, float[] texcoord, float[] normal, byte[] color) {
		this.position = position;
		this.texcoord = texcoord;
		this.normal = normal;
		this.color = color;
	}

	public Vertex(BinaryReader br) throws IOException {
		this.position = br.readFloat(3);
		this.texcoord = br.readFloat(4);
		this.normal = br.readFloat(3);
		this.color = br.readBytes(4);
	}
	
	@Override
	public String toString() {
		return f(position[0]) + ' ' + f(position[1]) + ' ' + f(position[2]) + " # " + f(texcoord[0]) + ' ' + f(texcoord[1]) + " # " + f(normal[0]) + ' ' + f(normal[1]) + ' ' + f(normal[2]);
	}

}
