package net.raysforge.q2.bsp;

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
