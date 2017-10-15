package net.raysforge.q3.bsp;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		ReadBSP bsp = new ReadBSP("q3dm17.bsp");
		bsp.getNodes();
		
	}
}
