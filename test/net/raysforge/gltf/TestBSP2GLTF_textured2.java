package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TestBSP2GLTF_textured2 {

	private static final String outDir = "D:\\Action\\id\\openarena-0.8.8\\baseoa\\tex";
	
	public static HashMap<String, String> fileNameMap = new HashMap<String, String>(); 
	
	// Quake 3 BSP files do not store the extension of the used texture.
	// So here we create a map that stores the name and the name with the extension.
	public static void saveFilesInMap(File directory) {
		
		File[] listFiles = directory.listFiles();
		for (File file : listFiles) {
			if( file.isDirectory() ) {
				saveFilesInMap(file);
			} else {
				//System.out.println(file.getPath());
				String fileNameSansExt = file.getPath().replaceFirst("[.][^.]+$", "");
				String path=file.getPath();
				fileNameSansExt=fileNameSansExt.substring(outDir.length()+1).replace('\\', '/');
				path=path.substring(outDir.length()+1).replace('\\', '/');
				fileNameMap.put(fileNameSansExt, path);
				//System.out.println(fileNameSansExt + " -> " + path);
			}
		}
		
	}

	public static void main(String[] args) throws IOException {
		
		saveFilesInMap(new File(outDir));
	
		
		// cbctf1 is a very strong contender
		// https://openarena.fandom.com/wiki/Maps/cbctf1
		// very cool: mlctf1beta
		// oa_ctf2 is a space map but super simplistic
		// oa_ctf4ish is a space map but very small (it seems)
		
		// oa_reptctf11 looks like a small, flat bunker map, not good.
		
		// ps9ctf looks like a small, flat bunker map, but good
		// https://openarena.fandom.com/wiki/Maps/ps9ctf
			
		// ps37ctf looks like a big, boring castle map
		// ps37ctf2 looks like ps37ctf
		
		// pul1ctf is a really nice, colorful bunker/castle map
		// http://openarena.ws/board/index.php?topic=1836
		
		// Houston we have a problem: I am using the map files to get the jump pad hit boxes
		// I know they are in the BSP as well... But this would be new work to get to them...
		
		
		
		BSP2GLTF_textured bsp2glTF = new BSP2GLTF_textured(new File(outDir+"\\ctf","pul1ctf.bsp"), new File(outDir), fileNameMap);
		
		
		bsp2glTF.writeGLTF();
		
		
		

	}

}
