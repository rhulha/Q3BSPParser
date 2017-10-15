package net.raysforge.q3.bsp;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadBSP {

	private byte[][] lumps;
	private BinaryReader br;

	public ReadBSP(String name) throws IOException {

		br = new BinaryReader(new RandomAccessFile(name, "r"));
		
		String magic = br.readString(4);
		int version = br.readInt();
		assert_ (magic.equals("IBSP"));
		assert_ (version == 46);

		int count = 0;
		
		int[] lumpsOffset = new int[17];
		int[] lumpsLength = new int[17];
		for (int i = 0; i < lumpsOffset.length; i++) {
			lumpsOffset[i] = br.readInt();
			lumpsLength[i] = br.readInt();
		}

		
		lumps = new byte[17][];
		for (int i = 0; i < 17; i++) {
			int offset = lumpsOffset[i];
			int length = lumpsLength[i];
			count+=length;
			br.seek(offset);
			lumps[i] = new byte[length];
			System.out.println(offset);
			System.out.println(length);
			br.readFully(lumps[i]);
		}
		
		assert_( count+145 == br.length());
	}

	private void assert_(boolean b) {
		if( ! b)
			throw new RuntimeException("assert failed");
		
	}

	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");

		String entities = bsp.getEntities();
		System.out.println(entities);

		Texture textures[] = bsp.getTextures();
		

		for (int i = 0; i < textures.length; i++) {
			Texture texture = textures[i];
			System.out.println(texture.name);
		}

		bsp.close();

	}

	private void close() throws IOException {
		br.close();
		
	}

	private Texture[] getTextures() throws IOException {
		Texture[] textures = new Texture[lumps[1].length / 72];
		BinaryReader br = new BinaryReader(lumps[1]);
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture( br.readString(64), br.readInt(), br.readInt());
		}
		return textures;
	}

	private String getEntities() {

		return new String( lumps[0]);
	}


}

class Texture  {
	public String name;
	public int flags;
	public int contents;

	public Texture(String name, int flags, int contents) {
		this.name = name;
		this.flags = flags;
		this.contents = contents;
	}
}
