package net.raysforge.q2.test;

import java.io.FileWriter;
import java.io.IOException;

import net.raysforge.q2.bsp.Face;
import net.raysforge.q2.bsp.Quake2BSP;
import net.raysforge.q2.bsp.Vertex;

// USING FACES

public class Quake2PLY1 {
	
	
	public static void main(String[] args) throws IOException {

		String bsp_path = "C:\\Users\\Ray\\OneDrive\\Media\\Games\\ShooterPaks\\Q2\\baseq2\\maps\\base1.bsp";
		// bsp_path =  "D:\\GameDev\\Tests\\test_q2.bsp";
				
		Quake2BSP bsp = new Quake2BSP(bsp_path);

		FileWriter fw = new FileWriter("D:\\GameDev\\Tests\\test.ply");
		fw.write("ply\n");
		fw.write("format ascii 1.0\n");
		fw.write("element vertex " + bsp.vertices.length + "\n");
		fw.write("property float32 x\n");
		fw.write("property float32 y\n");
		fw.write("property float32 z\n");
		fw.write("element face " + bsp.faces.length + "\n");
		fw.write("property list uint8 int32 vertex_index\n");
		fw.write("end_header\n");

		
		
		for (Vertex v : bsp.vertices) {
			fw.write(v.x + " " + v.y + " " + v.z + "\n");
		}

		for (Face face : bsp.faces) {
			int fe = face.first_edge;
			char ne = face.num_edges;
			// char ti = face.texture_info;
			
			//int edgeIdx = bsp.face_edges[face.first_edge];
			//Edge edge0 = bsp.edges[Math.abs(edgeIdx)];
			
			// Vertex vert0 = bsp.vertices[edgeIdx < 0 ? edge.edge2 : edge.edge1];
			
			fw.write(((int)ne) + " ");
			
			for (int j = 0; j < ne; j++) {
				int edgeIdx = bsp.face_edges[fe+j];
				int vert_idx = edgeIdx < 0 ? bsp.edges[-edgeIdx].edge2 : bsp.edges[edgeIdx].edge1;
				//int vert_idx2 = bsp.edges[edgeIdx].edge2;
				fw.write(vert_idx + " ");
				 
			}
			fw.write("\n");
			
		}

		

		fw.close();
		bsp.close();
	}


}

