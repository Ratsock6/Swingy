package fr.aallouv.manager.map;

public enum CardinalPoint {

	NORTH("North", 0, 1),
	SOUTH("South", 0, -1),
	EAST("East", 1, 0),
	WEST("West", -1, 0);

	private final String string;
	private final int addX;
	private final int addY;

	CardinalPoint(String string, int addX, int addY) {
		this.string = string;
		this.addX = addX;
		this.addY = addY;
	}

	public String toString() {
		return string;
	}

	public int getAddX() {
		return addX;
	}

	public int getAddY() {
		return addY;
	}
}
