package game.entities.towers;



public enum TowerLibary {
	HOME(new HomeTower(new int[] {
			//damage
			20,
			30,
			45
	}, new int[] {
			//cost
			200,
			100,
			150
	}, new int[] {
			//speed
			1,
			2,
			3
	}, new String[] {
			//lore
			"so farmers do fight",
			"now its almost like they like it",
			"Dont mess with them"
	}, 997, 289)),
	BASIC(new BasicTower(new int[] {
			//damage
			5,
			10,
			25
	}, new int[] {
			//cost
			100,
			50,
			100
	}, new int[] {
			//speed
			1,
			2,
			3
	}, new String[] {
			//lore
			"so basic",
			"welle it is better",
			"hmmm thats okay"
	}, 1090, 289));
	
	private final Tower tower;
	
	TowerLibary(final Tower tower) {
		this.tower = tower;
    }
	
	public Tower getValue() {
		return tower; 
	}
}
