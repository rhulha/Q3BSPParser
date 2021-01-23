package net.raysforge.bsp.q3;

import java.io.File;
import java.io.IOException;

import net.raysforge.bsp.q3.model.Q3LumpTypes;
import net.raysforge.bsp.q3.model.Shader;

public class TestQ3BSPReader {
	
	public static void test() throws IOException {
		Q3BSPReader bsp = new Q3BSPReader(new File("q3dm17.bsp"));
		
		byte[] lump = bsp.getLump(Q3LumpTypes.Lightmaps.ordinal());
		
		System.out.println(lump.length / (128*128*3));
		
		Shader[] textures = bsp.getShaders();
		for (Shader texture : textures) {
			System.out.println(texture.shader);
		}
	}
	
	

}
