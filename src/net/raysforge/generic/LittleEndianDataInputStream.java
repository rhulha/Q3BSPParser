package net.raysforge.generic;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput {

	public LittleEndianDataInputStream(InputStream in) {
		super(in);
	}

	public String readLine() {
		throw new UnsupportedOperationException("readLine is not supported");
	}

	public void readFully(byte[] b) throws IOException {
		throw new UnsupportedOperationException("readFully is not supported");
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		throw new UnsupportedOperationException("readFully is not supported");
	}

	@Override
	public int skipBytes(int n) throws IOException {
		return (int) in.skip(n);
	}

	@Override
	public int readUnsignedByte() throws IOException {
		int b1 = in.read();
		if (0 > b1) {
			throw new EOFException();
		}
		return b1;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		byte b1 = readAndCheckByte();
		byte b2 = readAndCheckByte();
		return (0xff & 0) << 24 | (0xff & 0) << 16 | (0xff & b2) << 8 | (0xff & b1) << 0;
	}

	@Override
	public int readInt() throws IOException {
		byte b1 = readAndCheckByte();
		byte b2 = readAndCheckByte();
		byte b3 = readAndCheckByte();
		byte b4 = readAndCheckByte();
		return (0xff & b4) << 24 | (0xff & b3) << 16 | (0xff & b2) << 8 | (0xff & b1) << 0;
	}

	@Override
	public long readLong() throws IOException {
		byte b1 = readAndCheckByte();
		byte b2 = readAndCheckByte();
		byte b3 = readAndCheckByte();
		byte b4 = readAndCheckByte();
		byte b5 = readAndCheckByte();
		byte b6 = readAndCheckByte();
		byte b7 = readAndCheckByte();
		byte b8 = readAndCheckByte();
		return (long) (0xff & b8) << 56 | (0xff & b7) << 48 | (0xff & b6) << 40 | (0xff & b5) << 32 | (0xff & b4) << 24 | (0xff & b3) << 16 | (0xff & b2) << 8 | (0xff & b1) << 0;
	}

	@Override
	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public String readUTF() throws IOException {
		return new DataInputStream(in).readUTF();
	}

	@Override
	public short readShort() throws IOException {
		return (short) readUnsignedShort();
	}

	@Override
	public char readChar() throws IOException {
		return (char) readUnsignedShort();
	}

	@Override
	public byte readByte() throws IOException {
		return (byte) readUnsignedByte();
	}

	@Override
	public boolean readBoolean() throws IOException {
		return readUnsignedByte() != 0;
	}

	private byte readAndCheckByte() throws IOException, EOFException {
		int b1 = in.read();
		if (-1 == b1) {
			throw new EOFException();
		}
		return (byte) b1;
	}

	public byte[] readBytes(int len) throws IOException {
		byte bytes[] = new byte[len];
		read(bytes);
		return bytes;

	}

	public byte[] readBytesUntilEnd() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while (true) {
				baos.write(readByte());
			}
		} catch (EOFException eof) {
		}
		return baos.toByteArray();
	}

}