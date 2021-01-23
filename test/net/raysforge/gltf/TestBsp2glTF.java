package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

public class TestBsp2glTF {

	public static void main(String[] args) throws IOException {
		
		Bsp2glTF.convert(new File("q3dm17.bsp"), new File("D:\\GameDev\\Tests\\gltf\\"));

	}

}
