package net.raysforge.bsp.q3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.q3.model.Shader;
import net.raysforge.bsp.q3.model.Surface;
import net.raysforge.bsp.q3.model.Vertex;
import net.raysforge.map.Point;

public class Q3BSP {
	
	public int[][] brushes;
	public int[][] brushSides;
	public List<Integer> indexes;
	public List<Vertex> vertices;
	public Map<String, List<Map<String, String>>> entities;
	public Surface[] surfaces;
	public Shader[] shaders;

	public Q3BSP(File bspFile) throws IOException {
		Q3BSPReader reader = new Q3BSPReader(bspFile);
		brushes = reader.getBrushes();
		brushSides = reader.getBrushSides();
		indexes = reader.getDrawIndexes();
		vertices = reader.getDrawVerts();
		entities = reader.getEntities();
		surfaces = reader.getSurfaces();
		shaders = reader.getShaders();
		
	}

	public void scaleYZ(double d) {
		for (Vertex v : vertices) {
			v.xyz.scaleInPlace(d);
		}
	}

	public void flipYZ() {
		System.out.println("flipping YZ");
		for (Vertex v : vertices) {
			v.xyz = v.xyz.getPointSwapZY();
		}
	}
	
	public void tessellateAllPatchFaces(int level) {
		System.out.println("tessellateAllPatchFaces");
		for (Surface face : surfaces) {
			if( face.surfaceType == Surface.patch) {
				Tessellate.tessellate(face, vertices, indexes, level);
			}
		}
	}

	public void changeColors() throws IOException {
		System.out.println("changing Colors");
		
		List<String> red = new ArrayList<String>();
		//red.add("textures/base_wall/atech1_e");
		//red.add("textures/base_light/light5_5k");

		List<String> blue = SpecialTexturesList.getQ3DM17BorderHighlightList();
		
		for (Surface face : surfaces) {
			if( blue.contains( shaders[face.shaderNum].shader) ) {
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					vertices.get(i).color=new Point(vertices.get(i).color.x,vertices.get(i).color.y,1);
                }
			}
			if( red.contains( shaders[face.shaderNum].shader) ) {
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					vertices.get(i).color=new Point(1, vertices.get(i).color.y,vertices.get(i).color.z);
                }
			}
			
			if( shaders[face.shaderNum].shader.equals("textures/base_floor/diamond2c") ) { // special middle jump pad handling
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					double z = vertices.get(i).xyz.z;
					if( z >= 95 && z <= 108 ) {
						vertices.get(i).color=new Point( 0.25, 0.25, 1);
					}
					
                }
			}
		}
	}

}
