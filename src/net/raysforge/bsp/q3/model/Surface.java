package net.raysforge.bsp.q3.model;

import java.io.IOException;

import net.raysforge.generic.BinaryReader;

public class Surface {

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
	
	public int shaderNum;
	public int fogNum;//		Index into lump 12 (Effects), or -1.
	public int surfaceType;//			Face type. 1=polygon, 2=patch, 3=mesh, 4=billboard
	public int firstVert;//		Index of first vertex.
	public int numVerts;//	Number of vertices that form a polygon ( not yet triangulated )
	public int firstIndex;//		Index of first meshvert.
	public int numIndexes;//	Number of meshverts that form a triangulated polygon (mesh?)
	public int lightmapNum;//		Lightmap index.
	public int[] lm_start;//	Corner of this face's lightmap image in lightmap.
	public int[] lm_size;//		Size of this face's lightmap image in lightmap.
	public float[] lightmapOrigin;//	World space origin of lightmap.
	public float[] lightmapVecs;//	World space lightmap s and t unit vectors. (and maybe normal)
	public int[] patch_size;//	Patch dimensions.


	public static void main(String[] args) {
		// get size
		System.out.println("tetvnmnis_s_o__v_____n__s_".length()*4);
	}

	public final static int size = 104;

	public Surface(int shaderNum, int fogNum, int surfaceType, int firstVert, int numVerts, int firstIndex, int numIndexes, int lightmapNum, int[] lm_start, int[] lm_size, float[] lightmapOrigin,
			float[] lightmapVecs, int[] patch_size) {
		super();
		this.shaderNum = shaderNum;
		this.fogNum = fogNum;
		this.surfaceType = surfaceType;
		this.firstVert = firstVert;
		this.numVerts = numVerts;
		this.firstIndex = firstIndex;
		this.numIndexes = numIndexes;
		this.lightmapNum = lightmapNum;
		this.lm_start = lm_start;
		this.lm_size = lm_size;
		this.lightmapOrigin = lightmapOrigin;
		this.lightmapVecs = lightmapVecs;
		this.patch_size = patch_size;
	}

	public Surface(BinaryReader br) throws IOException {
		this.shaderNum = br.readInt();
		this.fogNum = br.readInt();
		this.surfaceType = br.readInt();
		this.firstVert = br.readInt();
		this.numVerts =br.readInt();
		this.firstIndex = br.readInt();
		this.numIndexes = br.readInt();
		this.lightmapNum = br.readInt();
		this.lm_start = br.readInt(2);
		this.lm_size = br.readInt(2);
		this.lightmapOrigin = br.readFloat(3);
		this.lightmapVecs = br.readFloat(9);
		this.patch_size = br.readInt(2);

	}
	
	@Override
	public String toString() {
		return "type:" + surfaceType + " tex:" + shaderNum + " verts:" + firstVert + ", " + numVerts + " mesh:" + firstIndex + ", " + numIndexes + " lm:" + lightmapNum + " patch:" + patch_size[0] + " " + patch_size[1];
	}

	public String toString2() {
		return surfaceType + " " + shaderNum + " " + firstVert + " # " + numVerts + " " + firstIndex + " # " + numIndexes + " " + lightmapNum + " " + patch_size[0] + " " + patch_size[1];
	}

}
