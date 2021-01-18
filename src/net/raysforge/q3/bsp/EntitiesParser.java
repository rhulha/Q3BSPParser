package net.raysforge.q3.bsp;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.raysforge.generic.GenericParser;

/*
ammo_bullets
item_armor_shard
item_health
item_armor_combat
info_player_intermission
item_health_large
trigger_push
weapon_rocketlauncher
item_quad
target_speaker
trigger_multiple
ammo_slugs
weapon_railgun
trigger_hurt
light
ammo_shells
ammo_rockets
item_health_mega
misc_model
worldspawn
misc_teleporter_dest
trigger_teleport
info_player_deathmatch
target_remove_powerups
target_position
weapon_shotgun
item_armor_body
*/

public class EntitiesParser extends GenericParser {

	public EntitiesParser(String entities) {
		super(initStreamTokenizer(entities));
	}

	private static StreamTokenizer initStreamTokenizer(String entities) {
		StreamTokenizer st = new StreamTokenizer(new StringReader(entities));
		st.eolIsSignificant(true);
		st.slashSlashComments(false);
		st.quoteChar('"');
		return st;
	}

	public String getNextString() throws IOException {
		assertNextToken('"');
		return st.sval;
	}

	// the outer map contains the classname of the entities
	// so you can get all "info_player_deathmatch" for example
	// the inner map contains all name value pairs of the entity
	public Map<String, List<Map<String, String>>> parse() throws IOException {

		Map<String, List<Map<String, String>>> entities = new HashMap<>();

		while (peekNextToken() == '{') {
			assertNextToken('{');
			swallowEOLs();

			HashMap<String, String> ent = new HashMap<>();

			while( peekNextToken() != '}')
			{
				String key = getNextString();
				String value = getNextString();
				ent.put(key, value);
				assertNextToken(10);
			}

			String cn = ent.get("classname");
			if( ! entities.containsKey(cn)) {
				entities.put(cn, new ArrayList<Map<String, String>>());
			}
			
			entities.get(cn).add(ent);

			swallowEOLs();
			assertNextToken('}');
			swallowEOLs();
		}

		return entities;

	}

}
