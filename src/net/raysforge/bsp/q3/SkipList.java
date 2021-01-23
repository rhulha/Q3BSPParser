package net.raysforge.bsp.q3;

import java.util.ArrayList;
import java.util.List;

public class SkipList {

	public static List<String> getSkipList() {
		List<String> skip = new ArrayList<String>();
		skip.add("flareShader");
		skip.add("textures/skies/blacksky");
		skip.add("textures/sfx/beam");
		skip.add("models/mapobjects/spotlamp/beam");
		skip.add("models/mapobjects/lamps/flare03");
		skip.add("models/mapobjects/teleporter/energy"); // TODO read and make blue ?
		skip.add("models/mapobjects/spotlamp/spotlamp");
		skip.add("models/mapobjects/spotlamp/spotlamp_l");
		skip.add("models/mapobjects/lamps/bot_lamp"); // head on the railgun pad
		skip.add("models/mapobjects/lamps/bot_lamp2");
		skip.add("models/mapobjects/lamps/bot_flare");
		skip.add("models/mapobjects/lamps/bot_flare2");
		skip.add("models/mapobjects/lamps/bot_wing");
		//skip.add("models/mapobjects/kmlamp1"); // stand lights
		//skip.add("models/mapobjects/kmlamp_white");
		return skip;
	}

}
