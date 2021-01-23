package net.raysforge.bsp.q3;

import java.io.FileOutputStream;
import java.io.IOException;

import net.raysforge.bsp.q3.model.Q3LumpTypes;

public class LumpWriter {
	
	
	public static void writeBSPPartsToFiles(Q3BSPReader q3BspReader, String outputPath) throws IOException {

		writeFile( outputPath + "q3dm17.brushes", q3BspReader.getLump(Q3LumpTypes.Brushes.ordinal()));
		writeFile( outputPath + "q3dm17.brushsides", q3BspReader.getLump(Q3LumpTypes.BrushSides.ordinal()));
		writeFile( outputPath + "q3dm17.leafbrushes", q3BspReader.getLump(Q3LumpTypes.LeafBrushes.ordinal()));
		writeFile( outputPath + "q3dm17.leafs", q3BspReader.getLump(Q3LumpTypes.Leafs.ordinal()));
		writeFile( outputPath + "q3dm17.nodes", q3BspReader.getLump(Q3LumpTypes.Nodes.ordinal()));
		writeFile( outputPath + "q3dm17.planes", q3BspReader.getLump(Q3LumpTypes.Planes.ordinal()));
	}
	
	public static void writeFile( String fn, byte[] bytes) {
		try ( FileOutputStream fos = new FileOutputStream( fn)) {
			fos.write( bytes );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
