package net.raysforge.bsp.q3;

import java.io.IOException;

public class TestBSPSplitter {
	
	public static void test() throws IOException {
		Q3BSPReader bsp = new Q3BSPReader("q3dm17.bsp");
		
		byte[] lump = bsp.getLump(Q3LumpTypes.Lightmaps.ordinal());
		
		System.out.println(lump.length / (128*128*3));
		
		Shader[] textures = bsp.getShaders();
		for (Shader texture : textures) {
			System.out.println(texture.shader);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
		String basePath = "D:\\GameDev\\Tests\\gltf\\";
		BSPSplitter bspSplitter = new BSPSplitter("q3dm17.bsp", basePath);
		bspSplitter.writeBSPPartsToFiles();
		
	}

}
