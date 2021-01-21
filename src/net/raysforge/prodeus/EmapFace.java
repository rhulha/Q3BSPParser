package net.raysforge.prodeus;

import java.util.ArrayList;
import java.util.List;

import net.raysforge.q3.map.Point;

public class EmapFace {
	
	private int material_id;
	public List<Integer> points = new ArrayList<Integer>(); // 0;1;2;3
	public List<Point> uvs = new ArrayList<Point>(); // uv texture coordinates in the range of 0..1
	
	public EmapFace(int material_id) {
		this.material_id = material_id;
	}
	
	private String surf = "surf={\r\n"
			+ "localMapping=False\r\n"
			+ "mappingType=0\r\n"
			+ "material=%MATERIAL_ID%\r\n"
			+ "color=0\r\n"
			+ "colorEmissive=0\r\n"
			+ "seed=858\r\n"
			+ "halfRes=False\r\n"
			// + "uvScaleBias=1,1,0,0\r\n"
			+ "uvScaleBias=4.4375,4.03125,-4.75,0\r\n" // trying out these values with Quake 2 Base 1 // Yippee it Works!!!!!!!!
			+ "uvScroll=0,0\r\n"
			+ "localOffset=0,0,0\r\n"
			+ "worldOffset=0,0,0\r\n"
			+ "}\r\n";
	
	public String getSurfaceText()
	{
		return surf.replace("%MATERIAL_ID%", ""+material_id);
	}

	public String getUVsAsString() {
		String s = "";
		boolean semi=false;
		for (Point uv : uvs) {
			if(semi)
				s += ";";
			s += uv.x + "," + uv.y;
			semi=true;
		}
		return s;
	}
	
	public List<Integer> getPoints() {
		return points;
	}

	public void addUV(int u, int v) {
		uvs.add(new Point(u,v,0));
		
	}

}
