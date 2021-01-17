package net.raysforge.generic;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class LittleEndianDataOutputStream {
	private OutputStream os;

	public LittleEndianDataOutputStream(OutputStream os) throws IOException {
		this.os = os;
	}

	public void writeBytes(String string) throws IOException {
		os.write(string.getBytes("UTF-8"));
	}

	public void writeInt32(int i) throws IOException {
		for (int c = 0; c < 4; c++) {
			os.write((byte) (i >>> (c * 8)));
		}
	}

	public void writeNull(int count) throws IOException {
		for (int i = 0; i < count; i++) {
			os.write(0);
		}
	}

	public void activateGzip() throws IOException {
		os.flush();
		os = new GZIPOutputStream(os);
	}

	public void writeLengthAndString(String string) throws IOException {
		writeInt32(string.length());
		writeBytes(string);
	}

	public void close() throws IOException {
		os.close();
	}

	public void write(byte[] data) throws IOException {
		os.write(data);
	}

	public void writeBytes(byte[] data) throws IOException {
		os.write(data);
	}

	public void writeByte(byte b) throws IOException {
		os.write(b);
	}

	public void flush() throws IOException {
		os.flush();
	}

}
