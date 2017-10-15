package net.raysforge.q3.bsp;

public class Texture  {
	public String name;
	public int flags;
	public int contents;

	public Texture(String name, int flags, int contents) {
		this.name = name;
		this.flags = flags;
		this.contents = contents;
	}
}