package net.raysforge.q2.bsp;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Vertex {
	public float x;
	public float y;
	public float z;

	public Vertex(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vertex(LittleEndianDataInputStream ledis) throws IOException {
		x = ledis.readFloat();
		y = ledis.readFloat();
		z = ledis.readFloat();
	}
}
