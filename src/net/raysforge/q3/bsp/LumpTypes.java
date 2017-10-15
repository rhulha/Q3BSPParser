package net.raysforge.q3.bsp;

public enum LumpTypes {
	Entities,
	Textures,
	Planes,
	Nodes,
	Leafs,
	Leaffaces,
	Leafbrushes,
	Models,
	Brushes,
	Brushsides,
	Vertexes,
	Meshverts,
	Effects,
	Faces,
	Lightmaps,
	Lightvols,
	Visdata;
	public static final int size = LumpTypes.values().length;
}