package net.raysforge.bsp.q2.model;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Edge {

	public char edge1;
	public char edge2;

	public Edge(LittleEndianDataInputStream ledis) throws IOException {
		edge1 = ledis.readChar();
		edge2 = ledis.readChar();
	}
}
