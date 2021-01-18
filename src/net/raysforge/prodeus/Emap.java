package net.raysforge.prodeus;

import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.raysforge.q2.bsp.Vertex;

public class Emap {

	
	private String emap_start_text;
	private String emap_end_text;
	public List<EmapBrush> brushList = new ArrayList<EmapBrush>();

	public Emap()  {
		try {
			URL res = Emap.class.getResource("emap_start.txt");
			URL res2 = Emap.class.getResource("emap_end.txt");
			emap_start_text = Files.readString( Path.of(res.toURI()), StandardCharsets.UTF_8);
			emap_end_text = Files.readString(Path.of(res2.toURI()), StandardCharsets.UTF_8);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	public void writeMap( String prodeusFile) {
		try ( FileWriter fw = new FileWriter(prodeusFile)) {
			fw.write(emap_start_text);
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
					fw.write( face.surf);
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
					if( face.getPoints().size() == 3 )
						fw.write( "uvs="+face.uvs3+"\r\n");
					else if( face.getPoints().size() == 4 )
						fw.write( "uvs="+face.uvs4+"\r\n");
					else if( face.getPoints().size() == 5 )
						fw.write( "uvs="+face.uvs5+"\r\n");
					else if( face.getPoints().size() == 6 )
						fw.write( "uvs="+face.uvs6+"\r\n");
					else if( face.getPoints().size() == 7 )
						fw.write( "uvs="+face.uvs7+"\r\n");
					else if( face.getPoints().size() == 8 )
						fw.write( "uvs="+face.uvs8+"\r\n");
					else if( face.getPoints().size() == 9 )
						fw.write( "uvs="+face.uvs9+"\r\n");
					else if( face.getPoints().size() == 10 )
						fw.write( "uvs="+face.uvs4+";"+face.uvs6+"\r\n");
					else if( face.getPoints().size() == 12 )
						fw.write( "uvs="+face.uvs5+";"+face.uvs7+"\r\n");
					else if( face.getPoints().size() == 13 )
						fw.write( "uvs="+face.uvs5+";"+face.uvs6+"\r\n");
					else if( face.getPoints().size() == 14 )
						fw.write( "uvs="+face.uvs9+";"+face.uvs5+"\r\n");
					else if( face.getPoints().size() == 15 )
						fw.write( "uvs="+face.uvs9+";"+face.uvs6+"\r\n");
					else 
						throw new RuntimeException("points != 3 or 4: " + face.getPoints().size());
					fw.write( "}\r\n");
				}
				fw.write( "}\r\n");
			}
			fw.write( "}\r\n");
			fw.write(emap_end_text);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


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


	public void addBrush(EmapBrush brush) {
		this.brushList.add(brush);
	}
}
