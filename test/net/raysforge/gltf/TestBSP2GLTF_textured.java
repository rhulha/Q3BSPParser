package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TestBSP2GLTF_textured {

	private static final String outDir = "D:\\Action\\id\\Q3\\Quake3\\baseq3\\tex";
	
	public static HashMap<String, String> fileNameMap = new HashMap<String, String>(); 
	
	// Quake 3 BSP files do not store the extension of the used texture.
	// So here we create a map that stores the name and the name with the extension.
	public static void saveFilesInMap(File directory) {
		
		File[] listFiles = directory.listFiles();
		for (File file : listFiles) {
			if( file.isDirectory() ) {
				saveFilesInMap(file);
			} else {
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
	
		BSP2GLTF_textured bsp2glTF = new BSP2GLTF_textured(new File("q3dm17.bsp"), new File(outDir), fileNameMap);
		
		
		bsp2glTF.writeGLTF();
		
		
		

	}

}
