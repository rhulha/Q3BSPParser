package net.raysforge.prodeus;

import java.io.IOException;

import net.raysforge.prodeus.textures.GenerateTexturesFromPngZip;

public class TestGenerateTexturesFromPngZip {
	public static void main(String[] args) throws IOException {
		

		String path2PngZipFile = "D:\\GameDev\\LevelEditors\\Quake2\\quake2-neural-upscale-textures-2.0.1.zip";

		GenerateTexturesFromPngZip gen = new GenerateTexturesFromPngZip(path2PngZipFile, ProdeusFolders.Materials);

		gen.generate();
		
		

	}

}
