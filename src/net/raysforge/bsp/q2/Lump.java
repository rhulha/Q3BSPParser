package net.raysforge.bsp.q2;

import java.io.ByteArrayInputStream;

import net.raysforge.generic.LittleEndianDataInputStream;

public class Lump {
	public int offset;
	public int length;
	public byte[] data;
	public Lump(int offset, int length, byte[] data) {
		this.offset = offset;
		this.length = length;
		this.data = data;
	}
	
	public LittleEndianDataInputStream getLittleEndianDataInputStream() {
		byte chunk[] = new byte[length];
		System.arraycopy(data, offset, chunk, 0, length);
		ByteArrayInputStream bais = new ByteArrayInputStream(chunk);
		return new LittleEndianDataInputStream(bais);
	}
}
