package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.raysforge.bsp.q3.SpecialTexturesList;

public class TestBSP2glTF {

	private static final String outDir = "C:\\BackupYes\\Coding\\Projects\\Private\\Games\\Instagib2\\web\\models";

	public static void main(String[] args) throws IOException {
		
		
		// write black surfaces
		BSP2glTF bsp2glTF = new BSP2glTF(new File("q3dm17.bsp"), new File(outDir), true);
		
		List<String> skipList = SpecialTexturesList.getSkipList();
		skipList.addAll(SpecialTexturesList.getQ3DM17BorderHighlightList());
		bsp2glTF.addMeshFromBSP(skipList, false);
		
		List<String> includeList = SpecialTexturesList.getQ3DM17BorderHighlightList();
		bsp2glTF.addMeshFromBSP(includeList, true);
		
		// The next mesh is only for collision detection. So we remove the cable patches and some other stuff not needed, to make octreeing faster.
		skipList = SpecialTexturesList.getSkipList();

		skipList.add("textures/base_support/cable"); // the thin round cables, they can never be reached afaik.
		skipList.add("textures/base_wall/metalblack03"); // the things above and below the thin round cables.

		skipList.add("models/mapobjects/teleporter/energy");
		skipList.add("models/mapobjects/teleporter/teleporter");
		skipList.add("models/mapobjects/teleporter/teleporter_edge");
		skipList.add("models/mapobjects/teleporter/pad");
		skipList.add("models/mapobjects/teleporter/transparency");
    	skipList.add("models/mapobjects/teleporter/widget");
    	
    	// the two 8 sided jump pads, they hinder the player a bit. So we remove them from the collision shape.
    	skipList.add("textures/base_trim/border11light");
		skipList.add("textures/sfx/diamond2cjumppad"); // if 8 vertices only ? well, since these are jump pads, we never need to collide with these anyways.
		bsp2glTF.addMeshFromBSP(skipList, false);
		
		bsp2glTF.writeGLTF();
		
		
		

	}

}
