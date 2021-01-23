package net.raysforge.bsp.q3;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;

public class Model {

	public float[] mins; // float bounding box min coord.
	public float[] maxs; // float bounding box max coord.

	public int firstSurface; // First face for model.
	public int numSurfaces; // Number of faces for model.
	public int firstBrush; // First brush for model.
	public int numBrushes; // Number of brushes for model.

	public final static int size = 40;

	

	public Model(float[] mins, float[] maxs, int firstSurface, int numSurfaces, int firstBrush, int numBrushes) {
		super();
		this.mins = mins;
		this.maxs = maxs;
		this.firstSurface = firstSurface;
		this.numSurfaces = numSurfaces;
		this.firstBrush = firstBrush;
		this.numBrushes = numBrushes;
	}



	public Model(BinaryReader br) throws IOException {
		this.mins = br.readFloat(3);
		this.maxs = br.readFloat(3);
		this.firstSurface = br.readInt();
		this.numSurfaces = br.readInt();
		this.firstBrush = br.readInt();
		this.numBrushes = br.readInt();
	}

}
