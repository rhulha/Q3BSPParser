package net.raysforge.q3.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

public class MapParser implements AutoCloseable {

	private BufferedReader br;
	private StreamTokenizer st;

	public MapParser(String file) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(file));
		st = new StreamTokenizer(br);
		st.eolIsSignificant(true);
		st.ordinaryChar('/');
		st.wordChars('_', '_');
		st.slashSlashComments(true);
	}
	
	public void assertNextToken( int t) throws IOException {
		int nt = st.nextToken();
		if( nt != t)
			throwErrorAtCurrentLine("token is: " + ((char)nt) + " " + nt + ", expected was: " + ((char)t) + " " + t);
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

	public String getNextString() throws IOException {
		assertNextToken( StreamTokenizer.TT_WORD);
		return st.sval;
	}

	public String getNextStringWithSlashes() throws IOException {
		StringBuffer sb = new StringBuffer();
		while( true) {
			int p = peekNextToken();
			if( p == '/')
				sb.append((char) st.nextToken());
			else if( p == StreamTokenizer.TT_WORD) {
				st.nextToken();
				sb.append(st.sval);
			} else {
				break;
			}
		}
		return sb.toString();
	}

	@Override
	public void close() throws IOException {
		br.close();
	}

	public int peekNextToken() throws IOException {
		int t = st.nextToken();
		st.pushBack();
		return t;
	}

	public void swallowEOLs() throws IOException {
		while( peekNextToken() == StreamTokenizer.TT_EOL)
			st.nextToken();
		
	}

	public int getNextToken() throws IOException {
		return st.nextToken();
		
	}

	public int getCurrentLine() {
		return st.lineno();
	}

	public void swallowUntil(char c) throws IOException {
		while( true) {
			int t = getNextToken();
			if( t == StreamTokenizer.TT_EOF) {
				throwErrorAtCurrentLine("premature EOF");
			}
			if( t == c)
				break;
		}
	}
	
	public void throwErrorAtCurrentLine(String msg) throws IOException {
		throw new IOException(msg+ ", line nr: " + st.lineno());
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
		
		String texture = getNextStringWithSlashes();
		
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
