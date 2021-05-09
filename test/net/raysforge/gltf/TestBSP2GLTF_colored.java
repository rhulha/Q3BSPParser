package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.raysforge.bsp.q3.SkipItem;
import net.raysforge.bsp.q3.SpecialTexturesList;

public class TestBSP2GLTF_colored {

	private static final String outDir = "C:\\BackupYes\\Coding\\Projects\\Private\\Games\\Instagib2\\web\\models";

	public static void main(String[] args) throws IOException {
		
		
		// write black surfaces
		BSP2GLTF_colored bsp2glTF = new BSP2GLTF_colored(new File("q3dm17.bsp"), new File(outDir), true);
		
		Map<String, SkipItem> skipList = SpecialTexturesList.getSkipList();
		skipList.putAll(SpecialTexturesList.getQ3DM17BorderHighlightList());
		bsp2glTF.addMeshFromBSP(skipList, false);
		
		Map<String, SkipItem> includeList = SpecialTexturesList.getQ3DM17BorderHighlightList();
		bsp2glTF.addMeshFromBSP(includeList, true);
		
		// The next mesh is only for collision detection. So we remove the cable patches and some other stuff not needed, to make octreeing faster.
		skipList = SpecialTexturesList.getSkipList();

		// the thin round cables, they can never be reached afaik.
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("textures/base_support/cable", true, -1, false));
		// the things above and below the thin round cables.
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("textures/base_wall/metalblack03", true, -1, false));

		// TODO: see last true
		// SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/", false, -1, true));
		
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/energy", false, -1, false));
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/teleporter", false, -1, false));
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/teleporter_edge", false, -1, false));
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/pad", false, -1, false));
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/transparency", false, -1, false));
    	SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("models/mapobjects/teleporter/widget", false, -1, false));
    	
    	// the two 8 sided jump pads, they hinder the player a bit. So we remove them from the collision shape.
    	SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("textures/base_trim/border11light", true, -1, false));
		// if 8 vertices only ? well, since these are jump pads, we never need to collide with these anyways.
		SpecialTexturesList.addSkipItemEntry(skipList, new SkipItem("textures/sfx/diamond2cjumppad", true, 8, false));
		bsp2glTF.addMeshFromBSP(skipList, false);
		
		bsp2glTF.writeGLTF();
		
		
		

	}

}
