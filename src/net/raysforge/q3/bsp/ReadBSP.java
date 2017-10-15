package net.raysforge.q3.bsp;

import java.io.IOException;
import java.io.RandomAccessFile;

import net.raysforge.q3.map.Plane;
import net.raysforge.q3.map.Point;

public class ReadBSP {

	private byte[][] lumps;

	public ReadBSP(String name) throws IOException {

		BinaryReader br = new BinaryReader(new RandomAccessFile(name, "r"));

		String magic = br.readString(4);
		int version = br.readInt();
		assert_(magic.equals("IBSP"));
		assert_(version == 46);

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
			count += length;
			br.seek(offset);
			lumps[i] = new byte[length];
			System.out.println(offset);
			System.out.println(length);
			br.readFully(lumps[i]);
		}

		assert_(count + 145 == br.length());

		br.close();
	}

	private void assert_(boolean b) {
		if (!b)
			throw new RuntimeException("assert failed");
	}

	public byte[] getLump(LumpTypes t) {
		return lumps[t.ordinal()];
	}

	public BinaryReader getLumpReader(LumpTypes t) {
		return new BinaryReader(getLump(t));
	}

	public Node[] getNodes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Nodes);
		Node[] nodes = new Node[br.length() / Node.size];

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(br);
		}
		return nodes;
	}

	public Leaf[] getLeafs() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Leafs);
		Leaf[] leafs = new Leaf[br.length() / Leaf.size];

		for (int i = 0; i < leafs.length; i++) {
			leafs[i] = new Leaf(br);
		}
		return leafs;
	}

	public int[] getLeaffaces() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Leaffaces);
		int[] leaffaces = br.readInt(br.length() / 4);
		return leaffaces;
	}

	public int[] getLeafbrushes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Leafbrushes);
		int[] leafbrushes = br.readInt(br.length() / 4);
		return leafbrushes;
	}

	public Plane[] getPlanes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Planes);
		Plane[] planes = new Plane[br.length() / Plane.size];
		for (int i = 0; i < planes.length; i++) {
			float x = br.readFloat();
			float y = br.readFloat();
			float z = br.readFloat();
			float d = br.readFloat();
			planes[i] = new Plane(new Point(x, y, z), d);
		}
		return planes;
	}

	public Texture[] getTextures() throws IOException {
		Texture[] textures = new Texture[lumps[LumpTypes.Textures.ordinal()].length / Texture.size];
		BinaryReader br = new BinaryReader(lumps[LumpTypes.Textures.ordinal()]);
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture(br.readString(64), br.readInt(), br.readInt());
		}
		return textures;
	}

	public String getEntities() {
		return new String(lumps[LumpTypes.Entities.ordinal()]);
	}
}
