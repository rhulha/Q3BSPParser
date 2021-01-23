package net.raysforge.bsp.q3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.q3.model.Vertex;

public class Q3BSP {
	
	public int[][] brushes;
	public int[][] brushSides;
	public List<Integer> drawIndexes;
	public List<Vertex> drawVerts;
	public Map<String, List<Map<String, String>>> entities;

	public Q3BSP(File bspFile) throws IOException {
		Q3BSPReader reader = new Q3BSPReader(bspFile);
		brushes = reader.getBrushes();
		brushSides = reader.getBrushSides();
		drawIndexes = reader.getDrawIndexes();
		drawVerts = reader.getDrawVerts();
		entities = reader.getEntities();
		
	}

}
