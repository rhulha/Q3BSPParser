package net.raysforge.bsp.q3.model;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;

public class Visibility  {
	
	/*
		int n_vecs	Number of vectors.
		int sz_vecs	Size of each vector, in bytes.
		ubyte[n_vecs * sz_vecs] vecs	Visibility data. One bit per cluster per vector.
	*/
	
	public int n_vecs;
	public int sz_vecs;
	public byte[] vecs;
	
	public Visibility(BinaryReader br) throws IOException {
		this.n_vecs = br.readInt();
		this.sz_vecs = br.readInt();
		this.vecs = br.readBytes(n_vecs*sz_vecs);
	}
}