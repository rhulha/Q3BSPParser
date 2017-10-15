package net.raysforge.q3.bsp;

import java.io.IOException;

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
		this.color = new byte[4]; 
		br.readFully(this.color);
	}

}
