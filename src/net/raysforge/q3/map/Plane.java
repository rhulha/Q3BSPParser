package net.raysforge.q3.map;

public class Plane {
	
	public final Point p1;
	public final Point p2;
	public final Point p3;
	public final String texture;
	
	public final Point normal;
	public final double distance;


	public Plane(Point p1, Point p2, Point p3, String texture ) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.texture = texture;
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

}
