package net.raysforge.gltf;

import java.io.File;
import java.io.IOException;

public class TestBSP2glTF {

	public static void main(String[] args) throws IOException {
		
		
		
		new BSP2glTF(new File("q3dm17.bsp"), new File("C:\\BackupNo\\Coding\\IDE\\eclipseWeb\\workspace\\Instagib2\\public\\models\\gltf")).convert();
		//new BSP2glTF(new File("D:\\GameDev\\Tests\\bsps\\q3dm6.bsp"), new File("D:\\GameDev\\Tests\\gltf\\")).convert();

	}

}
