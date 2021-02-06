package net.raysforge.map;

import java.io.FileWriter;
import java.io.IOException;

public class TestMap2JSON {

	public static void main(String[] args) throws IOException {
		
		boolean includeTriggerBrushes=true;
		boolean swapYZ=false;
		Map2JSON m2j = new Map2JSON("D:\\GameDev\\Tests\\maps\\q3dm17sample.map", includeTriggerBrushes, swapYZ);
		String json = m2j.convert().toString();
		
		FileWriter fw = new FileWriter("C:\\BackupYes\\Coding\\Projects\\Private\\Games\\Instagib2\\web\\models\\q3dm17.js"); 
		fw.write(json);
		fw.close();

	}

}
