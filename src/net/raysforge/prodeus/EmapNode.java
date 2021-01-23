package net.raysforge.prodeus;

import net.raysforge.bsp.q2.model.Vertex;

public class EmapNode {

	protected NodeType nodeType;
	private int id;
	private static int ID_COUNTER=0;
	private Vertex pos;
	
	public EmapNode(NodeType nodeType, Vertex pos) {
		this.nodeType = nodeType;
		this.pos = pos;
		this.id = ID_COUNTER++;
	}
	
	public String getNodeText() {
		String node_text;
		try {
			node_text = this.getClass().getDeclaredField(nodeType.toString()).get(this).toString();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
		node_text = node_text.replace("%POS%", ""+ pos.x+","+pos.y+","+pos.z);
		node_text = node_text.replace("%ID%", ""+ id);
		return node_text;
	}

	String Player = "Node{\r\n"
			+ "Player\r\n"
			+ "giveFists=True\r\n"
			+ "slowFadeIn=False\r\n"
			+ "startNoHud=False\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";

	String Light ="Node{\r\n"
			+ "Light\r\n"
			+ "isOn=True\r\n"
			+ "range=10\r\n" // TODO: use real numbers from Quake map
			+ "color=0.87,0.9571,1,1\r\n" // TODO: use real numbers from Quake map
			+ "intensity=1.5\r\n" // TODO: use real numbers from Quake map
			+ "type=2\r\n"
			+ "spotAngle=30\r\n"
			+ "shadows=False\r\n"
			+ "flickerTime=1\r\n"
			+ "loop=True\r\n"
			+ "isSun=False\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=60,315,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Weapon_Pistol ="Node{\r\n"
			+ "Weapon_Pistol\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Weapon_Shotgun = "Node{\r\n"
			+ "Weapon_Shotgun\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";

	
	String Weapon_SMG = "Node{\r\n"
			+ "Weapon_SMG\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Weapon_SuperShotgun = "Node{\r\n"
			+ "Weapon_SuperShotgun\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Zombie ="Node{\r\n"
			+ "Zombie\r\n"
			+ "startTriggered=True\r\n"
			+ "useEffect=False\r\n"
			+ "stationary=False\r\n"
			+ "delayMin=0\r\n"
			+ "delayMax=0\r\n"
			+ "invisible=False\r\n"
			+ "invisibleHealth=50\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,180,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	
	String ZombieHazmat = "Node{\r\n"
			+ "ZombieHazmat\r\n"
			+ "startTriggered=True\r\n"
			+ "useEffect=False\r\n"
			+ "stationary=False\r\n"
			+ "delayMin=0\r\n"
			+ "delayMax=0\r\n"
			+ "invisible=False\r\n"
			+ "invisibleHealth=50\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String ZombieHeavy_Minigun = "Node{\r\n"
			+ "ZombieHeavy_Minigun\r\n"
			+ "startTriggered=True\r\n"
			+ "useEffect=False\r\n"
			+ "stationary=False\r\n"
			+ "delayMin=0\r\n"
			+ "delayMax=0\r\n"
			+ "invisible=False\r\n"
			+ "invisibleHealth=50\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Soldier_Shotgun = "Node{\r\n"
			+ "Soldier_Shotgun\r\n"
			+ "startTriggered=True\r\n"
			+ "useEffect=False\r\n"
			+ "stationary=False\r\n"
			+ "delayMin=0\r\n"
			+ "delayMax=0\r\n"
			+ "invisible=False\r\n"
			+ "invisibleHealth=50\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String ZombieSniper = "Node{\r\n"
			+ "ZombieSniper\r\n"
			+ "startTriggered=True\r\n"
			+ "useEffect=False\r\n"
			+ "stationary=False\r\n"
			+ "delayMin=0\r\n"
			+ "delayMax=0\r\n"
			+ "invisible=False\r\n"
			+ "invisibleHealth=50\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Armor_Large = "Node{\r\n"
			+ "Armor_Large\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";

	
	String Ammo_Shells_Small = "Node{\r\n"
			+ "Ammo_Shells_Small\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Armor_Small = "Node{\r\n"
			+ "Armor_Small\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Ammo_Shells_Large = "Node{\r\n"
			+ "Ammo_Shells_Large\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Ammo_Bullets_Small ="Node{\r\n"
			+ "Ammo_Bullets_Small\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Ammo_Bullets_Large = "Node{\r\n"
			+ "Ammo_Bullets_Large\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	
	String Health_Small ="Node{\r\n"
			+ "Health_Small\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Health_Large = "Node{\r\n"
			+ "Health_Large\r\n"
			+ "startSpawned=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
	
	String Soldier_Dead_01 = "Node{\r\n"
			+ "Soldier_Dead_01\r\n"
			+ "snapToFloor=True\r\n"
			+ "attachToFloor=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";
			
	String BarrelExplosive = "Node{\r\n"
			+ "BarrelExplosive\r\n"
			+ "snapToFloor=True\r\n"
			+ "attachToFloor=True\r\n"
			+ "pos=%POS%\r\n"
			+ "rotation=0,0,0\r\n"
			+ "id=%ID%\r\n"
			+ "layer=-1\r\n"
			+ "parent=-1\r\n"
			+ "}\r\n";

	
}
