package game.entities.towers;



public enum TowerLibary {
	HOME(new HomeTower(new int[] {
			//damage
			50,
			75,
			125
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
			250,
			300
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
	}, 1090, 289));
	
	private final Tower tower;
	
	TowerLibary(final Tower tower) {
		this.tower = tower;
    }
	
	public Tower getValue() {
		return tower; 
	}
}
