package net.raysforge.bsp.q2.model;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Face {
	public char plane;
	public char plane_side;
	public int first_edge; // unsigned
	public char num_edges;
	public char texture_info;
	public byte lightmap_styles[]; // 4 bytes
	public int lightmap_offset; // unsigned

	public Face(LittleEndianDataInputStream ledis) throws IOException {
		this.plane = ledis.readChar();
		this.plane_side = ledis.readChar();
		this.first_edge = ledis.readInt();
		this.num_edges = ledis.readChar();
		this.texture_info = ledis.readChar();
		this.lightmap_styles = ledis.readNBytes(4);
		this.lightmap_offset = ledis.readInt();
	}
}
