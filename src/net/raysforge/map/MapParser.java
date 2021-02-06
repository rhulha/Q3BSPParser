package net.raysforge.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.StringWriter;
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
		parseMap(false);
	}

	public MapParser(String file, boolean includeTriggerBrushes) throws IOException {
		super(initStreamTokenizer(file));
		parseMap(includeTriggerBrushes);
	}
	
	private static StreamTokenizer initStreamTokenizer(String file) throws IOException {
		List<String> lines = Files.readAllLines(Path.of(file));
		StringWriter sw = new StringWriter(); 
		for (String line : lines) {
			// skip comments
			if( line.startsWith("//"))
				continue;
			sw.write(line+"\r\n");
		}
		sw.close();
		BufferedReader br = new BufferedReader(new StringReader(sw.toString()));
		StreamTokenizer st = new StreamTokenizer(br);
		st.eolIsSignificant(true);
		st.ordinaryChar('/');
		st.wordChars('_', '_');
		st.slashSlashComments(true);
		return st;
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
	
	public void parseMap(boolean includeTriggerBrushes) throws IOException {
		assertNextToken('{');
		swallowUntil('{');
		st.pushBack();
		
		while( peekNextToken() == '{') {
			assertNextToken('{');
			
			swallowEOLs();
			
			if( peekNextToken() == StreamTokenizer.TT_WORD) {
				// this is a patchDef2 part probably, outside of a patchCapped
				//System.out.println("patchDef2");
				assertNextString("patchDef2");
				swallowUntil('}');
				swallowUntil('}'); // two closing braces for pathDef2
				swallowEOLs();
				continue;
			}
			
			Brush brush = parseBrush();
			brushList.add(brush);
		
			swallowEOLs();
			assertNextToken('}');
			swallowEOLs();
		}
		
		assertNextToken('}'); // done reading world spawn block
		
		System.out.println("Done parsing brushes. Now parsing entities. Line No.: " + st.lineno());
		
		// Parse Entities
		st.quoteChar('"');
		
		if( peekNextToken() == StreamTokenizer.TT_EOL)
			st.nextToken();
		
		// HERE STARTS THE BEGINNING OF A NEW ENTITY
		while (peekNextToken() == '{') {
			assertNextToken('{');
			swallowEOLs();

			HashMap<String, String> ent = new HashMap<>();

			// WHILE ENTITY NOT CLOSED OFF 
			while( peekNextToken() != '}')
			{
				if( peekNextToken() == '{' ) {
					// brushes inside entities, like in trigger_push
					assertNextToken('{');
					assertNextToken(10);
					Brush brush = parseBrush();
					if( includeTriggerBrushes ) {
						List<Plane> planes = brush.getPlanes();
						int i=0;
						for (Plane plane : planes) {
							ent.put("plane"+(i++), "( "+(int)plane.p1.x+ " " +(int)plane.p1.y+ " " +(int)plane.p1.z+ " ) ( "
									                   +(int)plane.p2.x+ " " +(int)plane.p2.y+ " " +(int)plane.p2.z+ " ) ( "
									                   +(int)plane.p3.x+ " " +(int)plane.p3.y+ " " +(int)plane.p3.z+ " )");
						}
						if(i>0) {
							ent.put("plane_count", ""+i);
						}
					}
					assertNextToken('}');
					assertNextToken(10);
					continue;
				}
					
				String key = getNextQuotetString();
				String value = getNextQuotetString();
				assertNextToken(10);
				if( key.equals("classname") && value.equals("func_group")) {
					// SKIP FUNC_GROUP CONTENTS FOR NOW 
					while( peekNextToken() != '}')
					{
						if(peekNextToken() == '"') {
							// catch these cases:
							// "classname" "func_group" is second
							// "type" "patchCapped" is first 
							getNextQuotetString();
							getNextQuotetString();
							assertNextToken(10);
						}
						assertNextToken('{');
						assertNextToken(10);
						if(peekNextToken() == StreamTokenizer.TT_WORD) {
							assertNextString("patchDef2");
							assertNextToken(10);
							assertNextToken('{');
							swallowUntil('}');
							swallowUntil('}');
							swallowEOLs();
						} else {
							swallowUntil('}');
							swallowEOLs();
							continue;
						}
					}
				}
				ent.put(key, value);
				
			}

			String cn = ent.get("classname");
			if( ! entities.containsKey(cn)) {
				entities.put(cn, new ArrayList<Map<String, String>>());
			}
			
			// System.out.println(cn);
			
			entities.get(cn).add(ent);

			swallowEOLs();
			assertNextToken('}');
			swallowEOLs();
		}
		entities.remove("func_group"); // ignore func_group for now.
	} // pareseMap()
	
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
