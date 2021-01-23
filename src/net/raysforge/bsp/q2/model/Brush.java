package net.raysforge.bsp.q2.model;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Brush {
	public int brushside; // First brushside for brush.
	public int n_brushsides; // Number of brushsides for brush.
	public int content; // Texture index.

	public Brush(int brushside, int n_brushsides, int content) {
		this.brushside = brushside;
		this.n_brushsides = n_brushsides;
		this.content = content;
	}

	public Brush(LittleEndianDataInputStream ledis) throws IOException {
		this.brushside = ledis.readInt();
		this.n_brushsides = ledis.readInt();
		this.content = ledis.readInt();
	}

}
