package net.raysforge.map;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.raysforge.bsp.q2.Vertex;

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
			if (dot > best) {
				best = dot;
				bestaxis = i;
			}
		}

		int[] u = baseaxis[bestaxis * 3 + 1];
		int[] v = baseaxis[bestaxis * 3 + 2];

		return new AbstractMap.SimpleEntry<Point, Point>(new Point(u[0], u[1], u[2]), new Point(v[0], v[1], v[2]));

	}
	
	public static Point getUV(String materialsFolder, Plane plane, Point vertex) {
		
		Point v = vertex;
		
		Point u_axis = texture_axis_from_plane(plane).getKey();
		Point v_axis = texture_axis_from_plane(plane).getValue();
		
		if( plane.texture.equals("e1u1/box1_5") ) {
			//System.out.println(v);
			//System.out.println(plane.toString3());
		}
		
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
        
        Dimension physicalTextureInfo = getPhysicalTextureInfo(materialsFolder, plane.texture);
        s /= physicalTextureInfo.width;
        t /= physicalTextureInfo.height;

        return new Point(s,t,0);

	}

	// CACHE THE IMAGE STUFF
	
	static HashMap<String, Dimension> cache = new HashMap<String, Dimension>();;
	
	private static Dimension getPhysicalTextureInfo(String materialsFolder, String texture) {
		
		if(cache.containsKey(texture))
			return cache.get(texture);
		
		//System.out.println("getPhysicalTextureInfo: " + new File(Materials,texture+".png"));
		try {
			BufferedImage image = ImageIO.read(new File(materialsFolder, texture+".png"));
			Dimension d = new Dimension(image.getWidth(), image.getHeight());
			cache.put(texture, d);
			return d;
		} catch (IOException e) {
			System.out.println(texture);
			//FileNameExtensionFilter ff;
			throw new RuntimeException(e);
			
		}
	}

}
