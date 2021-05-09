package net.raysforge.gltf;

import java.util.ArrayList;
import java.util.HashMap;

import net.raysforge.gltf.model.Accessor;
import net.raysforge.gltf.model.Buffer;
import net.raysforge.gltf.model.BufferView;
import net.raysforge.gltf.model.Image;
import net.raysforge.gltf.model.Material;
import net.raysforge.gltf.model.Mesh;
import net.raysforge.gltf.model.Texture;

public class GlTFInternalCache {
	
	HashMap<Class<?>, ArrayList<?>> map = new HashMap<Class<?>, ArrayList<?>>();
	
	public GlTFInternalCache() {
		map.put(Buffer.class, new ArrayList<Buffer>());
		map.put(BufferView.class, new ArrayList<BufferView>());
		map.put(Accessor.class, new ArrayList<Accessor>());
		map.put(Mesh.class, new ArrayList<Mesh>());
		map.put(Material.class, new ArrayList<Material>());
		map.put(Texture.class, new ArrayList<Texture>());
		map.put(Image.class, new ArrayList<Image>());
	}

	@SuppressWarnings("unchecked")
	public int add(Object instance) {
		ArrayList<Object> list = (ArrayList<Object>)map.get(instance.getClass());
		if (list.contains(instance))
			return list.indexOf(instance);
		list.add(instance);
		return list.size() - 1;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, int i) {
		ArrayList<Object> list = (ArrayList<Object>)map.get(clazz);
		return (T) list.get(i);
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object instance) {
		ArrayList<Object> list = (ArrayList<Object>)map.get(instance.getClass());
		return list.contains(instance);
	}

	@SuppressWarnings("unchecked")
	public int getIndex(Object instance) {
		ArrayList<Object> list = (ArrayList<Object>)map.get(instance.getClass());
		return list.indexOf(instance);
	}

	@SuppressWarnings("unchecked")
	public int getSize(Class<?> clazz) {
		ArrayList<Object> list = (ArrayList<Object>)map.get(clazz);
		return list.size();
	}

}
