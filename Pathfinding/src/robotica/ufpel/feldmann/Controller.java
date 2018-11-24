package robotica.ufpel.feldmann;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Controller {
	

	private NXTRegulatedMotor motorDireita = Motor.C;
	private NXTRegulatedMotor motorEsquerda = Motor.A;
	private NXTRegulatedMotor motorSensor= Motor.B;

	private  final int degreesToRotation = 310;	
	private final float perDistance = (float)(360/(3.5*Math.PI));
	private final int gridSize = 30;

	private static UltrasonicSensor ultra;
	
	public Controller() {
		
		setSpeed(motorDireita.getMaxSpeed()/2);
		ultra = new UltrasonicSensor(SensorPort.S1);
		
	}
	public void left() { 
		motorDireita.rotate(degreesToRotation,true);
		motorEsquerda.rotate(-degreesToRotation,false);
	}
	public void right() {
		motorDireita.rotate(-degreesToRotation,true);
		motorEsquerda.rotate(degreesToRotation,false);
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
	public void rotateToDirection(Direction dir) {
		if(dir==Direction.LEFT) {
			left();
		}else
		if(dir==Direction.RIGHT) {
			right();
		}
	}
	
	public boolean hasBarrier(Direction dir) {
		motorSensor.rotateTo(dir.getRot());
		return hasBarrier();
	
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
}
