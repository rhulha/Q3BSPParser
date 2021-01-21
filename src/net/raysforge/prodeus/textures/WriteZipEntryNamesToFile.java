package net.raysforge.prodeus.textures;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WriteZipEntryNamesToFile {
	
	public static void main(String[] args) throws IOException {
		
		FileWriter fw = new FileWriter("D:\\GameDev\\Tests\\quake2-neural.txt"); 
		
		ZipFile zip = new ZipFile("D:\\GameDev\\Tests\\quake2-neural-upscale-textures-2.0.1.zip");
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while(entries.hasMoreElements())
		{
			ZipEntry ze = entries.nextElement();
			fw.write(ze.getName() + "\r\n");
		}
		
		
		zip.close();
		fw.close();
	}

}
