package robotica;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

public class LightRobo extends Robo{

	ColorSensor light;
	int andou = 0;
	boolean lastCor = false;
	
	public LightRobo() {
		 light = new ColorSensor(Config.lightSensor);
		 preto = isBlack();
	}

	@Override
	public boolean onTick() {
		
		boolean cor = isBlack();
		if(cor!=lastCor) {
			andou++;
			cor = !cor;
		}
		LCD.drawInt(andou, 0, 0);
		float distance = andou*0.6f;
		LCD.drawString(distance+"", 0, 1);
		move();
		return false;
	}
	
	public boolean isBlack() {
		return light.getColorID()==7;
	}
	
	@Override
	public void stop(int d) {
		andou = 0;
		super.stop(d);
	}
}
