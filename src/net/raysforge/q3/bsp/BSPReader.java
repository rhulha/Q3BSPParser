package net.raysforge.q3.bsp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.q3.map.Plane;
import net.raysforge.q3.map.Point;

public class BSPReader {

	private byte[][] lumps;

	public BSPReader(String name) throws IOException {

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
			System.out.println( LumpTypes.values()[i].toString() + " " +  offset + " " + length);
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

	public int[] getLeafSurfaces() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.LeafSurfaces);
		return br.readInt(br.length() / 4);
	}

	public int[] getLeafBrushes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.LeafBrushes);
		return br.readInt(br.length() / 4);
	}

	public Model[] getModels() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Models);
		Model[] models = new Model[br.length() / Model.size];
		for (int i = 0; i < models.length; i++) {
			models[i] = new Model(br);
		}
		return models;
	}

	// int[n][3] => brushside, n_brushsides, shader
	public int[][] getBrushes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Brushes);
		int[][] brushes = new int[br.length() / 12][];
		for (int i = 0; i < brushes.length; i++) {
			brushes[i] = br.readInt( 3);
		}
		return brushes;
	}

	// int[n][2] => plane, shader
	public int[][] getBrushSides() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.BrushSides);
		int[][] brushsides = new int[br.length() / 8][];
		for (int i = 0; i < brushsides.length; i++) {
			brushsides[i] = br.readInt( 2);
		}
		return brushsides;
	}
		
	public List<Vertex> getDrawVerts() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.DrawVerts);
		int length = br.length() / Vertex.size;
		List<Vertex> vertexes = new ArrayList<Vertex>(length);
		for (int i = 0; i < length; i++) {
			vertexes.add( new Vertex(br));
		}
		return vertexes;
	}

	public List<Integer> getDrawIndexes() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.DrawIndexes);
		List<Integer> drawIndexes = new ArrayList<Integer>();
		int length = br.length() / 4;
		for (int i = 0; i < length; i++) {
			drawIndexes.add( br.readInt());
		}
		return drawIndexes;
	}

	public Fog[] getFogs() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Fogs);
		Fog[] fogs = new Fog[br.length() / Fog.size];
		for (int i = 0; i < fogs.length; i++) {
			fogs[i] = new Fog(br.readString(64), br.readInt(), br.readInt());
		}
		return fogs;
	}

	public Surface[] getSurfaces() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Surfaces);
		Surface[] surfaces = new Surface[br.length() / Surface.size];
		for (int i = 0; i < surfaces.length; i++) {
			surfaces[i] = new Surface(br);
		}
		return surfaces;
	}

	public List<byte[]> getLightmaps() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Lightmaps);
		int count = br.length() / (128*128*3);
		List<byte[]> list = new ArrayList<byte[]>(count);
		
		for (int i = 0; i < count; i++) {
			list.add(br.readBytes(128*128*3));
			
		}
		return list;
	}

	public LightGrid[] getLightGrid() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.LightGrid);
		LightGrid[] lightgrid = new LightGrid[br.length() / LightGrid.size];
		for (int i = 0; i < lightgrid.length; i++) {
			lightgrid[i] = new LightGrid(br);
		}
		return lightgrid;
	}

	public Visibility getVisibility() throws IOException {
		return new Visibility(getLumpReader(LumpTypes.Visibility));
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

	public Shader[] getShaders() throws IOException {
		BinaryReader br = getLumpReader(LumpTypes.Shaders);
		Shader[] shaders = new Shader[br.length() / Shader.size];
		for (int i = 0; i < shaders.length; i++) {
			String name = br.readString(64);
			name = name.substring(0, name.indexOf(0));
			shaders[i] = new Shader( name, br.readInt(), br.readInt());
		}
		return shaders;
	}

	public Map<String, List<Map<String, String>>> getEntities() throws IOException {
		String str = new String(lumps[LumpTypes.Entities.ordinal()]);
		EntitiesParser ep = new EntitiesParser(str);
		return ep.parse();
	}
}