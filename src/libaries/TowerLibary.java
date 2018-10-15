package libaries;

import game.entities.towers.BasicTower;
import game.entities.towers.BubbleTower;
import game.entities.towers.HomeTower;
import game.entities.towers.Tower;

public enum TowerLibary {
	HOME(new HomeTower(new int[] {
			//damage
			100,
			150,
			200
	}, new int[] {
			//cost
			200,
			300,
			500
	}, new int[] {
			//speed
			1,
			1,
			1
	}, new String[] {
			//lore
			"so farmers do fight",
			"now its almost like they like it",
			"Dont mess with them"
	}, 997, 289)),
	BASIC(new BasicTower(new int[] {
			//damage
			25,
			32,
			45
	}, new int[] {
			//cost
			200,
			200,
			250
	}, new int[] {
			//speed
			2,
			3,
			3
	}, new String[] {
			//lore
			"so basic",
			"welle it is better",
			"hmmm thats okay"
	},
	new int[] {
			150,
			175,
			200
	},
			1090, 289)), 
	BUBBLE(new BubbleTower(new int[] {
			//damage
			10,
			16,
			20
	}, new int[] {
			//cost
			300,
			200,
			250
	}, new int[] {
			//speed
			7,
			7,
			10
	}, new String[] {
			//lore
			"is this even possible",
			"harder bubbles?",
			"this got to stop"
	},
	new int[] {
			125,
			125,
			150
	},
			1180, 289));
	
	private final Tower tower;
	
	TowerLibary(final Tower tower) {
		this.tower = tower;
    }
	
	public Tower getValue() {
		return tower; 
	}
}
