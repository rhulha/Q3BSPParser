package net.raysforge.gltf.model;

public class Texture {

	public String name;
	public Image image;
	public int sampler;
	
	public Texture(Image image) {
		this.image=image;
	}
	
}
