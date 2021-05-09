package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.raysforge.bsp.q3.SkipItem;
import net.raysforge.bsp.q3.SpecialTexturesList;

public class TestBSP2GLTF_pul1ctf_colored {

	private static final String outDir = "D:\\Action\\id\\openarena-0.8.8\\baseoa\\tex";

	public static void main(String[] args) throws IOException {
		
		// pul1ctf is a really nice, colorful bunker/castle map
		
		// The result of this code looks very broken in at least one GTLF viewer (Gestaltor)
		
		BSP2GLTF_colored bsp2glTF = new BSP2GLTF_colored(new File(outDir+"\\ctf","pul1ctf.bsp"), new File(outDir), true);
		
		Map<String, SkipItem> skipList = SpecialTexturesList.getSkipList();
		bsp2glTF.addMeshFromBSP(skipList, false);
		
		bsp2glTF.writeGLTF();
		
		
		

	}

}
