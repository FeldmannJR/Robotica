package robotica.ufpel.feldmann;

public abstract class AbstractNode {

	public abstract byte getX();
	public abstract byte getY();

	public Direction getDirection(AbstractNode to) {
		int difX = getX() - to.getX();
		int difY = getY() - to.getY();
		if(difX > 0 && difY==0) {
			return Direction.EAST;
		}else if(difX < 0 && difY==0) {
			return Direction.WEST;
		}else if(difX==0 && difY>0) {
			return Direction.NORTH;
		}else if(difX == 0 && difY<0) {
			return Direction.SOUTH;
		}	
		return null;
	}
}
