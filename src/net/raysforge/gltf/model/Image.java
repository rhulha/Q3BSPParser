package net.raysforge.gltf.model;

public class Image {
	
	public String name;
	public BufferView bufferView;
	// mimeType : image/jpeg
	public String uri;
	
	public Image(String uri) {
		this.uri = uri;
	}

	public Image(BufferView bufferView) {
		this.bufferView = bufferView;
	}

}
