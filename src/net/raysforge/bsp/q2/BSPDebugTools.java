package net.raysforge.bsp.q2;

public class BSPDebugTools {

	public static void printBrushes2(Quake2BSP bsp) {
		int b = 0;
		for (Brush brush : bsp.brushes) {
			for (int i = 0; i < brush.n_brushsides; i++) {
				BrushSide brushSide = bsp.brush_sides[brush.brushside + i];
				System.out.print("brush: ");
				System.out.print(b);
				System.out.print(", brush_side: ");
				System.out.print(i);
				System.out.print(", plane: ");
				System.out.println((int) brushSide.plane);
			}
			b++;
		}
	}

	public static void printBrushes(Quake2BSP bsp, int skip_content) {
		for (Brush brush : bsp.brushes) {
			if (brush.content == skip_content)
				continue;
			System.out.print("brushside: ");
			System.out.print((int) brush.brushside);
			System.out.print(", num: ");
			System.out.print((int) brush.n_brushsides);
			System.out.print(", content: ");
			System.out.print((int) brush.content);
			System.out.println();
		}
	}

	public static void printFaces(Quake2BSP bsp) {
		for (Face face : bsp.faces) {
			System.out.print("plane: ");
			System.out.print((int) face.plane);
			System.out.print(", num_edges: ");
			System.out.print((int) face.num_edges);
			System.out.println();
		}
	}

}
