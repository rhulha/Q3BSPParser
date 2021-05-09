package net.raysforge.gltf.model;

public class Material {
	
	public String name;
	public boolean doubleSided=false;
	
	//emissiveTexture : null
	// normalTexture : 

	//SECTION  pbrMetallicRoughness
	// pbr_baseColorFactor : [F@bca024e
	
	public Texture pbr_baseColorTexture;
	public int pbr_baseColorTexture_texCoord;
	
	public float pbr_metallicFactor = 0.0f;
	public float pbr_roughnessFactor = 1.0f;

	// String pbr_metallicRoughnessTexture;

	public Material(Texture pbr_baseColorTexture) {
		this.pbr_baseColorTexture = pbr_baseColorTexture;
	}
	

}
