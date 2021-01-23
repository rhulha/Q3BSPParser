package net.raysforge.bsp.q2;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import net.raysforge.bsp.q2.model.Brush;
import net.raysforge.bsp.q2.model.BrushSide;
import net.raysforge.bsp.q2.model.Edge;
import net.raysforge.bsp.q2.model.Face;
import net.raysforge.bsp.q2.model.TextureInfo;
import net.raysforge.bsp.q2.model.Vertex;
import net.raysforge.generic.LittleEndianDataInputStream;

public class Quake2BSP {

	private LittleEndianDataInputStream ledis;

	Lumps lumps = new Lumps();
	
	public Face faces[];
	public Brush brushes[];
	public BrushSide brush_sides[];
	public int face_edges[]; // sign is order
	public Edge edges[];
	public Vertex vertices[];
	public TextureInfo tis[];

	public Quake2BSP(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte data[] = fis.readAllBytes();
		fis.close();
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ledis = new LittleEndianDataInputStream(bais);
		
		String magic = new String(ledis.readBytes(4)); // IBSP
		int version = ledis.readInt(); // 38
		
		assert(magic.equals("IBSP"));
		assert(version==38);
		
		Lump lumps[] = new Lump[19];
		for(int i=0; i<19;i++) {
			lumps[i] = new Lump(ledis.readInt(), ledis.readInt(), data);
		}
		
		this.lumps.entities = lumps[0];
		this.lumps.planes = lumps[1];
		this.lumps.vertices = lumps[2];
		// 3,4 skipped
		this.lumps.texture_infos = lumps[5];
		this.lumps.faces = lumps[6];
		// 7,8,9,10
		this.lumps.edges = lumps[11];
		this.lumps.face_edges = lumps[12];
		// 13 models
		this.lumps.brushes = lumps[14];
		this.lumps.brush_sides = lumps[15];
		
		
		// http://jheriko-rtw.blogspot.com/2010/11/dissecting-quake-2-bsp-format.html
		
		parseFaces();
		parseBrushes();
		parseBrushSides();
		parseFaceEdges();
		parseEdges();
		parseVertices();
		
		parseTextureInfos();
	}

	private void parseTextureInfos() throws IOException {
		int n_texture_infos = this.lumps.texture_infos.length / 76;
		tis = new TextureInfo[n_texture_infos];
		LittleEndianDataInputStream ledis = this.lumps.texture_infos.getLittleEndianDataInputStream();
		for (int i = 0; i < n_texture_infos; i++) {
			tis[i] = new TextureInfo(ledis);
		}
	}

	private void parseVertices() throws IOException {
		int n_vertices = this.lumps.vertices.length / 12;
		vertices = new Vertex[n_vertices];
		LittleEndianDataInputStream ledis = this.lumps.vertices.getLittleEndianDataInputStream();
		for (int i = 0; i < n_vertices; i++) {
			vertices[i] = new Vertex(ledis);
		}
	}

	private void parseEdges() throws IOException {
		int n_edges = this.lumps.edges.length / 4;
		edges = new Edge[n_edges];
		LittleEndianDataInputStream edges_ledis = this.lumps.edges.getLittleEndianDataInputStream();
		for (int i = 0; i < n_edges; i++) {
			edges[i] = new Edge(edges_ledis);
		}
	}

	private void parseFaceEdges() throws IOException {
		int n_face_edges = this.lumps.face_edges.length / 4;
		face_edges = new int[n_face_edges];
		LittleEndianDataInputStream face_edges_ledis = this.lumps.face_edges.getLittleEndianDataInputStream();
		for (int i = 0; i < n_face_edges; i++) {
			face_edges[i] = face_edges_ledis.readInt();
		}
	}

	private void parseBrushSides() throws IOException {
		int n_brush_sides = this.lumps.brush_sides.length / 4;
		brush_sides = new BrushSide[n_brush_sides];
		LittleEndianDataInputStream brush_sides_ledis = this.lumps.brush_sides.getLittleEndianDataInputStream();
		for (int i = 0; i < n_brush_sides; i++) {
			brush_sides[i] = new BrushSide(brush_sides_ledis);
		}
	}

	private void parseBrushes() throws IOException {
		int n_brushes = this.lumps.brushes.length / 12;
		brushes = new Brush[n_brushes];
		LittleEndianDataInputStream brushes_ledis = this.lumps.brushes.getLittleEndianDataInputStream();
		for (int i = 0; i < n_brushes; i++) {
			brushes[i] = new Brush(brushes_ledis);
		}
	}

	private void parseFaces() throws IOException {
		int n_faces = this.lumps.faces.length / 20;
		faces = new Face[n_faces];
		LittleEndianDataInputStream faces_ledis = this.lumps.faces.getLittleEndianDataInputStream();
		for (int i = 0; i < n_faces; i++) {
			faces[i] = new Face(faces_ledis);
		}
	}
	
	public void close() throws IOException {
		ledis.close();
	}

}
