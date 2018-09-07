package robotica;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.UltrasonicSensor;

public class Robo {

	//Sensores
	public UltrasonicSensor distance;
	
	//Motores
	public NXTRegulatedMotor motorDireita= Motor.A;
	public NXTRegulatedMotor motorEsquerda= Motor.B;
	
	public Robo() {
		distance = new UltrasonicSensor(Config.ultraSonic);
	}
	
	boolean started = false;

	public boolean onTick() {
		if(!started && Button.RIGHT.isDown()) {
			start();
		}else if(!started) {
			return false;
		}
		int d =	distance.getDistance();
		if(d == 255) {
			return false;
		}
		if(d<120) {
			LCD.drawInt(d, 0, 0);
			motorDireita.rotate(10, true);
			motorEsquerda.rotate(10, true);
		}else {
			stop();
		}
		return false;
	}
	
	public void stop() {
		LCD.clear();
		LCD.drawString("Parou a "+distance.getDistance()+" Aperte RIGHT para comecar novamente!", 0, 0);
		started = false;
	}
	
	public void start() {
		LCD.clear();
		started = true;
	}
	public void printStart() {
		 System.out.println("Aperte o botao RIGHT para comecar a andar!");
	}
	
}

