package net.raysforge.q3.bsp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.raysforge.q3.map.Point;

public class Main {
	
	static String basePath = "C:\\Users\\Ray\\dart\\Instagib\\web\\data\\";
	
	public static void test() throws IOException {
		BSPReader bsp = new BSPReader("q3dm17.bsp");
		
		byte[] lump = bsp.getLump(LumpTypes.Lightmaps);
		
		System.out.println(lump.length / (128*128*3));
		
		Shader[] textures = bsp.getShaders();
		for (Shader texture : textures) {
			System.out.println(texture.shader);
		}
	}
	
	public static void main(String[] args) throws IOException {

		BSPReader bsp = new BSPReader("q3dm17.bsp");
		BSPWriter bspWriter = new BSPWriter( basePath);
		
		writeBasics(bsp, bspWriter);

		writeFile( basePath + "q3dm17.brushes", bsp.getLump(LumpTypes.Brushes));
		writeFile( basePath + "q3dm17.brushsides", bsp.getLump(LumpTypes.BrushSides));
		writeFile( basePath + "q3dm17.leafbrushes", bsp.getLump(LumpTypes.LeafBrushes));
		writeFile( basePath + "q3dm17.leafs", bsp.getLump(LumpTypes.Leafs));
		writeFile( basePath + "q3dm17.nodes", bsp.getLump(LumpTypes.Nodes));
		writeFile( basePath + "q3dm17.planes", bsp.getLump(LumpTypes.Planes));
		
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
	}

	public static void writeBasics(BSPReader bsp, BSPWriter bspWriter) throws IOException {
		Surface[] surfaces = bsp.getSurfaces();
		List<Vertex> vertexes = bsp.getDrawVerts();
		List<Integer> indexes = bsp.getDrawIndexes();

		for (Surface face : surfaces) {
			if( face.surfaceType == Surface.patch) {
				System.out.println("tessellate");
				Tessellate.tessellate(face, vertexes, indexes, 10);
			}
		}

		bspWriter.writeVerts( vertexes, "q3dm17.verts");

		Shader[] shaders = bsp.getShaders();
		bspWriter.writeObjectAsJSON( shaders, "q3dm17.textures");
		
		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy"); // TODO readd and make blue ?
		skip.add("models/mapobjects/spotlamp/spotlamp");
		skip.add("models/mapobjects/spotlamp/spotlamp_l");
		skip.add("models/mapobjects/lamps/bot_lamp"); // head on the railgun pad
		skip.add("models/mapobjects/lamps/bot_lamp2");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");
		skip.add("models/mapobjects/lamps/bot_wing");
		//skip.add("models/mapobjects/kmlamp1"); // stand lights
		//skip.add("models/mapobjects/kmlamp_white");

		bspWriter.writeIndexes( skip, surfaces, indexes, shaders, "q3dm17.indices");
		bspWriter.writeNormals( vertexes, "q3dm17.normals");
		bspWriter.writeTexCoords( vertexes, "q3dm17.texCoords");
		bspWriter.writeLmCoords( vertexes, "q3dm17.lmCoords");
		
		changeColors(surfaces, indexes, shaders, vertexes);
		
		bspWriter.writeColors( vertexes, "q3dm17.colors");

	}
	
	static void changeColors(Surface[] surfaces, List<Integer> indexes, Shader[] shaders, List<Vertex> vertexes) throws IOException {
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

	public static void writeFile( String fn, byte[] bytes) {
		try ( FileOutputStream fos = new FileOutputStream( fn)) {
			fos.write( bytes );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
