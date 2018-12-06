package robotica.ufpel.feldmann;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import robotica.ufpel.matheusih.Node;


public class Controller {
	

	private NXTRegulatedMotor motorDireita = Motor.C;
	private NXTRegulatedMotor motorEsquerda = Motor.A;
	private NXTRegulatedMotor motorSensor= Motor.B;

	private  final int degreesToRotation = 285;	
	private float perDistance = 2;
			 //(float)(360/(5.1 * (5/3)*Math.PI));
	private final int gridSize = 30;
	
	private Direction dir = Direction.CIMA;
	
	private UltrasonicSensor ultra;
	
	public Controller() {
		
		
		ultra = new UltrasonicSensor(SensorPort.S1);
		
	}
	public void updatePerDistance(float f) {
		perDistance = (float)(360/((3+f) * (5/3)*Math.PI));;
	}
	public void left() { 
		System.out.print("L ");
		Sound.playTone(415, 1);
		setSpeed(motorDireita.getMaxSpeed()/4);
		motorDireita.rotate(degreesToRotation,true);
		motorEsquerda.rotate(-degreesToRotation,false);
	}
	public void right() {
		System.out.print("R ");
		Sound.playTone(214, 1);
		setSpeed(motorDireita.getMaxSpeed()/4);
		motorDireita.rotate(-degreesToRotation,true);
		motorEsquerda.rotate(degreesToRotation,false);
	}
	public void forward() {

		System.out.print("F ");
		Sound.playTone(144, 1);
		setSpeed(motorDireita.getMaxSpeed()/2);
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
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	public void rotateToDirection(Direction to) {
		if(to==dir)return;
		int diffLeft = diffLeft(to);
		int diffRight = diffRight(to);
		//Calculcula pra qual lado é melhor rotacionar
		if(diffRight<diffLeft) {
			for(int x=0;x<diffRight;x++) {
				right();
			}
		}else {
			for(int x=0;x<diffLeft;x++) {
				left();
			}
		}
		dir = to;
	}
	
	public boolean hasBarrier(AbstractNode current,AbstractNode check) {
		Direction to = current.getDirection(check);
		if(to==dir) {
			motorSensor.rotateTo(Direction.CIMA.getRot());
		}else if(to == dir.left()) {
			
			motorSensor.rotateTo(Direction.ESQUERDA.getRot());
		}else if(to == dir.right()) {
			motorSensor.rotateTo(Direction.DIREITA.getRot());		
		}else if(to==dir.left().left()) {
			//Se ta atras tem duas opções, se a cabeça conseguir ler só girar ela, caso contrario girar o robo
			// Codigo pra rotacionar o robo
			//right();
			//motorSensor.rotateTo(Direction.EAST.getRot());;
			//Codigo pra rotacionar a cabeça
			motorSensor.rotateTo(Direction.BAIXO.getRot());
		}
		
		return hasBarrier();
	}
	

	public void move(AbstractNode position,AbstractNode to) {
		System.out.print("\nTO: "+to.getX() +" "+to.getY()+"");
		Direction toMove = position.getDirection(to);
		rotateToDirection(toMove);
		forward();
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
			tmp = tmp.right();
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
			tmp = tmp.left();
		}
		return dif;
	}

}
