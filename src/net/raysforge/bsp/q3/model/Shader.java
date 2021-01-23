package net.raysforge.bsp.q3.model;

public class Shader  {
	public String shader;
	public int surfaceFlags;
	public int contentFlags;
	
	public final static int size = 72;

	public Shader(String shader, int surfaceFlags, int contentFlags) {
		this.shader = shader;
		this.surfaceFlags = surfaceFlags;
		this.contentFlags = contentFlags;
	}
}