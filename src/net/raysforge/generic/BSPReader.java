package net.raysforge.generic;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BSPReader {

	protected byte[][] lumps;

	public BSPReader(String name, int lumpSize, int version) throws IOException {

		BinaryReader br = new BinaryReader(new RandomAccessFile(name, "r"));

		String magic = br.readString(4);
		int bsp_version = br.readInt();
		assert_(magic.equals("IBSP"));
		assert_(bsp_version == version);

		int count = 0;

		int[] lumpsOffset = new int[lumpSize];
		int[] lumpsLength = new int[lumpSize];
		for (int i = 0; i < lumpsOffset.length; i++) {
			lumpsOffset[i] = br.readInt();
			lumpsLength[i] = br.readInt();
		}

		lumps = new byte[lumpSize][];
		for (int i = 0; i < lumpSize; i++) {
			int offset = lumpsOffset[i];
			int length = lumpsLength[i];
			count += length;
			br.seek(offset);
			lumps[i] = br.readBytes(length);
			//System.out.println( LumpTypes.values()[i].toString() + " " +  offset + " " + length);
		}
		
		System.out.println(br.length());
		System.out.println(count+145);

		//assert_(count + 145 == br.length());

		br.close();
	}

	private void assert_(boolean b) {
		if (!b)
			throw new RuntimeException("assert failed");
	}

	public byte[] getLump(int lump) {
		return lumps[lump];
	}

	public BinaryReader getLumpReader(int lump) {
		return new BinaryReader(getLump(lump));
	}

}
