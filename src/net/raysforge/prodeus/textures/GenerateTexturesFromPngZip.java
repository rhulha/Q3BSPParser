package net.raysforge.prodeus.textures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.raysforge.commons.FileUtils;

public class GenerateTexturesFromPngZip {
	
	private String path2PngZipFile;
	private String targetDir;
	
	String matFile = "shader=environment\r\n"
			+ "surface=Default\r\n"  // Stone, Metal, Water, Lava, NoClip, Glass, Waste
			//+ "hotspotAtlas=Wall\r\n"
			+ "parmTex=_MainTex=%NAME%\r\n";
			//+ "parmTex=_BumpTex=Wall01_Wall_normal.png\r\n"
			//+ "parmTex=_PropertyTex=Wall01_Wall_property.png\r\n";

	public GenerateTexturesFromPngZip(String path2PngZipFile, String targetDir) {
		this.path2PngZipFile = path2PngZipFile;
		this.targetDir = ensureEndingSlash(targetDir);
		
	}
	
	private String ensureEndingSlash(String dir) {
		return dir.endsWith("/") || dir.endsWith("\\") ? dir : dir.concat(File.separator);
	}

	public void generate() throws IOException {
		ZipFile zip = new ZipFile(path2PngZipFile); 
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while(entries.hasMoreElements())
		{
			ZipEntry ze = entries.nextElement();
			//ze.get
			if( ze.getName().startsWith("textures/")) {
				String name = ze.getName().substring("textures/".length());
				if( name.length() < 1) {
					continue;
				}
				if( name.endsWith("/")) {
					boolean mkdir = new File(targetDir + name).mkdir();
					System.out.println("mkdir: " + targetDir + name + ": " + mkdir);
					continue;
				}
				//if(name.endsWith("png"))
					System.out.println("writing file: " + targetDir + name);
			
				InputStream is = zip.getInputStream(ze);
				FileUtils.writeFile(targetDir + name, is);
				is.close();
				
				FileWriter fw = new FileWriter(targetDir+name.replace(".png", ".mat"));
				fw.write(matFile.replace("%NAME%", name.substring(name.lastIndexOf('/')+1)));
				fw.close();
			}
		}
		zip.close();
	}
	
	


}
