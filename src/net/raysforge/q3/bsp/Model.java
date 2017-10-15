package net.raysforge.q3.bsp;

import java.io.IOException;

public class Model {

	public float[] mins; // float bounding box min coord.
	public float[] maxs; // float bounding box max coord.

	public int face; // First face for model.
	public int n_faces; // Number of faces for model.
	public int brush; // First brush for model.
	public int n_brushes; // Number of brushes for model.

	public final static int size = 40;

	

	public Model(float[] mins, float[] maxs, int face, int n_faces, int brush, int n_brushes) {
		super();
		this.mins = mins;
		this.maxs = maxs;
		this.face = face;
		this.n_faces = n_faces;
		this.brush = brush;
		this.n_brushes = n_brushes;
	}



	public Model(BinaryReader br) throws IOException {
		this.mins = br.readFloat(3);
		this.maxs = br.readFloat(3);
		this.face = br.readInt();
		this.n_faces = br.readInt();
		this.brush = br.readInt();
		this.n_brushes = br.readInt();
	}

}
