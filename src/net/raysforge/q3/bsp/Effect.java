package net.raysforge.q3.bsp;

public class Effect  {
	
	/*
	string[64] name	Effect shader.
	int brush	Brush that generated this effect.
	int unknown	Always 5, except in q3dm8, which has one effect with -1.
	*/
	
	public String name;
	public int brush;
	public int unknown;
	
	public final static int size = 72;

	public Effect(String name, int brush, int unknown) {
		this.name = name;
		this.brush = brush;
		this.unknown = unknown;
	}
}