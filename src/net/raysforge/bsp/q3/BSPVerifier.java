package net.raysforge.bsp.q3;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BSPVerifier {

	public static void main(String[] args) throws IOException {
		Q3BSPReader bsp = new Q3BSPReader("q3dm17.bsp");

		printAllBrushSidesShader(bsp);
		// verifyValues(bsp);
	}

	static void printAllShader(Q3BSPReader bsp) throws IOException {
		Shader[] shaders = bsp.getShaders();
		for (Shader shader : shaders) {
			System.out.println(shader.shader);
		}
	}

	static void printAllBrushSidesShader(Q3BSPReader bsp) throws IOException {
		Shader[] shaders = bsp.getShaders();

		Set<String> uniqueShader = new HashSet<String>();

		int[][] brushSides = bsp.getBrushSides();
		
		for (int[] brushSide : brushSides) {
			uniqueShader.add(shaders[brushSide[1]].shader);
		}
		
		for (String string : uniqueShader) {
			System.out.println(string);
		}
	}

	static void printAllBrushesShader(Q3BSPReader bsp) throws IOException {
		Shader[] shaders = bsp.getShaders();

		Set<String> uniqueShader = new HashSet<String>();

		int[][] brushes = bsp.getBrushes();
		
		for (int[] brush : brushes) {
			uniqueShader.add(shaders[brush[2]].shader);
		}
		
		for (String string : uniqueShader) {
			System.out.println(string);
		}
	}

	static void printAllSurfacesShader(Q3BSPReader bsp) throws IOException {
		Shader[] shaders = bsp.getShaders();

		Set<String> uniqueShader = new HashSet<String>();

		for (Surface face : bsp.getSurfaces()) {
			uniqueShader.add(shaders[face.shaderNum].shader);
		}

		for (String string : uniqueShader) {
			System.out.println(string);
		}
	}

}
