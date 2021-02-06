package net.raysforge.generic;

import java.io.IOException;
import java.io.StreamTokenizer;

public class GenericParser {
	
	protected StreamTokenizer st;
	
	public GenericParser(StreamTokenizer st) {
		if( st == null)
			throw new NullPointerException("StreamTokenizer is null.");
		this.st = st;
	}
	
	public void throwErrorAtCurrentLine(String msg) throws IOException {
		throw new IOException(msg+ ", line nr: " + st.lineno());
	}
	
	public void assertNextToken( int t) throws IOException {
		int nt = st.nextToken();
		if( nt != t)
			throwErrorAtCurrentLine("token is: " + ((char)nt) + " " + nt + ", expected was: " + ((char)t) + " " + t);
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

	public int getNextToken() throws IOException {
		return st.nextToken();
		
	}

	public String getNextString() throws IOException {
		assertNextToken( StreamTokenizer.TT_WORD);
		return st.sval;
	}

	public void assertNextString(String s) throws IOException {
		String ns = getNextString();
		if( !ns.equals(s))
			throwErrorAtCurrentLine("string is: " + ns + ", expected was: " + s );
	}

	public int getCurrentLine() {
		return st.lineno();
	}
	
	public int peekNextToken() throws IOException {
		int type = st.nextToken();
		st.pushBack();
		return type;
	}

	public void swallowEOLs() throws IOException {
		while( peekNextToken() == StreamTokenizer.TT_EOL)
			st.nextToken();
		
	}



}
