package net.raysforge.q2.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q3MapRegexTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {

		File f = new File("test.ray");
		
		String pattern_q1 = "\\( (-?\\d+) (-?\\d+) (-?\\d+) \\) \\( (-?\\d+) (-?\\d+) (-?\\d+) \\) \\( (-?\\d+) (-?\\d+) (-?\\d+) \\) ([^ ]+) -?\\d+ -?\\d+ -?\\d+ -?\\d+\\.?\\d* -?\\d*\\.?\\d*";
		
		String pattern = "\\( (-?\\d+) (-?\\d+) (-?\\d+) \\) \\( (-?\\d+) (-?\\d+) (-?\\d+) \\) \\( (-?\\d+) (-?\\d+) (-?\\d+) \\) ([^ ]+) -?\\d+ -?\\d+ -?\\d+ -?\\d+\\.?\\d* -?\\d*\\.?\\d* -?\\d* -?\\d* -?\\d*";
		//System.out.println(pattern_q1);
		Pattern p = Pattern.compile(pattern_q1);

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {

			while( true) {
				String line = br.readLine();
				if( line == null)
					break;
				
				Matcher m = p.matcher(line);
				if( m.matches()) {
					System.out.print( m.group(1) + " " + m.group(2) + " " + m.group(3) + " " );
					System.out.print( m.group(4) + " " + m.group(5) + " " + m.group(6) + " " );
					System.out.print( m.group(7) + " " + m.group(8) + " " + m.group(9) + " " );
					System.out.println( m.group(10));
				} else if( line.startsWith("(") && line.endsWith(")")){
					// ignore patchDef2 for now
				} else if (line.length() < 3){
					// ignore empty lines
				} else if (line.startsWith("//")){
					// ignore comments
				} else if (line.startsWith("  ")){
					// ignore patchDef2 for now
				} else {
					System.err.println("unknown line: " + line);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static int p( String s ) {
		return Integer.parseInt(s);
	}

}
