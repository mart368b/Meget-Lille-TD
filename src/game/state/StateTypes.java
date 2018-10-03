package game.state;

public enum StateTypes {
	MENU(0),
	LEVEL1_0(1);
	
	private final int value;
	
	public static int getCount() {
		return 2;
	}
	
	StateTypes(final int newValue) {
        value = newValue;
    }
	
	public int getValue() { return value; }
}
