package net.raysforge.q3.bsp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BSPVerifier {

	public static void main(String[] args) throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");

		printAllBrushSidesShader(bsp);
		// verifyValues(bsp);
	}

	static void printAllShader(BSPReader bsp) throws IOException {
		Shader[] shaders = bsp.getShaders();
		for (Shader shader : shaders) {
			System.out.println(shader.shader);
		}
	}

	static void printAllBrushSidesShader(BSPReader bsp) throws IOException {
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

	static void printAllBrushesShader(BSPReader bsp) throws IOException {
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

	static void printAllSurfacesShader(BSPReader bsp) throws IOException {
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
