package net.raysforge.q3.bsp;

public class Fog  {
	
	/*
	string[64] name	Effect shader.
	int brush	Brush that generated this effect.
	int unknown	Always 5, except in q3dm8, which has one effect with -1.
	*/
	
	public String shader;
	public int brushNum;
	public int visibleSide;
	
	public final static int size = 72;

	public Fog(String name, int brush, int visibleSide) {
		this.shader = name;
		this.brushNum = brush;
		this.visibleSide = visibleSide;
	}
}