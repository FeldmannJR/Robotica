package feldmann;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {
	
	public static Random rnd = new Random();
	public static Controller pilot;


	public static void main(String[] args) {
		pilot = new Controller();
		alwaysExit();
		
		Button.waitForAnyPress();
		control();
		
		
	
	}

	public static void control() {
		if(pilot.hasBarrier(Direction.FORWARD)) {
			boolean b = rnd.nextBoolean();
			Direction dir = b?Direction.RIGHT:Direction.LEFT;
			Direction other = b?Direction.LEFT:Direction.RIGHT;
			if(!pilot.hasBarrier(dir)) {
				pilot.rotateToDirection(dir);
				control();
				return;
			}
			if(!pilot.hasBarrier(other)) {
				pilot.rotateToDirection(other);
				control();
				return;
			}
			//Não achou nenhum lado pra ir
			LCD.drawString("STUCK", 0, 0);
			Button.waitForAnyPress();
			return;
		}else {
			pilot.forward();
		}
		control();
		
	}
	
	
	public static void coolTricks() {
		List<Action> todo = new ArrayList();
		todo.add(Action.FORWARD);
		todo.add(Action.ROTATE_RIGHT);
		todo.add(Action.FORWARD);
		todo.add(Action.ROTATE_RIGHT);
		todo.add(Action.FORWARD);
		todo.add(Action.ROTATE_RIGHT);
		todo.add(Action.FORWARD);
		todo.add(Action.ROTATE_LEFT);
		todo.add(Action.ROTATE_LEFT);
		todo.add(Action.ROTATE_LEFT);
		

		for(Action c : todo) {
			pilot.doAction(c);
			
		}
	}
	
	public static void alwaysExit() {
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
				System.exit(0);				
			}
			
			@Override
			public void buttonPressed(Button b) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
