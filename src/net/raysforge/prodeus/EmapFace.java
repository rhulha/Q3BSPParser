package net.raysforge.prodeus;

import java.util.ArrayList;
import java.util.List;

public class EmapFace {
	
	private int material_id;
	public String uvs;

	public EmapFace(int material_id, String uvs) {
		this.material_id = material_id;
		this.uvs = uvs;
	}
	
	private String surf = "surf={\r\n"
			+ "localMapping=False\r\n"
			+ "mappingType=0\r\n"
			+ "material=%MATERIAL_ID%\r\n"
			+ "color=0\r\n"
			+ "colorEmissive=0\r\n"
			+ "seed=858\r\n"
			+ "halfRes=False\r\n"
			+ "uvScaleBias=1,1,0,0\r\n"
			+ "uvScroll=0,0\r\n"
			+ "localOffset=0,0,0\r\n"
			+ "worldOffset=0,0,0\r\n"
			+ "}\r\n";
	
	public String getSurfaceText()
	{
		return surf.replace("%MATERIAL_ID%", ""+material_id);
	}
	
	
	public List<Integer> points = new ArrayList<Integer>(); // 0;1;2;3
	//String uvs14= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75;0.75,-0.75;0.5,-0.5;0.75,-0.75;0.5,-0.5";
	String uvs9= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75;0.75,-0.75;0.5,-0.5;0.75,-0.75;0.5,-0.5";
	String uvs8= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75;0.75,-0.75;0.5,-0.5;0.75,-0.75";
	String uvs7= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75;0.75,-0.75;0.5,-0.5";
	String uvs6= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75;0.75,-0.75";
	String uvs5= "-0.75,-0.75;-0.75,0.75;1,0.9;1,1;0.75,0.75";
	String uvs4= "-0.75,-0.75;-0.75,0.75;0.75,0.75;0.75,-0.75";
	String uvs3= "-0.75,-0.75;-0.75,0.75;0.75,0.75";
	
	
	public List<Integer> getPoints() {
		return points;
	}

}
