package net.raysforge.gltf;

public class BufferView {
	
	// a buffer view cannot be used for mixed data (i.e., it cannot contain both indices and vertex attributes).
	// https://github.com/KhronosGroup/glTF/issues/1440
	
	Buffer buffer;
	int byteLength=-1;
	int byteOffset=-1;
	int byteStride=-1;
	int target=-1;
	
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
