package my_project;

import org.junit.runner.notification.RunListener.ThreadSafe;

import com.pi4j.io.gpio.*;

public class PIR_sersor {
	GpioController gpio;
	GpioPinDigitalInput pir;
	
	PIR_sersor(GpioController gpio) {
		this.gpio = gpio;
		this.pir = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
	}
	
	public void start() {
		boolean PIR_result = false;	
		PIR_result = pir.isHigh();
		
		if(PIR_result == true) {
			System.out.println("\tSomeone comes here!\n");
		}
		
	}

}
