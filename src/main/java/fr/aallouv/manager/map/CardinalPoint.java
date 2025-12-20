package fr.aallouv.manager.map;

public enum CardinalPoint {

	NORTH("North"),
	SOUTH("South"),
	EAST("East"),
	WEST("West");

	private final String string;

	CardinalPoint(String string) {
		this.string = string;
	}

	public String toString() {
		return string;
	}

}
