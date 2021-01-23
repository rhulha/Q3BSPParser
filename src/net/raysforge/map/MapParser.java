package net.raysforge.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.q2.model.Vertex;
import net.raysforge.generic.GenericParser;

public class MapParser extends GenericParser {

	public MapParser(String file) throws IOException {
		super(initStreamTokenizer(file));
		parseMap();
	}
	
	private static StreamTokenizer initStreamTokenizer(String file) throws FileNotFoundException {
		
		try {
			List<String> lines = Files.readAllLines(Path.of(file));
			boolean foundComments=false;
			for (String line : lines) {
				if( line.startsWith("//"))
				{
					foundComments=true;
					break;
				}
			
			}
			if( foundComments ) {
				FileWriter fw = new FileWriter(file);
				for (String line : lines) {
					if( line.startsWith("//"))
						continue;
					fw.write(line+"\r\n");
				}
				fw.close();
			}
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
		return Point.getPointSwapZY(getNextDouble(),getNextDouble(),getNextDouble());
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
		
		int xoff = (int) getNextDouble();
		int yoff = (int) getNextDouble();
		double angle = getNextDouble();
		double xscale = getNextDouble();
		double yscale = getNextDouble();
		
		while( true) {
			if( peekNextToken() == StreamTokenizer.TT_EOL)
				break;
			getNextDouble(); // swallow rest numbers for now
		}
		swallowEOLs();
		
		return new Plane( p1, p2, p3, texture, xoff, yoff, angle, xscale, yscale);
		
	}

	public Brush parseBrush() throws IOException {
		List<Plane> planes = new ArrayList<Plane>();
		
		swallowEOLs();
		
		while( peekNextToken() == '(') {
			planes.add(parsePlane());
		}
		
		return new Brush(planes);
	}
	
	List<Brush> brushList = new ArrayList<Brush>();
	Map<String, List<Map<String, String>>> entities = new HashMap<>();
	
	public void parseMap() throws IOException {
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
		
		// Parse Entities
		st.quoteChar('"');
		
		if( peekNextToken() == StreamTokenizer.TT_EOL)
			st.nextToken();
		
		while (peekNextToken() == '{') {
			assertNextToken('{');
			swallowEOLs();

			HashMap<String, String> ent = new HashMap<>();

			while( peekNextToken() != '}')
			{
				if( peekNextToken() == '{' ) {
					// skip brushes inside entities, like in trigger_once
					swallowUntil('}');
					swallowEOLs();
					continue;
				}
					
				String key = getNextQuotetString();
				String value = getNextQuotetString();
				ent.put(key, value);
				assertNextToken(10);
			}

			String cn = ent.get("classname");
			if( ! entities.containsKey(cn)) {
				entities.put(cn, new ArrayList<Map<String, String>>());
			}
			
			entities.get(cn).add(ent);

			swallowEOLs();
			assertNextToken('}');
			swallowEOLs();
		}
	}
	
	public String getNextQuotetString() throws IOException {
		assertNextToken('"');
		return st.sval;
	}

	public Map<String, List<Map<String, String>>> getEntities()	{
		return entities;
	}
	
	public List<Brush> getAllBrushes()  {
		return brushList;
	}

	public List<Map<String, String>> getEntities(String classname) {
		return entities.get(classname);
	}

	public String getEntityValue(String classname, int item, String key) {
		return entities.get(classname).get(item).get(key);
	}

	public Vertex getEntityOrigin(String classname, int item, boolean swapYZ) {
		return originString2Vertex(entities.get(classname).get(item).get("origin"), swapYZ);
	}

	public static Vertex originString2Vertex(String origin, boolean swapYZ) {
		String[] split = origin.split(" ");
		float x = Float.parseFloat(split[0]);
		float y = Float.parseFloat(split[1]);
		float z = Float.parseFloat(split[2]);
		if(swapYZ)
			return new Vertex(x,z,y);
		else
			return new Vertex(x,y,z);
	}

	public List<String> getAllUsedTextures() {
		
		List<String> texts = new ArrayList<String>();
		
		for (Brush brush : brushList) {
			List<Plane> planes = brush.getPlanes();
			for (Plane plane : planes) {
				if(!texts.contains(plane.getTexture()))
					texts.add(plane.getTexture());
			}
		}
		
		return texts;
		
	}

}
