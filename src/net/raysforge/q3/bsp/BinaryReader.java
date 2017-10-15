package net.raysforge.q3.bsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryReader {

	private RandomAccessFile raf;
	private ByteArrayInputStream bais;
	private byte[] buffer;

	public BinaryReader(RandomAccessFile raf) {
		this.raf = raf;
	}

	public BinaryReader(byte[] buffer) {
		this.buffer = buffer;
		this.bais = new ByteArrayInputStream(buffer);
	}

	public int readInt() throws IOException {
		byte[] b = new byte[4];
		if (bais != null)
			bais.read(b);
		else
			raf.readFully(b);
		return ((b[3] & 0xff) << 24) + ((b[2] & 0xff) << 16) + ((b[1] & 0xff) << 8) + (b[0] & 0xff);
	}

	public int[] readInt(int i) throws IOException {
		int[] buf = new int[i];
		for (int j = 0; j < buf.length; j++) {
			buf[j] = readInt();
		}
		return buf;
	}

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public float[] readFloat(int i) throws IOException {
		float[] buf = new float[i];
		for (int j = 0; j < buf.length; j++) {
			buf[j] = readFloat();
		}
		return buf;
	}

	public void close() throws IOException {
		if (bais != null)
			bais.close();
		else
			raf.close();
	}

	public String readString(int len) throws IOException {
		byte[] b = new byte[len];
		if (bais != null)
			bais.read(b);
		else
			raf.readFully(b);
		return new String(b);
	}

	public void seek(int offset) throws IOException {
		if (bais != null) {
			bais.reset();
			bais.skip(offset);
		} else
			raf.seek(offset);

	}

	public int length() throws IOException {
		return (int)(bais != null ? buffer.length : raf.length());
	}

	public void readFully(byte[] b) throws IOException {
		if (bais != null)
			bais.read(b);
		else
			raf.readFully(b);
	}

}
