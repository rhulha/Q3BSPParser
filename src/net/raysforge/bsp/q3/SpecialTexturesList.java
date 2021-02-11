package net.raysforge.bsp.q3;

import java.util.ArrayList;
import java.util.List;

public class SpecialTexturesList {
	
	public static List<String> getQ3DM17BorderHighlightList() {
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
		return blue;
	}

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
