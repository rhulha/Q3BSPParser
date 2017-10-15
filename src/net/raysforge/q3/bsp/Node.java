package net.raysforge.q3.bsp;

import java.io.IOException;

public class Node {

	public int plane; // Plane index
	public int[] children; // Children indices. Negative numbers are leaf indices: -(leaf+1).
	public int[] mins; // Integer bounding box min coord.
	public int[] maxs; // Integer bounding box max coord.

	public final static int size = 36;

	public Node(int plane, int[] children, int[] mins, int[] maxs) {
		this.plane = plane;
		this.children = children; // 2
		this.mins = mins; // 3
		this.maxs = maxs; // 3
	}

	public Node(BinaryReader br) throws IOException {
		this.plane = br.readInt();
		this.children = br.readInt(2); // 2
		this.mins = br.readInt(3); // 3
		this.maxs = br.readInt(3); // 3
	}

}
