package week4;

import org.junit.runner.notification.RunListener.ThreadSafe;

import com.pi4j.io.gpio.*;

public class PIR_sersor {
	public static void main(String args[]) {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput r_led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW);
		GpioPinDigitalInput pir = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
		
		boolean PIR_result = false;
		
		while(true) {
			PIR_result = pir.isHigh();
			
			if(PIR_result == true) {
				System.out.println("Detected!");
				r_led.high();
				
			}
			else {
				System.out.println("Not detected!");
				r_led.low();
				
			}
		}
	}
}
