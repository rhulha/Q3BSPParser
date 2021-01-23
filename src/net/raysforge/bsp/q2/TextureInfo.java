package net.raysforge.q2.bsp;

import java.io.IOException;

import net.raysforge.generic.LittleEndianDataInputStream;
import net.raysforge.q3.map.Point;

public class TextureInfo {

	Point u_axis;
	float u_offset;

	Point v_axis;
	float v_offset;

	int flags;
	int value;

	String texture_name;

	int next_texinfo;
	
	public TextureInfo(LittleEndianDataInputStream ledis) throws IOException {
		this.u_axis =  new Point( ledis.readFloat(), ledis.readFloat(), ledis.readFloat());
		this.u_offset = ledis.readFloat();
		this.v_axis = new Point( ledis.readFloat(), ledis.readFloat(), ledis.readFloat());
		this.v_offset = ledis.readFloat();
		this.flags = ledis.readInt();
		this.value = ledis.readInt();
		byte[] name = ledis.readNBytes(32);
		int i = 0;
		for (i = 0; i < name.length; i++) {
			if(name[i] == 0)
				break;
		}
		this.texture_name = new String(name, 0, i);
		this.next_texinfo = ledis.readInt();
	}

	@Override
	public String toString() {
		return "TextureInfo [u_axis=" + u_axis + ", u_offset=" + u_offset + ", v_axis=" + v_axis + ", v_offset=" + v_offset + ", flags=" + flags + ", value=" + value + ", texture_name=" + texture_name + ", next_texinfo="
				+ next_texinfo + "]";
	}
	
	
	

}
