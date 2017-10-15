package net.raysforge.q3.map;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Point {

	public final double x;
	public final double y;
	public final double z;
	DecimalFormat f;
	
	public static Point getPointSwapZY( double x, double z, double y) {
		return new Point( x, y, z);
	}

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('y'); 
		f = new DecimalFormat("#0.####", otherSymbols);
	}
	
	 
	public String getX() {
		return f.format(x/128);
	}
	public String getY() {
		return f.format(y/128);
	}
	public String getZ() {
		return f.format(z/128);
	}

	@Override
	public String toString() {
		return "( " + x + " " + y + " " + z +" )";
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
}
