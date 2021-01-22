package net.raysforge.gltf;

public class Accessor {
	
	BufferView bufferView;
	int componentType=-1;
	int count=-1;
	String type; // "VEC3", SCALAR, VEC2
	
	int byteOffset=-1;
	
	int max[] = new int[3];
	int min[] = new int[3];

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
