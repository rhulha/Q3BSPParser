package net.raysforge.bsp.q2.model;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Vertex {
	public float x;
	public float y;
	public float z;

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vertex(LittleEndianDataInputStream ledis) throws IOException {
		x = ledis.readFloat();
		y = ledis.readFloat();
		z = ledis.readFloat();
	}

	@Override
	public String toString() {
		return "Vertex [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public Vertex mult(double d) {
		x*=d;
		y*=d;
		z*=d;
		return this;
	}
	
}
