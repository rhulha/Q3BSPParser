package net.raysforge.prodeus;

import java.io.IOException;

public class TestMap2Emap {
	
	
	public static void main(String[] args) throws IOException {
		
		Map2Emap.convert( "D:\\GameDev\\Tests\\base2b.map", ProdeusFolders.Maps + "base2.emap", ProdeusFolders.Materials);
		//Map2Emap.convert( "D:\\GameDev\\Tests\\box.map", ProdeusFolders.Maps + "box.emap");

	}

	
}
