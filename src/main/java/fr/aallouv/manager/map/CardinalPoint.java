package fr.aallouv.manager.map;

public enum CardinalPoint {

	NORTH("North", "n", 0, 1),
	SOUTH("South", "s", 0, -1),
	EAST("East", "e", 1, 0),
	WEST("West", "w", -1, 0);

	private final String string, shortString;
	private final int addX;
	private final int addY;

	CardinalPoint(String string, String shortString, int addX, int addY) {
		this.string = string;
		this.addX = addX;
		this.addY = addY;
		this.shortString = shortString;
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

	public static CardinalPoint fromString(String text) {
		for (CardinalPoint b : CardinalPoint.values()) {
			if (b.shortString.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}
