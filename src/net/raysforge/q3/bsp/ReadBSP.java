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
			lumps[i] = br.readBytes(length);
			//System.out.println(offset);
			//System.out.println(length);
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

	public Model[] getModels() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Models);
		Model[] models = new Model[br.length() / Model.size];
		for (int i = 0; i < models.length; i++) {
			models[i] = new Model(br);
		}
		return models;
	}

	// int[n][3] => brushside, n_brushsides, texture
	public int[][] getBrushes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Brushes);
		int[][] brushes = new int[br.length() / 12][];
		for (int i = 0; i < brushes.length; i++) {
			brushes[i] = br.readInt( 3);
		}
		return brushes;
	}

	// int[n][2] => plane, texture
	public int[][] getBrushSides() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Brushsides);
		int[][] brushsides = new int[br.length() / 8][];
		for (int i = 0; i < brushsides.length; i++) {
			brushsides[i] = br.readInt( 2);
		}
		return brushsides;
	}
		
	public Vertex[] getVertexes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Vertexes);
		Vertex[] vertexes = new Vertex[br.length() / Vertex.size];
		for (int i = 0; i < vertexes.length; i++) {
			vertexes[i] = new Vertex(br);
		}
		return vertexes;
	}

	public int[] getMeshverts() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Meshverts);
		int[] meshverts = br.readInt(br.length() / 4);
		return meshverts;
	}

	public Effect[] getEffects() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Effects);
		Effect[] effects = new Effect[br.length() / Effect.size];
		for (int i = 0; i < effects.length; i++) {
			effects[i] = new Effect(br.readString(64), br.readInt(), br.readInt());
		}
		return effects;
	}

	public Face[] getFaces() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Faces);
		Face[] faces = new Face[br.length() / Face.size];
		for (int i = 0; i < faces.length; i++) {
			faces[i] = new Face(br);
		}
		return faces;
	}

	public byte[] getLightmaps() throws IOException {
		return getLumpReader(LumpTypes.Lightmaps).readBytes(128*128*3);
	}

	public Lightvol[] getLightvols() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Lightvols);
		Lightvol[] lightvols = new Lightvol[br.length() / Lightvol.size];
		for (int i = 0; i < lightvols.length; i++) {
			lightvols[i] = new Lightvol(br);
		}
		return lightvols;
	}

	public Visdata getVisdata() throws IOException {
		return new Visdata(getLumpReader(LumpTypes.Visdata));
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
		BinaryReader br = getLumpReader(LumpTypes.Textures);
		Texture[] textures = new Texture[br.length() / Texture.size];
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture(br.readString(64), br.readInt(), br.readInt());
		}
		return textures;
	}

	public String getEntities() {
		return new String(lumps[LumpTypes.Entities.ordinal()]);
	}
}
