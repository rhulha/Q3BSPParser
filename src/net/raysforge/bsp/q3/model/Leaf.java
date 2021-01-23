package net.raysforge.bsp.q3.model;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;

public class Leaf {

	public int cluster; // Visdata cluster index.
	public int area; // Areaportal area.
	public int[] mins; // Integer bounding box min coord.
	public int[] maxs; // Integer bounding box max coord.

	public int firstLeafSurface; // First leafface for leaf.
	public int numLeafSurfaces; // Number of leaffaces for leaf.
	
	public int firstLeafBrush; // First leafbrush for leaf.
	public int numLeafBrushes; // Number of leafbrushes for leaf.
	
	public final static int size = 48;
	
	public Leaf(int cluster, int area, int[] mins, int[] maxs, int firstLeafSurface, int numLeafSurfaces, int firstLeafBrush, int numLeafBrushes) {
		this.cluster = cluster;
		this.area = area;
		this.mins = mins; // 3
		this.maxs = maxs; // 3
		this.firstLeafSurface = firstLeafSurface;
		this.numLeafSurfaces = numLeafSurfaces;
		this.firstLeafBrush = firstLeafBrush;
		this.numLeafBrushes = numLeafBrushes;
	}

	public Leaf(BinaryReader br) throws IOException {
		this.cluster = br.readInt();
		this.area = br.readInt();
		this.mins = br.readInt(3); // 3
		this.maxs = br.readInt(3); // 3
		this.firstLeafSurface = br.readInt();
		this.numLeafSurfaces = br.readInt();
		this.firstLeafBrush = br.readInt();
		this.numLeafBrushes = br.readInt();
	}
	

}
