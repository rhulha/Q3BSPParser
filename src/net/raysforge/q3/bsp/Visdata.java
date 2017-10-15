package net.raysforge.q3.bsp;

import java.io.IOException;

public class Visdata  {
	
	/*
		int n_vecs	Number of vectors.
		int sz_vecs	Size of each vector, in bytes.
		ubyte[n_vecs * sz_vecs] vecs	Visibility data. One bit per cluster per vector.
	*/
	
	public int n_vecs;
	public int sz_vecs;
	public byte[] vecs;
	
	public Visdata(BinaryReader br) throws IOException {
		this.n_vecs = br.readInt();
		this.sz_vecs = br.readInt();
		this.vecs = br.readBytes(n_vecs*sz_vecs);
	}
}