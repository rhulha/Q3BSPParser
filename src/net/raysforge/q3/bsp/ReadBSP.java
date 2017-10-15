package net.raysforge.q3.bsp;

import java.io.IOException;
import java.io.RandomAccessFile;

import net.raysforge.q3.map.Plane;
import net.raysforge.q3.map.Point;

enum LumpTypes {
	Entities,
	Textures,
	Planes,
	Nodes,
	Leafs,
	Leaffaces,
	Leafbrushes,
	Models,
	Brushes,
	Brushsides,
	Vertexes,
	Meshverts,
	Effects,
	Faces,
	Lightmaps,
	Lightvols,
	Visdata;
	public static final int size = LumpTypes.values().length;
}

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
		
		int[] lumpsOffset = new int[LumpTypes.size];
		int[] lumpsLength = new int[LumpTypes.size];
		for (int i = 0; i < lumpsOffset.length; i++) {
			lumpsOffset[i] = br.readInt();
			lumpsLength[i] = br.readInt();
		}

		
		lumps = new byte[LumpTypes.size][];
		for (int i = 0; i < LumpTypes.size; i++) {
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

		Plane[] planes = bsp.getPlanes();
		
		bsp.close();

	}

	public Plane[] getPlanes() throws IOException {
		Plane[] planes = new Plane[lumps[LumpTypes.Planes.ordinal()].length / 16];
		BinaryReader br = new BinaryReader(lumps[LumpTypes.Planes.ordinal()]);
		for (int i = 0; i < planes.length; i++) {
			float x = br.readFloat();
			float y = br.readFloat();
			float z = br.readFloat();
			float d = br.readFloat();
			planes[i] = new Plane( new Point( x,y,z), d);
		}
		return planes;
	}

	public Texture[] getTextures() throws IOException {
		Texture[] textures = new Texture[lumps[LumpTypes.Textures.ordinal()].length / 72];
		BinaryReader br = new BinaryReader(lumps[LumpTypes.Textures.ordinal()]);
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture( br.readString(64), br.readInt(), br.readInt());
		}
		return textures;
	}

	public String getEntities() {
		return new String( lumps[LumpTypes.Entities.ordinal()]);
	}

	public void close() throws IOException {
		br.close();
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
