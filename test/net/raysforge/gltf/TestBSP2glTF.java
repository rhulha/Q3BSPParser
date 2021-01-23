package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

public class TestBSP2glTF {

	public static void main(String[] args) throws IOException {
		
		BSP2glTF.convert(new File("q3dm17.bsp"), new File("D:\\GameDev\\Tests\\gltf\\"));

	}

}
