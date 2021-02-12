package net.raysforge.bsp.q3;

import java.util.HashMap;
import java.util.Map;

public class SpecialTexturesList {
	
	public static void addSkipItemEntry(Map<String, SkipItem> map, SkipItem si) {
		map.put(si.textureName, si);
	}
	
	public static Map<String, SkipItem> getQ3DM17BorderHighlightList() {
		Map<String, SkipItem> blue = new HashMap<String, SkipItem>();
		addSkipItemEntry(blue, new SkipItem("textures/base_wall/c_met5_2", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_trim/border11b", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_trim/border11light", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_light/lt2_2000", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_light/lt2_8000", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_light/baslt4_1_4k", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_wall/metaltech12final", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_light/light5_5k", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_wall/main_q3abanner", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_support/cable", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("models/mapobjects/kmlamp1", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("models/mapobjects/kmlamp_white", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("models/mapobjects/teleporter/teleporter", false, -1, false));
		addSkipItemEntry(blue, new SkipItem("textures/base_trim/pewter_shiney", false, -1, false));
		return blue;
	}

	public static Map<String, SkipItem> getSkipList() {
		Map<String, SkipItem> skip = new HashMap<String, SkipItem>();
		addSkipItemEntry(skip, new SkipItem("flareShader", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("textures/skies/blacksky", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("textures/sfx/beam", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/spotlamp/beam", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/flare03", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/teleporter/energy", false, -1, false)); // TODO read and make blue ?
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/spotlamp/spotlamp", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/spotlamp/spotlamp_l", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/bot_lamp", false, -1, false)); // head on the railgun pad
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/bot_lamp2", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/bot_flare", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/bot_flare2", false, -1, false));
		addSkipItemEntry(skip, new SkipItem("models/mapobjects/lamps/bot_wing", false, -1, false));
		//addSkipItemEntry(skip, new SkipItem("models/mapobjects/kmlamp1", false, -1, false)); // stand lights
		//addSkipItemEntry(skip, new SkipItem("models/mapobjects/kmlamp_white", false, -1, false));
		return skip;
	}

}
