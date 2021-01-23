package net.raysforge.gltf.model;

public class Attribute {

	public final String type;
	public final Accessor accessor;

	public Attribute(String type, Accessor accessor) {
		this.type = type;
		this.accessor = accessor;
	}
	

}
