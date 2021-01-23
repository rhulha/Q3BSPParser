package net.raysforge.bsp.q2.model;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;

public class BrushSide {
	public char plane;
	public short texture;

	public BrushSide(LittleEndianDataInputStream ledis) throws IOException {
		plane = ledis.readChar();
		texture = ledis.readShort();
	}
}
