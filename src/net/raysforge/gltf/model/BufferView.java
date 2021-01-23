package net.raysforge.gltf.model;

public class BufferView {
	
	// a buffer view cannot be used for mixed data (i.e., it cannot contain both indices and vertex attributes).
	// https://github.com/KhronosGroup/glTF/issues/1440
	
	public Buffer buffer;
	public int byteLength=-1;
	public int byteOffset=-1;
	public int byteStride=-1;
	public int target=-1;
	
	// GltfConstants.GL_ELEMENT_ARRAY_BUFFER
    // public static final int GL_ARRAY_BUFFER =  34962;
    // public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;

	public BufferView(Buffer buffer, int byteOffset, int byteLength) {
		this.buffer = buffer;
		this.byteLength = byteLength;
		this.byteOffset = byteOffset;
	}
	
	
	public BufferView(Buffer buffer, int byteOffset, int byteLength, int byteStride) {
		this.buffer = buffer;
		this.byteLength = byteLength;
		this.byteOffset = byteOffset;
		this.byteStride = byteStride;
	}
    

}
