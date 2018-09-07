package robotica;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class Main {

	public static void main(String [] args) throws Exception {
		 Robo r = new Robo();
		 r.printStart();
		 while(!Button.ESCAPE.isDown()) {
			 if(r.onTick()) {
				 break;
			 }
		 }
		 
	}
}
