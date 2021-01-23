package net.raysforge.gltf.model;

public class Buffer {

	public String uri;
	public int byteLength=-1;
	
	public Buffer(String uri, int byteLength) {
		this.uri = uri;
		this.byteLength = byteLength;
	}

}
