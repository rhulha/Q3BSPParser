package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
	
	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		
		Map<String, List<Map<String, String>>> entities = bsp.getEntities();

		for (String key : entities.keySet()) {
			System.out.println(key);
			if( !key.equals("light"))
				System.out.println(entities.get(key));
		}

		//writeVerts(bsp);
		//writeIndices(bsp);
		//Face[] faces = bsp.getFaces();
		
		//bsp.getNodes();
		
	}
}
