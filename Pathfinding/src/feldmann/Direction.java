package feldmann;

public enum Direction {
	LEFT(-90),FORWARD(0),RIGHT(90);

	int rot;
	private Direction(int rot) {
		this.rot =rot;	
	}
	public int getRot() {
		return rot;
	}
}
