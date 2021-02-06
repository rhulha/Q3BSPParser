package net.raysforge.map;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Map2JSON {
	
	private MapParser mapParser;

	public Map2JSON(String file, boolean includeTriggerBrushes) throws IOException {
		mapParser = new MapParser(file, includeTriggerBrushes);
	}
	
	public StringBuffer convert() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{\r\n");
		
		Map<String, List<Map<String, String>>> entities = mapParser.getEntities();
		for (String key : entities.keySet()) {
			sb.append('"' +key+ "\":[\r\n");
			List<Map<String, String>> list = entities.get(key);
			for (Map<String, String> map : list) {
				sb.append("{\r\n");
				for (String map_key : map.keySet()) {
					String map_val = map.get(map_key);
					sb.append('"' +map_key+ "\":\""+map_val+'"'+",\r\n");
				}
				removeComma(sb);
				sb.append("},\r\n");
			}
			removeComma(sb);
			sb.append("],\r\n");
		}
		removeComma(sb);

		sb.append("}\r\n");
		return sb;
	}

	private void removeComma(StringBuffer sb) {
		if(sb.charAt(sb.length()-3) == ',')
			sb.deleteCharAt(sb.length()-3);
	}

}
