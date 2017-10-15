package net.raysforge.q3.bsp;

import java.io.IOException;

public class Leaf {

	public int cluster; // Visdata cluster index.
	public int area; // Areaportal area.
	public int[] mins; // Integer bounding box min coord.
	public int[] maxs; // Integer bounding box max coord.
	public int leafface; // First leafface for leaf.

	public int n_leaffaces; // Number of leaffaces for leaf.
	public int leafbrush; // First leafbrush for leaf.
	public int n_leafbrushes; // Number of leafbrushes for leaf.
	
	public final static int size = 48;
	
	public Leaf(int cluster, int area, int[] mins, int[] maxs, int leafface, int n_leaffaces, int leafbrush, int n_leafbrushes) {
		this.cluster = cluster;
		this.area = area;
		this.mins = mins; // 3
		this.maxs = maxs; // 3
		this.leafface = leafface;
		this.n_leaffaces = n_leaffaces;
		this.leafbrush = leafbrush;
		this.n_leafbrushes = n_leafbrushes;
	}

	public Leaf(BinaryReader br) throws IOException {
		this.cluster = br.readInt();
		this.area = br.readInt();
		this.mins = br.readInt(3); // 3
		this.maxs = br.readInt(3); // 3
		this.leafface = br.readInt();
		this.n_leaffaces = br.readInt();
		this.leafbrush = br.readInt();
		this.n_leafbrushes = br.readInt();
	}
	

}
