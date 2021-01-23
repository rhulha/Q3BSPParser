package net.raysforge.bsp.q3.model;

public enum Q3LumpTypes {
	Entities,
	Shaders,
	Planes,
	Nodes,
	Leafs,
	LeafSurfaces,
	LeafBrushes,
	Models,
	Brushes,
	BrushSides,
	DrawVerts,
	DrawIndexes,
	Fogs,
	Surfaces,
	Lightmaps,
	LightGrid,
	Visibility;
	public static final int size = Q3LumpTypes.values().length;
}