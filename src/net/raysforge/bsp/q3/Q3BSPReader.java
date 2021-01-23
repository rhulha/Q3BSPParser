package net.raysforge.bsp.q3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.BSPReader;
import net.raysforge.bsp.q3.model.Fog;
import net.raysforge.bsp.q3.model.Leaf;
import net.raysforge.bsp.q3.model.LightGrid;
import net.raysforge.bsp.q3.model.Model;
import net.raysforge.bsp.q3.model.Node;
import net.raysforge.bsp.q3.model.Q3LumpTypes;
import net.raysforge.bsp.q3.model.Shader;
import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.bsp.q3.model.Vertex;
import net.raysforge.bsp.q3.model.Visibility;
import net.raysforge.generic.BinaryReader;
import net.raysforge.map.Plane;
import net.raysforge.map.Point;

public class Q3BSPReader extends BSPReader {

	public Q3BSPReader(String fileName) throws IOException {
		super( fileName, Q3LumpTypes.size, 46);
	}

	public Node[] getNodes() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Nodes.ordinal());
		Node[] nodes = new Node[br.length() / Node.size];

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(br);
		}
		return nodes;
	}

	public Leaf[] getLeafs() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Leafs.ordinal());
		Leaf[] leafs = new Leaf[br.length() / Leaf.size];

		for (int i = 0; i < leafs.length; i++) {
			leafs[i] = new Leaf(br);
		}
		return leafs;
	}

	public int[] getLeafSurfaces() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.LeafSurfaces.ordinal());
		return br.readInt(br.length() / 4);
	}

	public int[] getLeafBrushes() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.LeafBrushes.ordinal());
		return br.readInt(br.length() / 4);
	}

	public Model[] getModels() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Models.ordinal());
		Model[] models = new Model[br.length() / Model.size];
		for (int i = 0; i < models.length; i++) {
			models[i] = new Model(br);
		}
		return models;
	}

	// int[n][3] => brushside, n_brushsides, shader
	public int[][] getBrushes() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Brushes.ordinal());
		int[][] brushes = new int[br.length() / 12][];
		for (int i = 0; i < brushes.length; i++) {
			brushes[i] = br.readInt( 3);
		}
		return brushes;
	}

	// int[n][2] => plane, shader
	public int[][] getBrushSides() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.BrushSides.ordinal());
		int[][] brushsides = new int[br.length() / 8][];
		for (int i = 0; i < brushsides.length; i++) {
			brushsides[i] = br.readInt( 2);
		}
		return brushsides;
	}
		
	public List<Vertex> getDrawVerts() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.DrawVerts.ordinal());
		int length = br.length() / Vertex.size;
		List<Vertex> vertexes = new ArrayList<Vertex>(length);
		for (int i = 0; i < length; i++) {
			vertexes.add( new Vertex(br));
		}
		return vertexes;
	}

	public List<Integer> getDrawIndexes() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.DrawIndexes.ordinal());
		List<Integer> drawIndexes = new ArrayList<Integer>();
		int length = br.length() / 4;
		for (int i = 0; i < length; i++) {
			drawIndexes.add( br.readInt());
		}
		return drawIndexes;
	}

	public Fog[] getFogs() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Fogs.ordinal());
		Fog[] fogs = new Fog[br.length() / Fog.size];
		for (int i = 0; i < fogs.length; i++) {
			fogs[i] = new Fog(br.readString(64), br.readInt(), br.readInt());
		}
		return fogs;
	}

	public Surface[] getSurfaces() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Surfaces.ordinal());
		Surface[] surfaces = new Surface[br.length() / Surface.size];
		for (int i = 0; i < surfaces.length; i++) {
			surfaces[i] = new Surface(br);
		}
		return surfaces;
	}

	public List<byte[]> getLightmaps() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Lightmaps.ordinal());
		int count = br.length() / (128*128*3);
		List<byte[]> list = new ArrayList<byte[]>(count);
		
		for (int i = 0; i < count; i++) {
			list.add(br.readBytes(128*128*3));
			
		}
		return list;
	}

	public LightGrid[] getLightGrid() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.LightGrid.ordinal());
		LightGrid[] lightgrid = new LightGrid[br.length() / LightGrid.size];
		for (int i = 0; i < lightgrid.length; i++) {
			lightgrid[i] = new LightGrid(br);
		}
		return lightgrid;
	}

	public Visibility getVisibility() throws IOException {
		return new Visibility(getLumpReader(Q3LumpTypes.Visibility.ordinal()));
	}

	public Plane[] getPlanes() throws IOException {
		BinaryReader br = getLumpReader(Q3LumpTypes.Planes.ordinal());
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
		BinaryReader br = getLumpReader(Q3LumpTypes.Shaders.ordinal());
		Shader[] shaders = new Shader[br.length() / Shader.size];
		for (int i = 0; i < shaders.length; i++) {
			String name = br.readString(64);
			name = name.substring(0, name.indexOf(0));
			shaders[i] = new Shader( name, br.readInt(), br.readInt());
		}
		return shaders;
	}

	public Map<String, List<Map<String, String>>> getEntities() throws IOException {
		String str = new String(lumps[Q3LumpTypes.Entities.ordinal()]);
		EntitiesParser ep = new EntitiesParser(str);
		return ep.parse();
	}
}
