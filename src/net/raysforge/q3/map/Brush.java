package net.raysforge.q3.map;

import java.util.ArrayList;
import java.util.List;

public class Brush {

	public final List<Plane> planes;

	List<List<Point>> polys = new ArrayList<List<Point>>();

	public Brush(List<Plane> planes) {
		this.planes = planes;

		for (int i = 0; i < planes.size(); i++) {
			polys.add(new ArrayList<Point>());
		}
	}

	public List<List<Point>> calculatePolygons() {
		int count = planes.size();

		for (int i = 0; i <= count - 3; i++) {
			for (int j = i; j <= count - 2; j++) {
				for (int k = j; k <= count - 1; k++) {
					if (i == j || j == k || i == k)
						continue;
					Point intersection = getIntersection(planes.get(i), planes.get(j), planes.get(k));
					if (intersection == null)
						continue;

					// System.out.println("i: " + intersection);
					boolean legal = true;
					for (int m = 0; m < count; m++) {
						// Test if the point is outside the brush
						double d = planes.get(m).distance(intersection);
						if (d > epsilon) {
							legal = false;
							//System.out.println("ILLEGAL: " + planes.get(m).toString2() + ", d: " + d + ", i: " + intersection);
							break;
						}
					}
					if (legal) {
						//System.out.println("LEG");
						polys.get(i).add(intersection); // Add vertex to
						polys.get(j).add(intersection); // 3 polygons
						polys.get(k).add(intersection); // at a time
					}
				}
			}
		}

		sortVerticesCW();
		return polys;

	}

	double epsilon = 1e-5;

	public Point getIntersection(Plane a, Plane b, Plane c) {
		double denom = a.normal.dot(b.normal.cross(c.normal));
		if (Math.abs(denom) < epsilon)
			return null;
		//System.out.println("a: " + a.toString2());
		//System.out.println("b: " + b.toString2());
		//System.out.println("c: " + c.toString2());
		// p = -d1 * ( n2.Cross ( n3 ) ) – d2 * ( n3.Cross ( n1 ) ) – d3 * ( n1.Cross ( n2 ) ) / denom;
		Point temp1 = b.normal.cross(c.normal).times(-a.distance);
		Point temp2 = c.normal.cross(a.normal).times(b.distance);
		Point temp3 = a.normal.cross(b.normal).times(c.distance);
		return temp1.minus(temp2).minus(temp3).dividedBy(denom);
	}

	// clock wise
	public void sortVerticesCW() {

		for (int n = 0; n < polys.size(); n++) {
			List<Point> polygon = polys.get(n);
			Plane plane = planes.get(n);

			Point center = new Point(0, 0, 0);
			for (Point point : polygon) {
				center = center.plus(point);
			}
			center = center.dividedBy(polygon.size());

			//
			// Sort vertices
			//
			for (int i = 0; i < polygon.size() - 2; i++) {
				Point a;
				Plane p;
				double smallestAngle = -1;
				int smallest = -1;

				a = polygon.get(i).minus(center);
				a = a.normalize();

				p = new Plane(polygon.get(i), center, center.plus(plane.normal), "");

				for (int j = i + 1; j < polygon.size(); j++) {
					double d = p.distance(polygon.get(j));
					if (d > -epsilon) {
						Point b = polygon.get(j).minus(center).normalize();
						double angle = a.dot(b);
						if (angle > smallestAngle) {
							smallestAngle = angle;
							smallest = j;
						}
					}
				}

				if (smallest == -1) {
					System.out.println("Error: Degenerate polygon!");
					return;
				}

				Point t = polygon.get(smallest);
				polygon.set(smallest, polygon.get(i + 1));
				polygon.set(i + 1, t);
			}

		}
	}
}
