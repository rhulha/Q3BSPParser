package net.raysforge.q3.map;

public class TestPlane {

	public static void main(String[] args) {
		
		Point p1 = new Point(1,2,-5);
		Point p2 = p1.plus(-2,3,7);
		Point p3 = p1.plus(1,-3,2);
		
		Plane p = new Plane(p1, p2, p3, "");
		
		System.out.println(p.toString2());

	}

}
