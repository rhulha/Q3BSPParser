package net.raysforge.q3.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import net.raysforge.q2.bsp.Vertex;

public class BaseAxis {

	// From https://github.com/rfsheffer/q2_map_to_fbx/blob/master/id_map.py

	final static int baseaxis[][] = {
			{ 0, 0, 1 }, { 1, 0, 0 }, { 0, -1, 0 }, // floor
			{ 0, 0, -1 }, { 1, 0, 0 }, { 0, -1, 0 }, // ceiling
			{ 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, // west wall
			{ -1, 0, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, // east wall
			{ 0, 1, 0 }, { 1, 0, 0 }, { 0, 0, -1 }, // south wall
			{ 0, -1, 0 }, { 1, 0, 0 }, { 0, 0, -1 } // north wall
	};

	public static Map.Entry<Point, Point> texture_axis_from_plane(Plane plane) {
		double best = 0;
		int bestaxis = 0;

		for (int i = 0; i < 6; i++) {
			double dot = plane.normal.dot(new Point(baseaxis[i * 3][0], baseaxis[i * 3][1], baseaxis[i * 3][2]));
			if (dot > best)
				best = dot;
			bestaxis = i;
		}

		int[] u = baseaxis[bestaxis * 3 + 1];
		int[] v = baseaxis[bestaxis * 3 + 1];

		return new AbstractMap.SimpleEntry<Point, Point>(new Point(u[0], u[1], u[2]), new Point(v[0], v[1], v[2]));

	}
	
	public Point v2p(Vertex v) {
		return new Point(v.x, v.y, v.z);
	}

	public static Point getUV(Plane plane, Point vertex) {
		
		Point v = vertex;
		
		Point u_axis = texture_axis_from_plane(plane).getKey();
		Point v_axis = texture_axis_from_plane(plane).getValue();
		
		double ang = plane.rotation_angle_in_deg / 180.0 * Math.PI;
		double sinv = Math.sin(ang);
		double cosv = Math.cos(ang);

        if (plane.xscale == 0)
        	plane.xscale = 1;
        if (plane.yscale == 0)
        	plane.yscale = 1;

        double s = v.dot(u_axis);
        double t = v.dot(v_axis);

        double ns = cosv * s - sinv * t;
        double nt = sinv * s + cosv * t;

        s = ns / plane.xscale + plane.xoff;
        t = nt / plane.yscale + plane.yoff;

        // gl scales everything from 0 to 1
        
        int[] physicalTextureInfo = getPhysicalTextureInfo(plane.texture);
        s /= physicalTextureInfo[0]; // width
        t /= physicalTextureInfo[1]; // height

        return new Point(s,t,0);

	}

	// CACHE THE IMAGE STUFF
	private static int[] getPhysicalTextureInfo(String texture) {
		// int [] wh = new int[2];
		String Materials = "D:\\Action\\Steam\\steamapps\\common\\Prodeus\\Prodeus_Data\\StreamingAssets\\Materials";
		System.out.println("getPhysicalTextureInfo: " + new File(Materials,texture+".png"));
		try {
			BufferedImage image = ImageIO.read(new File(Materials,texture+".png"));
			System.out.println("width: " + image.getWidth());
			return new int[] {image.getWidth(), image.getHeight()};
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
	}

}
