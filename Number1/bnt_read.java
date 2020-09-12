package Number1;

import com.pi4j.io.gpio.*;

public class bnt_read {
	public static void main(String[] args) {
		GpioController gpio = GpioFactory.getInstance();
		
		GpioPinDigitalInput btn = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29);
		
		boolean buttonPressed = false;
		
		while(true) {
			buttonPressed = btn.isHigh();
			if(buttonPressed == true) {
				System.out.println("Button is pressed!");
			}
			
		}
		
		
		
	}
}
