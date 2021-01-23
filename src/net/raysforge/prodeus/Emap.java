package net.raysforge.prodeus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.raysforge.bsp.q2.model.Vertex;
import net.raysforge.commons.StreamUtils;

public class Emap {
	
	private String emap_start_text;
	public List<EmapBrush> brushList = new ArrayList<EmapBrush>();
	private List<EmapNode> nodes = new ArrayList<EmapNode>();
	private List<String> materials;

	public Emap()  {
		try {
			InputStream resourceAsStream = Emap.class.getResourceAsStream("emap_start.txt");
			emap_start_text = StreamUtils.readCompleteInputStream(resourceAsStream, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addBrush(EmapBrush brush) {
		brushList.add(brush);
	}

	public void addNode(EmapNode emapNode) {
		nodes.add(emapNode);
	}
	
	public void writeEMap( String prodeusFile) {
		try ( FileWriter fw = new FileWriter(prodeusFile)) {
			fw.write(emap_start_text);
			for (String material : materials) {
				fw.write(material+"\r\n");
			}
			fw.write("}\r\n"); // close Materials block from emap_start_text
			writeBrushes(fw);
			writeNodes(fw);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeBrushes(FileWriter fw) throws IOException {
		fw.write( "Brushes{\r\n");
		for (EmapBrush brush : brushList) {
			fw.write( "Brush{\r\n");
			fw.write( "parent=-1\r\n");
			fw.write( "layer=-1\r\n");
			fw.write( "pos=0,0,0\r\n");
			fw.write( "points=");
			List<Vertex> points = brush.getPoints();
			boolean sc=false;
			for (Vertex p : points) {
					if(sc)
						fw.write(";");
					fw.write( p.x + "," + p.y + "," + p.z) ;
					sc=true;
			}
			fw.write( "\r\n");
			//writeEdges(fw);
			for (EmapFace face : brush.getFaces()) {
				fw.write( "Face{\r\n");
				fw.write( face.getSurfaceText());
				fw.write( "points=");
				List<Integer> point_idxs = face.getPoints();
				sc=false;
				for (int p_idx : point_idxs) {
						if(sc)
							fw.write(";");
						fw.write( ""+p_idx ) ;
						sc=true;
				}
				
				fw.write( "\r\n");
				fw.write( "uvs="+face.getUVsAsString()+"\r\n");
				fw.write( "}\r\n");
			}
			fw.write( "}\r\n");
		}
		fw.write( "}\r\n");
	}

	private void writeNodes(FileWriter fw) throws IOException {
		fw.write( "Nodes{\r\n");
		for (EmapNode emapNode : nodes) {
			fw.write(emapNode.getNodeText());
		}
		fw.write( "}\r\n");
	}

	public void setMaterials(List<String> allUsedTextures) {
		this.materials = allUsedTextures;
	}

	public int addMaterial(String material) {
		if(materials==null)
			materials = new ArrayList<String>();
		materials.add(material);
		return materials.size();
				
	}

	// Edges are optional but are useful in the level editor to edit brushes using their edges.
/*
 	private void writeEdges(FileWriter fw) throws IOException {
		fw.write( "edges=");
		//		1,0;3,2;0,2;1,3;4,5;1,4;3,5;6,7;4,6;5,7;8,9;6,8;7,9;10,11;8,10;9,11;12,13;10,12;11,13;14,15;12,14;13,15\r\n");
		int idx=0;
		for (List<Point> face : faces) {
			for (int i = 0; i < face.size()-1; i++) {
				if( (idx+i)>0)
					fw.write(";") ;
				fw.write( (idx+i) + "," + (idx+i+1));
			}
			fw.write( ";" + (idx+face.size()-1) + "," + idx);
			idx+=face.size();
		}
		fw.write( "\r\n");
	}
*/

}
