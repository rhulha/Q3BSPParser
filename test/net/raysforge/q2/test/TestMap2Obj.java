package net.raysforge.q2.test;

import java.io.IOException;

import net.raysforge.map.Q3Map2Obj;

public class TestMap2Obj {

	
	public static void main(String[] args) throws IOException {

		Q3Map2Obj.map2obj("q3dm17sample.map", "q3dm17sample.obj");
		// Q3Map2Obj.map2obj("D:\\GameDev\\Tests\\base1.map", "D:\\GameDev\\Tests\\base1.obj");

	}

}
