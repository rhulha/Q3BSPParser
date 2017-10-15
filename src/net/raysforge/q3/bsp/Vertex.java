package net.raysforge.q3.bsp;

import java.io.IOException;

import net.raysforge.q3.map.Point;
import static net.raysforge.q3.bsp.DecimalFormater.f; 

public class Vertex {
	
	/*
		float[3] position
		float[2][2] texcoord
		float[3] normal
		ubyte[4] color
	*/

	public Point position; // Vertex position.
	public Point texCoord; // Vertex texture coordinates.
	public Point lmCoord; // Vertex lightmap coordinates.
	public Point normal; // Vertex normal.

	public Point color; // Vertex color. RGBA.

	public final static int size = 44;

	public Vertex( Point position, Point texCoord, Point lmCoord, Point normal, Point color) {
		this.position = position;
		this.texCoord = texCoord;
		this.lmCoord = lmCoord;
		this.normal = normal;
		this.color = color;
	}

	public Vertex(BinaryReader br) throws IOException {
		this.position = new Point( br.readFloat(3));
		this.texCoord = new Point( br.readFloat(2));
		this.lmCoord = new Point(  br.readFloat(2));
		this.normal = new Point(   br.readFloat(3));
		this.color = new Point(    br.readBytes(4)); // alpha channel is ignored
	}
	
	@Override
	public String toString() {
		return f(position.x) + ' ' + f(position.y) + ' ' + f(position.z) + " # " + f(texCoord.x) + ' ' + f(texCoord.y) + " # " + f(normal.x) + ' ' + f(normal.y) + ' ' + f(normal.z);
	}

}
