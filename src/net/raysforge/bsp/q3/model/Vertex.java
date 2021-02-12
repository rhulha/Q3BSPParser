package net.raysforge.bsp.q3.model;

import static net.raysforge.generic.DecimalFormater.f;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;
import net.raysforge.map.Point; 

public class Vertex {
	
	/*
		float[3] position
		float[2][2] texcoord
		float[3] normal
		ubyte[4] color
	*/

	public Point xyz; // Vertex position.
	public Point st; // Vertex texture coordinates.
	public Point lightmap; // Vertex lightmap coordinates.
	public Point normal; // Vertex normal.

	public Point color; // Vertex color. RGBA.
	
	public static Point min = new Point(0,0,0);
	public static Point max = new Point(0,0,0);
	
	public final static int size = 44;

	public Vertex( Point xyz, Point st, Point lightmap, Point normal, Point color) {
		this.xyz = xyz;
		this.st = st;
		this.lightmap = lightmap;
		this.normal = normal;
		this.color = color;
		
		min.x = Math.min( min.x, xyz.x );
		min.y = Math.min( min.y, xyz.y );
		min.z = Math.min( min.z, xyz.z );
		max.x = Math.max( max.x, xyz.x );
		max.y = Math.max( max.y, xyz.y );
		max.z = Math.max( max.z, xyz.z );

	}

	public Vertex(BinaryReader br) throws IOException {
		this.xyz = new Point( br.readFloat(3));
		this.st = new Point( br.readFloat(2));
		this.lightmap = new Point(  br.readFloat(2));
		this.normal = new Point(   br.readFloat(3));
		this.color = new Point(    br.readBytes(4)); // alpha channel is ignored
	}
	
	@Override
	public String toString() {
		return f(xyz.x) + ' ' + f(xyz.y) + ' ' + f(xyz.z) + " # " + f(st.x) + ' ' + f(st.y) + " # " + f(normal.x) + ' ' + f(normal.y) + ' ' + f(normal.z);
	}

}
