package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.raysforge.bsp.q3.SpecialTexturesList;

public class TestBSP2glTF {

	private static final String outDir = "C:\\BackupYes\\Coding\\Projects\\Private\\Games\\Instagib2\\web\\models";

	public static void main(String[] args) throws IOException {
		
		List<String> skipList = SpecialTexturesList.getSkipList();
		
		skipList.addAll(SpecialTexturesList.getQ3DM17BorderHighlightList());
		
		// write black surfaces
		new BSP2glTF(new File("q3dm17.bsp"), new File(outDir)).convert(skipList, false);
		
		skipList.clear();
		skipList.addAll(SpecialTexturesList.getQ3DM17BorderHighlightList());
		
		new BSP2glTF(new File("q3dm17blue.bsp"), new File(outDir)).convert(skipList, true);

	}

}
