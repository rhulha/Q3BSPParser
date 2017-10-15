package net.raysforge.q3.bsp;

import java.io.IOException;

public class Face {

	/*
	int[2] lm_start	Corner of this face's lightmap image in lightmap.
	int[2] lm_size	Size of this face's lightmap image in lightmap.
	float[3] lm_origin	World space origin of lightmap.
	float[2][3] lm_vecs	World space lightmap s and t unit vectors.
	float[3] normal	Surface normal.
	int[2] size	Patch dimensions.
	*/
	
	public static int polygon=1;
	public static int patch=2;
	public static int mesh=3;
	public static int billboard=4;
	
	public int texture;//		Texture index.
	public int effect;//		Index into lump 12 (Effects), or -1.
	public int type;//			Face type. 1=polygon, 2=patch, 3=mesh, 4=billboard
	public int vertex;//		Index of first vertex.
	public int n_vertexes;//	Number of vertices.
	public int meshvert;//		Index of first meshvert.
	public int n_meshverts;//	Number of meshverts.
	public int lm_index;//		Lightmap index.
	public int[] lm_start;//	Corner of this face's lightmap image in lightmap.
	public int[] lm_size;//		Size of this face's lightmap image in lightmap.
	public float[] lm_origin;//	World space origin of lightmap.
	public float[] lm_vecs;//	World space lightmap s and t unit vectors.
	public float[] normal;//	Surface normal.
	public int[] patch_size;//	Patch dimensions.


	public static void main(String[] args) {
		// get size
		System.out.println("tetvnmnis_s_o__v_____n__s_".length()*4);
	}

	public final static int size = 104;

	public Face(int texture, int effect, int type, int vertex, int n_vertexes, int meshvert, int n_meshverts, int lm_index, int[] lm_start, int[] lm_size, float[] lm_origin,
			float[] lm_vecs, float[] normal, int[] patch_size) {
		super();
		this.texture = texture;
		this.effect = effect;
		this.type = type;
		this.vertex = vertex;
		this.n_vertexes = n_vertexes;
		this.meshvert = meshvert;
		this.n_meshverts = n_meshverts;
		this.lm_index = lm_index;
		this.lm_start = lm_start;
		this.lm_size = lm_size;
		this.lm_origin = lm_origin;
		this.lm_vecs = lm_vecs;
		this.normal = normal;
		this.patch_size = patch_size;
	}

	public Face(BinaryReader br) throws IOException {
		this.texture = br.readInt();
		this.effect = br.readInt();
		this.type = br.readInt();
		this.vertex = br.readInt();
		this.n_vertexes =br.readInt();
		this.meshvert = br.readInt();
		this.n_meshverts = br.readInt();
		this.lm_index = br.readInt();
		this.lm_start = br.readInt(2);
		this.lm_size = br.readInt(2);
		this.lm_origin = br.readFloat(3);
		this.lm_vecs = br.readFloat(6);
		this.normal = br.readFloat(3);
		this.patch_size = br.readInt(2);

	}
	
	@Override
	public String toString() {
		return type + " " + texture + " " + vertex + " # " + n_vertexes + " " + meshvert + " # " + n_meshverts + " " + lm_index + " " + patch_size[0] + " " + patch_size[1];
	}

}
