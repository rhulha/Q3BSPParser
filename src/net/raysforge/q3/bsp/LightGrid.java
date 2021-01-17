package net.raysforge.q3.bsp;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;

public class LightGrid  {
	
	/*
	  ubyte[3] ambient	Ambient color component. RGB.
	  ubyte[3] directional	Directional color component. RGB.
	  ubyte[2] dir	Direction to light. 0=phi, 1=theta.
	*/
	
	public byte[] ambient;
	public byte[] directional;
	public byte[] dir;
	
	public final static int size = 8;

	public LightGrid(byte[] ambient, byte[] directional, byte[] dir) {
		super();
		this.ambient = ambient;
		this.directional = directional;
		this.dir = dir;
	}

	public LightGrid(BinaryReader br) throws IOException {
		this.ambient = br.readBytes(3);
		this.directional = br.readBytes(3);
		this.dir = br.readBytes(2);
	}
}