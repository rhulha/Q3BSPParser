package net.raysforge.gltf.model;

public class Accessor {
	
	public BufferView bufferView;
	public int componentType=-1;
	public int count=-1;
	public String type; // "VEC3", SCALAR, VEC2
	
	public int byteOffset=-1;
	
	public int max[] = new int[3];
	public int min[] = new int[3];

	public Accessor(BufferView bufferView, int componentType, int byteOffset, int count, String type) {
		this.bufferView = bufferView;
		this.componentType = componentType;
		this.byteOffset=byteOffset;
		this.count = count;
		this.type = type;
	}
	
	
	public Accessor(BufferView bufferView, int componentType, int byteOffset, int count, String type, int[] max, int[] min) {
		this.bufferView = bufferView;
		this.componentType = componentType;
		this.byteOffset=byteOffset;
		this.count = count;
		this.type = type;
		this.max = max;
		this.min = min;
	}

	

}
