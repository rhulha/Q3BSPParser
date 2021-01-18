package net.raysforge.q3.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.raysforge.generic.GenericParser;

public class MapParser extends GenericParser {

	public MapParser(String file) throws FileNotFoundException {
		super(initStreamTokenizer(file));
	}
	
	private static StreamTokenizer initStreamTokenizer(String file) throws FileNotFoundException {
		
		try {
			List<String> lines = Files.readAllLines(Path.of(file));
			FileWriter fw = new FileWriter(file);
			for (String line : lines) {
				if( line.startsWith("//"))
					continue;
				fw.write(line+"\r\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		StreamTokenizer st = new StreamTokenizer(br);
		st.eolIsSignificant(true);
		st.ordinaryChar('/');
		st.wordChars('_', '_');
		st.slashSlashComments(true);
		return st;
	}
	
	public double getNextDouble() throws IOException {
		assertNextToken( StreamTokenizer.TT_NUMBER);
		return st.nval;
	}

	public int getNextInt() throws IOException {
		assertNextToken( StreamTokenizer.TT_NUMBER);
		int i = (int)st.nval;
		if( i != st.nval)
			throwErrorAtCurrentLine("i != st.nval");
		return (int)st.nval;
	}

	public Point getNextPoint() throws IOException {
		return Point.getPointSwapZY(getNextInt(),getNextInt(),getNextInt());
	}

	public String getNextStringWithSlashes() throws IOException {
		StringBuffer sb = new StringBuffer();
		while( true) {
			int type = peekNextToken();
			if( type == '/') {
				sb.append((char) st.nextToken());
			} else if( type == '+') { // some textures start with a +
				sb.append((char) st.nextToken());
			//} else if( type == StreamTokenizer.TT_NUMBER) { // some textures start with a number
				//st.nextToken();
				//sb.append(st.nval);
			} else if( type == StreamTokenizer.TT_WORD) {
				st.nextToken();
				sb.append(st.sval);
			} else {
				break;
			}
		}
		return sb.toString();
	}

	public Plane parsePlane() throws IOException {
		assertNextToken('(');
		Point p1 = getNextPoint();
		assertNextToken(')');

		assertNextToken('(');
		Point p2 = getNextPoint();
		assertNextToken(')');

		assertNextToken('(');
		Point p3 = getNextPoint();
		assertNextToken(')');
		
		st.wordChars('0', '9');
		st.wordChars('/', '/');
		st.wordChars('+', '+');
		//String texture = getNextStringWithSlashes();
		String texture = getNextString();
		st.parseNumbers();
		//System.out.println(texture);
		
		while( true) {
			if( peekNextToken() == StreamTokenizer.TT_EOL)
				break;
			getNextDouble(); // swallow rest numbers for now
		}
		swallowEOLs();
		
		return new Plane( p1, p2, p3, texture);
		
	}

	public Brush parseBrush() throws IOException {
		List<Plane> planes = new ArrayList<Plane>();
		
		swallowEOLs();
		
		while( peekNextToken() == '(') {
			planes.add(parsePlane());
		}
		
		return new Brush(planes);
	}
	
	public List<Brush> getAllBrushes() throws IOException {
		List<Brush> brushList = new ArrayList<Brush>();
		
		assertNextToken('{');
		swallowUntil('{');
		st.pushBack();
		
		while( peekNextToken() == '{') {
			assertNextToken('{');
			
			swallowEOLs();
			
			if( peekNextToken() == StreamTokenizer.TT_WORD) {
				// this is a patchDef2 part probably
				System.out.println("patchDef2");
				swallowUntil('}');
				swallowUntil('}'); // two closing braces for pathDef2
				continue;
			}
			
			Brush brush = parseBrush();
			brushList.add(brush);
		
			swallowEOLs();
			assertNextToken('}');
			swallowEOLs();
		}
		
		assertNextToken('}'); // done reading world spawn block
		
		

		return brushList;
		
	}

}
