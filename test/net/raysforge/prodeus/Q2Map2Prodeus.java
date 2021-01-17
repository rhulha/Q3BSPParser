package net.raysforge.prodeus;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import net.raysforge.q3.map.Brush;
import net.raysforge.q3.map.MapParser;
import net.raysforge.q3.map.Point;

public class Q2Map2Prodeus {
	
	public static void main(String[] args) throws URISyntaxException {
		
		//Q2Map2Prodeus.map2prodeus("D:\\GameDev\\Tests\\base1.map", "D:\\GameDev\\Tests\\base1.emap");
		Q2Map2Prodeus.map2prodeus("D:\\GameDev\\Tests\\test_q2.map", "D:\\GameDev\\Tests\\test_q2.emap");
	}

	public static void map2prodeus(String mapFile, String prodeusFile) throws URISyntaxException {
		try ( FileWriter fw = new FileWriter(prodeusFile)) {
			
			URL res = Q2Map2Prodeus.class.getResource("emap_start.txt");
			URL res2 = Q2Map2Prodeus.class.getResource("emap_end.txt");
			
			String emap_start = Files.readString( Path.of(res.toURI()), StandardCharsets.UTF_8);
			String emap_end = Files.readString(Path.of(res2.toURI()), StandardCharsets.UTF_8);
			
			fw.write(emap_start);

			MapParser mapParser = new MapParser(mapFile);
					
			List<Brush> brushList = mapParser.getAllBrushes();
			
			fw.write( "Brushes{\r\n");
			for (Brush brush : brushList) {
				fw.write( "Brush{\r\n");
				fw.write( "parent=-1\r\n");
				fw.write( "layer=-1\r\n");
				fw.write( "pos=0,0,0\r\n");
				fw.write( "points=");
				List<List<Point>> faces = brush.getPolygons();
				for (List<Point> face : faces) {
					for (int i = 0; i < face.size(); i++) {
						Point p = face.get(i);
						if( i>0)
							fw.write( ";") ;
						fw.write( p.getX() + "," + p.getY() + "," + p.getZ()) ;
					}
				}
				fw.write( "\r\n");
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
				idx=0;
				for (List<Point> face : faces) {
					fw.write( "Face{\r\n");
					fw.write( "surf={\r\n");
					fw.write( "localMapping=False\r\n");
					fw.write( "mappingType=0\r\n");
					fw.write( "material=0\r\n");
					fw.write( "color=0\r\n");
					fw.write( "colorEmissive=0\r\n");
					fw.write( "seed=843\r\n");
					fw.write( "halfRes=False\r\n");
					fw.write( "uvScaleBias=1,1,0,0\r\n");
					fw.write( "uvScroll=0,0\r\n");
					fw.write( "localOffset=0,0,0\r\n");
					fw.write( "worldOffset=0,0,0\r\n");
					fw.write( "}\r\n");
					fw.write( "points=");
					// 0;2;3;1
					for (int i = 0; i < face.size(); i++) {
						if( i>0)
							fw.write(";") ;
						fw.write( ""+(idx+i)) ;
					}
					idx+=face.size();
					
					fw.write( "\r\n");
					fw.write( "uvs=-0.4375,0;-0.4375,0.125;-0.3125,0.125;-0.3125,0\r\n");
				}
			}
			fw.write(emap_end);
			fw.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
