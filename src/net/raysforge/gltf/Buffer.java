package net.raysforge.gltf;

public class Buffer {

	String uri;
	int byteLength=-1;
	
	public Buffer(String uri, int byteLength) {
		this.uri = uri;
		this.byteLength = byteLength;
	}

}
