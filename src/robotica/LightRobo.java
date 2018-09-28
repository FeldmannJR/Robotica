package robotica;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.Color;

public class LightRobo extends Robo{

	ColorSensor light;
	float andou = 0;
	
	int andouSteps = 0;
	int andouPreto = 0;
	int andouBranco =0;
	
	boolean lastCor = false;
	
	public LightRobo() {
		 light = new ColorSensor(Config.lightSensor);
		 lastCor = isBlack();
		 light.calibrateLow();
	}

	@Override
	public boolean onTick() {
		int colorid = light.getColorID();
		boolean cor = colorid==7;
		if(cor!=lastCor) {
			andouSteps++;
			if(lastCor) {
				//Preto
				andou+=1.07f;
				andouPreto++;
			}else{
				//Branco
				andouBranco++;
				andou+=0.57;
			}
			
		}
		LCD.drawString(andou+"", 0, 1);
		LCD.drawString("Color: "+colorid, 0, 0);
		if(andou<100) {
			move();
		}else {
			stop((int)andou);
			//move();	
		}
		LCD.drawString("Preto "+andouPreto, 0, 2);
		LCD.drawString("Branco "+andouBranco, 0, 3);
		
		lastCor = cor;
		return false;
	}
	
	public boolean isBlack() {
		return light.getColorID()==7;
	}
	
	@Override
	public void stop(int d) {
		andou = 0;
		andouSteps = 0;
		andouBranco = 0;
		andouPreto = 0;
		super.stop(d);
	}
}
