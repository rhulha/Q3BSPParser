package net.raysforge.q3;

import java.io.IOException;
import java.io.StreamTokenizer;

public class GenericParser {
	
	protected StreamTokenizer st;
	
	public GenericParser(StreamTokenizer st) {
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

	public int getCurrentLine() {
		return st.lineno();
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



}
