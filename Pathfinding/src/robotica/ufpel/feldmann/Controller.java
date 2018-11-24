package robotica.ufpel.feldmann;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import robotica.ufpel.matheusih.Node;


public class Controller {
	

	private NXTRegulatedMotor motorDireita = Motor.C;
	private NXTRegulatedMotor motorEsquerda = Motor.A;
	private NXTRegulatedMotor motorSensor= Motor.B;

	private  final int degreesToRotation = 310;	
	private final float perDistance = (float)(360/(3.5*Math.PI));
	private final int gridSize = 30;
	
	private Direction dir = Direction.NORTH;
	
	private UltrasonicSensor ultra;
	
	public Controller() {
		
		setSpeed(motorDireita.getMaxSpeed()/2);
		ultra = new UltrasonicSensor(SensorPort.S1);
		
	}
	public void left() { 
		motorDireita.rotate(degreesToRotation,true);
		motorEsquerda.rotate(-degreesToRotation,false);
		dir = dir.left();
	}
	public void right() {
		motorDireita.rotate(-degreesToRotation,true);
		motorEsquerda.rotate(degreesToRotation,false);
		dir = dir.right();
	}
	public void forward() {
		motorDireita.rotate((int)(gridSize*perDistance),true);
		motorEsquerda.rotate((int)(gridSize*perDistance),false);	
	}
	public void setSpeed(float speed) {
		motorDireita.setSpeed(speed);
		motorEsquerda.setSpeed(speed);
	}
	public void doAction(Action c) {
		switch (c) {
		case FORWARD:
			forward();
			break;
		case ROTATE_RIGHT:
			right();
			break;
		case ROTATE_LEFT:
			left();
			break;
		default:
			break;
		}
	}
	public void rotateToDirection(Direction to) {
		if(to==dir)return;
		int diffLeft = diffLeft(to);
		int diffRight = diffRight(to);
		//Calculcula pra qual lado é melhor rotacionar
		if(diffRight>diffLeft) {
			for(int x=0;x<diffRight;x++) {
				right();
			}
		}else {
			for(int x=0;x<diffRight;x++) {
				left();
			}
		}
		
	}
	
	public boolean hasBarrier(Direction dir) {
		motorSensor.rotateTo(dir.getRot());
		return hasBarrier();
	}
	

	public void move(Node position,Node to) {
		Direction toMove = getDirection(position, to);
		rotateToDirection(toMove);
		forward();
	}
	
	private Direction getDirection(Node current,Node to) {
		int difX = to.x - current.x;
		int difY = to.y - current.y;
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
	
	private boolean hasBarrier() {
		int d = ultra.getDistance();
		if(d==255) {
			return false;
		}
		if(d<gridSize) {
			return true;
		}
		return false;
	}
	private int diffRight(Direction to) {
		if(to==dir) {
			return 0;
		}
		int dif = 0;
		Direction tmp = dir;
		while(tmp!=to) {
			dif++;
			tmp = tmp.left();
		}
		return dif;
	}
	private int diffLeft(Direction to) {
		if(to==dir) {
			return 0;
		}
		int dif = 0;
		Direction tmp = dir;
		while(tmp!=to) {
			dif++;
			tmp = tmp.right();
		}
		return dif;
	}

}
