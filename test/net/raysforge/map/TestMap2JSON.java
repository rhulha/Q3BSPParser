package net.raysforge.map;

import java.io.FileWriter;
import java.io.IOException;

public class TestMap2JSON {

	public static void main(String[] args) throws IOException {
		
		Map2JSON m2j = new Map2JSON("D:\\GameDev\\Tests\\maps\\q3dm17sample.map", false);
		String json = m2j.convert().toString();
		
		FileWriter fw = new FileWriter("D:\\GameDev\\Tests\\maps\\q3dm17sample.json"); 
		fw.write(json);
		fw.close();

	}

}
