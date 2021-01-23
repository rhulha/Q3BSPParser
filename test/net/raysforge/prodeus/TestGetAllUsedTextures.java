package net.raysforge.prodeus;

import java.io.IOException;
import java.util.List;

import net.raysforge.map.MapParser;

public class TestGetAllUsedTextures {
	
	public static void main(String[] args) throws IOException {
		MapParser mapParser = new MapParser("D:\\GameDev\\Tests\\base1.map");
		List<String> allUsedTextures = mapParser.getAllUsedTextures();
		for (String string : allUsedTextures) {
			System.out.println(string);
		}
	}

}
