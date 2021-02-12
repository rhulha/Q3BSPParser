package net.raysforge.bsp.q3;

public class SkipItem {
	
	public String textureName;
	public boolean patchOnly;
	public int vertexCount;
	public boolean beginsWith;

	public SkipItem(String textureName, boolean patchOnly, int vertexCount, boolean beginsWith) {
		this.textureName = textureName;
		this.patchOnly = patchOnly;
		this.vertexCount = vertexCount;
		this.beginsWith = beginsWith;
		
	}

}
