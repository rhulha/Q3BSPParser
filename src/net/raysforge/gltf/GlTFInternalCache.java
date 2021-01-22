package net.raysforge.gltf;

import java.util.ArrayList;

public class GlTFInternalCache {

	ArrayList<Buffer> buffers = new ArrayList<Buffer>();
	ArrayList<BufferView> bufferViews = new ArrayList<BufferView>();
	ArrayList<Accessor> accessors = new ArrayList<Accessor>();
	ArrayList<Mesh> meshes = new ArrayList<Mesh>();

	public int add(Buffer buffer) {
		if (buffers.contains(buffer))
			return buffers.indexOf(buffer);
		buffers.add(buffer);
		return buffers.size() - 1;
	}

	public int add(BufferView bufferView) {
		if (bufferViews.contains(bufferView))
			return bufferViews.indexOf(bufferView);
		bufferViews.add(bufferView);
		return bufferViews.size() - 1;
	}

	public boolean contains(Buffer buffer) {
		return buffers.contains(buffer);
	}

	public boolean contains(BufferView bufferView) {
		return bufferViews.contains(bufferView);
	}

	public int getIndex(Buffer buffer) {
		return buffers.indexOf(buffer);
	}

	public boolean contains(Accessor accessor) {
		return accessors.contains(accessor);
	}

	public int add(Accessor accessor) {
		if (accessors.contains(accessor))
			return accessors.indexOf(accessor);
		accessors.add(accessor);
		return accessors.size() - 1;
	}

	public int getIndex(BufferView bufferView) {
		return bufferViews.indexOf(bufferView);
	}

	public int addMesh(Mesh mesh) {
		if (meshes.contains(mesh))
			return meshes.indexOf(mesh);
		meshes.add(mesh);
		return meshes.size() - 1;
	}

	public int getIndex(Mesh mesh) {
		return meshes.indexOf(mesh);
	}

	public int getIndex(Accessor accessor) {
		return accessors.indexOf(accessor);
	}

}
