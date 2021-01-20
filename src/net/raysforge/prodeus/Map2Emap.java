package net.raysforge.prodeus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.q2.bsp.Vertex;
import net.raysforge.q3.map.Brush;
import net.raysforge.q3.map.MapParser;
import net.raysforge.q3.map.Plane;
import net.raysforge.q3.map.Point;

public class Map2Emap {

	public static void convert(String mapFile, String emapFile) throws IOException {

		Emap emap = new Emap();

		MapParser mapParser = new MapParser(mapFile);
		
		List<String> allUsedTextures = mapParser.getAllUsedTextures();

		List<Brush> brushList = mapParser.getAllBrushes();
		cleanUpBrushes(brushList);

		for (Brush map_brush : brushList) {
			EmapBrush brush = new EmapBrush();
			List<List<Point>> faces = map_brush.getPolygons();
			int planeNr = 0;
			for (List<Point> points : faces) {
				Plane plane = map_brush.getPlanes().get(planeNr);
				//plane.
				EmapFace face = new EmapFace(allUsedTextures.indexOf(plane.texture), ""); // TODO fix uvs
				for (Point p : points) {
					brush.addPoint(p.x / 30, p.y / 30, p.z / 30);
					face.points.add(brush.points.size() - 1);
				}
				brush.faces.add(face);
				planeNr++;
			}
			emap.addBrush(brush);
			// System.out.println("emap.brush.points.size: " + brush.points.size());
		}

		// Entities

		emap.addNode(new EmapNode(NodeType.Player, mapParser.getEntityOrigin("info_player_start", 1, true).mult(0.03)));
		emap.addNode(new EmapNode(NodeType.Weapon_Shotgun, mapParser.getEntityOrigin("weapon_shotgun", 0, true).mult(0.03)));
		emap.addNode(new EmapNode(NodeType.Weapon_SMG, mapParser.getEntityOrigin("weapon_machinegun", 0, true).mult(0.03)));

		List<Map<String, String>> light_soldiers = mapParser.getEntities("monster_soldier_light");
		for (Map<String, String> light_soldier : light_soldiers) {
			Vertex v = MapParser.originString2Vertex(light_soldier.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.Zombie, v.mult(0.03)));
		}

		List<Map<String, String>> soldiers = mapParser.getEntities("monster_soldier");
		for (Map<String, String> soldier : soldiers) {
			Vertex v = MapParser.originString2Vertex(soldier.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.Soldier_Shotgun, v.mult(0.03)));
		}

		List<Map<String, String>> infantries = mapParser.getEntities("monster_infantry");
		for (Map<String, String> infantry : infantries) {
			Vertex v = MapParser.originString2Vertex(infantry.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.ZombieHeavy_Minigun, v.mult(0.03)));
		}

		List<Map<String, String>> ammo_bullets_list = mapParser.getEntities("ammo_bullets");
		for (Map<String, String> ammo_bullets : ammo_bullets_list) {
			Vertex v = MapParser.originString2Vertex(ammo_bullets.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.Ammo_Bullets_Large, v.mult(0.03)));
		}

		List<Map<String, String>> item_health_list = mapParser.getEntities("item_health");
		for (Map<String, String> item_health : item_health_list) {
			Vertex v = MapParser.originString2Vertex(item_health.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.Health_Small, v.mult(0.03)));
		}

		List<Map<String, String>> lights = mapParser.getEntities("light");
		for (Map<String, String> light : lights) {
			Vertex v = MapParser.originString2Vertex(light.get("origin"), true);
			emap.addNode(new EmapNode(NodeType.Light, v.mult(0.03)));
		}

		emap.writeEMap(emapFile);
	}

	private static void cleanUpBrushes(List<Brush> brushList) {
		List<Brush> brushList2Delete = new ArrayList<Brush>();
		for (Brush q2_brush : brushList) {
			if (q2_brush.brushHasMoreFacesThan(6))
				brushList2Delete.add(q2_brush);
		}
		// remove faces with more than 6 points to help prodeus maybe
		for (Brush q2_brush : brushList2Delete) {
			brushList.remove(q2_brush);
		}
	}
}
