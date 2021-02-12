package net.raysforge.bsp.q3;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.raysforge.bsp.q3.model.Surface;

public class TestNumIndexes {

	public static void main1(String[] args) throws IOException {
		Q3BSP q3bsp = new Q3BSP(new File("q3dm17.bsp"));
		for (Surface surface : q3bsp.surfaces) {
			var sl = SpecialTexturesList.getSkipList();
			if( sl.containsKey( q3bsp.shaders[surface.shaderNum].shader))
				continue;
			//if(surface.numIndexes % 3 != 0 )
			System.out.println(surface.firstVert + " " + surface.firstIndex + " " + surface.numIndexes);
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		Set<Integer> dblChecker = new HashSet<Integer>();
		
		Q3BSP q3bsp = new Q3BSP(new File("q3dm17.bsp"));
		for (Surface surface : q3bsp.surfaces) {
			var sl = SpecialTexturesList.getSkipList();
			if( sl.containsKey( q3bsp.shaders[surface.shaderNum].shader))
				continue;
			
			for(int k = 0; k < surface.numIndexes; ++k) {
				int i = surface.firstVert + q3bsp.indexes.get(surface.firstIndex + k);
				if( dblChecker.contains(i)) {
					System.out.println("Alert!!!");
					System.out.println(surface.firstVert + " " + surface.firstIndex + " " + surface.numIndexes);
				} else {
					dblChecker.add(i);
				}
            }
		}

	}

}
