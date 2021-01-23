package net.raysforge.q2.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.raysforge.bsp.q2.Vertex;
import net.raysforge.map.MapParser;

/*
item_health_large
item_health
ammo_shells
weapon_supershotgun
item_health_small
ammo_grenades
light

ammo_cells
item_armor_combat
weapon_bfg
weapon_hyperblaster
ammo_bullets
item_armor_jacket
ammo_rockets
item_armor_shard
weapon_rocketlauncher
*/

public class TestEntityParser {
	public static void main(String[] args) throws IOException {
		MapParser mapParser = new MapParser("D:\\GameDev\\Tests\\base1.map");
			
		// Set<String> keySet = entities.keySet();
		//for (String key : keySet) {			//System.out.println(key);		}
		
		Vertex entityOrigin = mapParser.getEntityOrigin("info_player_start", 0, true);
		System.out.println("entityOrigin: " + entityOrigin);
		
		System.out.println(mapParser.getEntityValue("info_player_start", 0, "origin"));
		System.out.println(mapParser.getEntityValue("weapon_shotgun", 0, "origin"));
		System.out.println(mapParser.getEntityValue("weapon_machinegun", 0, "origin"));


		List<Map<String, String>> light_soldiers = mapParser.getEntities("monster_soldier_light");
		for (Map<String, String> light_soldier : light_soldiers) {
			System.out.println("light_soldier: " + light_soldier.get("origin"));
		}
		
		List<Map<String, String>> soldiers = mapParser.getEntities("monster_soldier");
		for (Map<String, String> soldier : soldiers) {
			System.out.println("soldier: " + soldier.get("origin"));
		}

		List<Map<String, String>> infantries = mapParser.getEntities("monster_infantry");
		for (Map<String, String> infantry : infantries) {
			System.out.println("infantry: " + infantry.get("origin"));
		}

	}

	
}
