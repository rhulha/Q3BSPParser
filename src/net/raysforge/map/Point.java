package net.raysforge.map;

import static net.raysforge.generic.DecimalFormater.f1;

public class Point {

	public final double x;
	public final double y;
	public final double z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

	}
	
	// supports arrays with only 2 elements
	public Point( float[] f) {
		this( f[0], f[1], f.length == 3 ? f[2] : 0);
	}

	public Point(byte[] b) {
		this( (b[0]&0xFF)/255.0, (b[1]&0xFF)/255.0, (b[2]&0xFF)/255.0);
	}

	public Point getPointSwapZY() {
		return new Point( x, z, y);
	}

	public String getXDividedBy128() {
		return f1.format(x/128);
	}
	public String getYDividedBy128() {
		return f1.format(y/128);
	}
	public String getZDividedBy128() {
		return f1.format(z/128);
	}

	public Point minus(Point p2) {
		return new Point(x - p2.x, y - p2.y, z - p2.z);
	}

	public Point cross(Point p2) {
		return new Point(y * p2.z - z * p2.y, z * p2.x - x * p2.z, x * p2.y - y * p2.x);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Point normalize() {
		double len = length();
		if (len == 0) {
			return new Point(0, 0, 0);
		} else if (len == 1) {
			return new Point(x, y, z);
		} else {
			len = 1 / len;
			return new Point(x * len, y * len, z * len);
		}

	}

	public Point negate() {
		return new Point( -x, -y, -z);
	}

	public double dot(Point p2) {
		return x * p2.x + y * p2.y + z * p2.z;
	}

	public Point times(double d) {
		return new Point(x * d, y * d, z * d);
	}

	public Point dividedBy(double d) {
		return new Point(x / d, y / d, z / d);
	}

	public Point plus(int x2, int y2, int z2) {
		return new Point(x + x2, y + y2, z + z2);
	}

	public Point plus(Point p) {
		return new Point(x + p.x, y + p.y, z + p.z);
	}

	public Point scale(double d) {
		return new Point(this.x*d, this.y*d, this.z*d);
	}

	public Point add(Point p) {
		return new Point(this.x+p.x, this.y+p.y, this.z+p.z);
	}

	public static Point getPointSwapZY( double x, double z, double y) {
		return new Point( x, y, z);
	}

	@Override
	public String toString() {
		return "( " + x + " " + y + " " + z +" )";
	}

}
