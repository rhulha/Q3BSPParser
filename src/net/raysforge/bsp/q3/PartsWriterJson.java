package net.raysforge.bsp.q3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.raysforge.bsp.q3.model.Leaf;
import net.raysforge.bsp.q3.model.Node;
import net.raysforge.map.Plane;

public class PartsWriterJson {

	private File basePath;

	public PartsWriterJson(File basePath) {
		this.basePath = basePath;
	}

	/*
	public void example( BSPReader bsp, BSPWriter bspWriter) throws IOException {
		bspWriter.writeArrayAsJSON( bsp.getLeafBrushes(), "q3dm17.leafbrushes");
		bspWriter.writeArrayAsJSON( bsp.getBrushSides(), "q3dm17.brushsides");
		bspWriter.writeArrayAsJSON( bsp.getBrushes(), "q3dm17.brushes");
		bspWriter.writeObjectAsJSON( bsp.getLeafs(), "q3dm17.leafs");
		bspWriter.writePlanesAsJSON( bsp.getPlanes(), "q3dm17.planes");
		bspWriter.writeEntitiesAsJSON( bsp.getEntities(), "q3dm17.ents");
		bspWriter.writeNodesAsJSON( bsp.getNodes(), "q3dm17.nodes");
	}
	*/

	public void writeObjectAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}

	public void writeArrayAsJSON( Object obj, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		json = json.replace("],[", "],\n[");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filename + " written");
	}
	
	
	public void writeLeafsAsJSON(Leaf[] leafs, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(leafs);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("leafs written");
	}

	public void writePlanesAsJSON(Plane[] planes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(planes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("planes written");
	}

	public void writeEntitiesAsJSON(Map<String, List<Map<String, String>>> entitites, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(entitites);
		json = json.replace("],\"", "],\n\"");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("entitites written");
	}

	public void writeNodesAsJSON(Node[] nodes, String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(nodes);
		json = json.replace("},{", "},\n{");
		try ( FileWriter fw = new FileWriter(new File(basePath, filename))) {
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("nodes written");
	}

}
