package net.raysforge.q3.map;

public class Plane {
	
	public Point p1;
	public Point p2;
	public Point p3;
	public String texture;
	
	public final Point normal;
	public final double distance;

	public final static int size = 16;
	
	// http://www.gamers.org/dEngine/quake2/Q2DP/Q2DP_Map/Q2DP_Map.shtml
	public int xoff;
	public int yoff;
	public double rotation_angle_in_deg;
	public double xscale;
	public double yscale;

	public Plane(Point normal, double distance) {
		this.normal = normal;
		this.distance = distance;
	}

	public Plane(Point p1, Point p2, Point p3, String texture ) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.texture = texture;
		normal = p3.minus(p2).cross( p1.minus(p2)).normalize();
		//normal = p3.minus(p1).cross( p2.minus(p1)).normalize();
		distance = -normal.dot( p1);
	}
	
	public Plane(Point p1, Point p2, Point p3, String texture, int xoff, int yoff, double rotation_angle_in_deg, double xscale, double yscale) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.texture = texture;
		this.xoff = xoff;
		this.yoff = yoff;
		this.rotation_angle_in_deg = rotation_angle_in_deg;
		//if( rotation_angle_in_deg != 0)
			//System.out.println("rotation_angle_in_deg: " + rotation_angle_in_deg);
		this.xscale = xscale;
		this.yscale = yscale;
		normal = p3.minus(p2).cross( p1.minus(p2)).normalize();
		//normal = p3.minus(p1).cross( p2.minus(p1)).normalize();
		distance = -normal.dot( p1);
	}

	public double distance( Point p) {
		return normal.dot(p) + distance;
	}
	
	@Override
	public String toString() {
		return p1 + " " + p2 + " " + p3 + " " + texture + " " + normal + " " + distance;
	}

	public String toString2() {
		return normal.x+"x + " + normal.y+"y + " + normal.z+"z = " + -distance;
	}

	public String getTexture() {
		return texture;
	}

}
