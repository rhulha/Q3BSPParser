package net.raysforge.prodeus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.q2.Vertex;
import net.raysforge.map.BaseAxis;
import net.raysforge.map.Brush;
import net.raysforge.map.MapParser;
import net.raysforge.map.Plane;
import net.raysforge.map.Point;

public class Map2Emap {

	private static final String SKYBOX_ASTEROID_SURFACE = "Skybox/Asteroid_Surface";

	public static void convert(String mapFile, String emapFile, String materialsFolder) throws IOException {

		Emap emap = new Emap();

		MapParser mapParser = new MapParser(mapFile);
		
		List<String> allUsedTextures = mapParser.getAllUsedTextures();
		
		int clear_calm1 = allUsedTextures.indexOf("Shaders/liquids/clear_calm1");
		if( clear_calm1 != -1)
			allUsedTextures.set(clear_calm1, SKYBOX_ASTEROID_SURFACE); // Shaders/liquids/clear_calm1 currently crashes Prodeus Level Editor
		
		emap.setMaterials(allUsedTextures);
		int skybox_nr = emap.addMaterial(SKYBOX_ASTEROID_SURFACE);
		System.out.println("skybox_nr: " + skybox_nr);

		List<Brush> brushList = mapParser.getAllBrushes();
		cleanUpBrushes(brushList);

		for (Brush map_brush : brushList) {
			EmapBrush brush = new EmapBrush();
			List<List<Point>> faces = map_brush.getPolygons();
			int planeNr = 0;
			for (List<Point> points : faces) {
				Plane plane = map_brush.getPlanes().get(planeNr);
				if( plane.texture.endsWith("clip") || plane.texture.endsWith("clip_mon") || plane.texture.endsWith("hint") )
					continue;
				
				if( plane.texture.equals("e1u1/sky1")) {
					plane.texture = SKYBOX_ASTEROID_SURFACE;
					System.out.println(plane.texture);
				}
				
				if( plane.texture.equals("Shaders/liquids/clear_calm1")) {
					plane.texture = SKYBOX_ASTEROID_SURFACE;
					System.out.println(plane.texture);
				}
				
				EmapFace face = new EmapFace(allUsedTextures.indexOf(plane.texture));
				for (Point p : points) {
					int pointNr = brush.addPoint(p.x / 30, p.y / 30, p.z / 30);
					face.points.add(pointNr);
					face.uvs.add(BaseAxis.getUV(materialsFolder, plane, new Point(p.x, p.y, p.z)));
				}
				brush.faces.add(face);
				planeNr++;
			}
			emap.addBrush(brush);
			// System.out.println("emap.brush.points.size: " + brush.points.size());
		}

		// Entities

		try {
			emap.addNode(new EmapNode(NodeType.Player, mapParser.getEntityOrigin("info_player_start", 1, true).mult(0.03))); // base 1 has 2 info_player_starts
		} catch (IndexOutOfBoundsException ioobe) {
			emap.addNode(new EmapNode(NodeType.Player, mapParser.getEntityOrigin("info_player_start", 0, true).mult(0.03)));
		}
		try {
			emap.addNode(new EmapNode(NodeType.Weapon_Shotgun, mapParser.getEntityOrigin("weapon_shotgun", 0, true).mult(0.03)));
			emap.addNode(new EmapNode(NodeType.Weapon_SMG, mapParser.getEntityOrigin("weapon_machinegun", 0, true).mult(0.03)));
		} catch (NullPointerException npe) {
			System.out.println("no weapons found.");
		}

		covertEntities(emap, mapParser, "monster_soldier_light", NodeType.Zombie);
		covertEntities(emap, mapParser, "monster_soldier", NodeType.Soldier_Shotgun);
		covertEntities(emap, mapParser, "monster_infantry", NodeType.ZombieHeavy_Minigun);
		covertEntities(emap, mapParser, "ammo_bullets", NodeType.Ammo_Bullets_Large);
		covertEntities(emap, mapParser, "item_health", NodeType.Health_Small);
		covertEntities(emap, mapParser, "light", NodeType.Light);

		emap.writeEMap(emapFile);
	}

	private static void covertEntities(Emap emap, MapParser mapParser, String classname, NodeType nodeType) {
		List<Map<String, String>> entities = mapParser.getEntities(classname);
		if( entities != null)
			for (Map<String, String> entity : entities) {
				Vertex v = MapParser.originString2Vertex(entity.get("origin"), true);
				emap.addNode(new EmapNode(nodeType, v.mult(0.03)));
			}
	}

	// remove faces with more than 6 points.
	// I think Prodeus crashes with too many faces.
	// TODO: Look at this again sometime
	private static void cleanUpBrushes(List<Brush> brushList) {
		List<Brush> brushList2Delete = new ArrayList<Brush>();
		for (Brush q2_brush : brushList) {
			if (q2_brush.brushHasMoreFacesThan(6))
				brushList2Delete.add(q2_brush);
		}
		for (Brush q2_brush : brushList2Delete) {
			brushList.remove(q2_brush);
		}
	}
}
