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
	public List<Vertex> vertexes;
	public Map<String, List<Map<String, String>>> entities;
	public Surface[] surfaces;
	public Shader[] shaders;

	public Q3BSP(File bspFile) throws IOException {
		Q3BSPReader reader = new Q3BSPReader(bspFile);
		brushes = reader.getBrushes();
		brushSides = reader.getBrushSides();
		indexes = reader.getDrawIndexes();
		vertexes = reader.getDrawVerts();
		entities = reader.getEntities();
		surfaces = reader.getSurfaces();
		shaders = reader.getShaders();
		
	}

	public void flipYZ() {
		System.out.println("flipping YZ");
		for (Vertex v : vertexes) {
			v.xyz = v.xyz.getPointSwapZY();
		}
	}
	
	public void writeBasics(PartsWriter partsWriter) throws IOException {
		partsWriter.writeIndexes( SkipList.getSkipList(), surfaces, indexes, shaders, "q3dm17.indices");
		partsWriter.writeVerts( vertexes, "q3dm17.verts");
		partsWriter.writeNormals( vertexes, "q3dm17.normals");
		partsWriter.writeTexCoords( vertexes, "q3dm17.texCoords");
		partsWriter.writeLmCoords( vertexes, "q3dm17.lmCoords");
		partsWriter.writeColors( vertexes, "q3dm17.colors");
	}

	public void tessellateAllPatchFaces() {
		System.out.println("tessellateAllPatchFaces");
		for (Surface face : surfaces) {
			if( face.surfaceType == Surface.patch) {
				Tessellate.tessellate(face, vertexes, indexes, 10);
			}
		}
	}

	
	public void changeColors() throws IOException {
		System.out.println("changing Colors");
		List<String> blue = new ArrayList<String>();
		blue.add("textures/base_wall/c_met5_2");
		blue.add("textures/base_trim/border11b");
		blue.add("textures/base_trim/border11light");
		blue.add("textures/base_light/lt2_2000");
		blue.add("textures/base_light/lt2_8000");
		blue.add("textures/base_light/baslt4_1_4k");
		blue.add("textures/base_wall/metaltech12final");
		blue.add("textures/base_light/light5_5k");
		blue.add("textures/base_wall/main_q3abanner");
		blue.add("textures/base_support/cable");
		blue.add("models/mapobjects/kmlamp1");
		blue.add("models/mapobjects/kmlamp_white");
		blue.add("models/mapobjects/teleporter/teleporter");
		blue.add("textures/base_trim/pewter_shiney");
		
		List<String> red = new ArrayList<String>();
		//red.add("textures/base_wall/atech1_e");
		//red.add("textures/base_light/light5_5k");

		for (Surface face : surfaces) {
			if( blue.contains( shaders[face.shaderNum].shader) ) {
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					vertexes.get(i).color=new Point(vertexes.get(i).color.x,vertexes.get(i).color.y,1);
                }
			}
			if( red.contains( shaders[face.shaderNum].shader) ) {
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					vertexes.get(i).color=new Point(1, vertexes.get(i).color.y,vertexes.get(i).color.z);
                }
			}
			
			if( shaders[face.shaderNum].shader.equals("textures/base_floor/diamond2c") ) { // special middle jump pad handling
				for(int k = 0; k < face.numIndexes; ++k) {
					int i = face.firstVert + indexes.get(face.firstIndex + k);
					double z = vertexes.get(i).xyz.z;
					if( z >= 95 && z <= 108 ) {
						vertexes.get(i).color=new Point( 0.25, 0.25, 1);
					}
					
                }
			}
		}
	}


}