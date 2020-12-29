package week9;


import java.util.*;
import com.pi4j.io.gpio.*;

public class ServoMotor_basic {
	public static void main(String[] args){
		  GpioController gpio = GpioFactory.getInstance();
		  GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_00);
		  
		  pwm.setPwmRange(200);// 100µs=1, 20ms(20,000µs)=200
		  		  
		  //1ms -> -90 degree, 1.5ms -> 0 degree, 2ms -> 90degree
		  //1ms(1000µs)=10, 1.5ms(1500µs)=15, 2ms(2000µs)=20
		  
		  //?���?�? ?��?��?�� ?��?�� ?��?��?�� ?��??�? ?�� 값이 ?��?��?���? ?��?���? 말함.
		  //?��른쪽 2.4ms, 중간  1.4ms, ?���? 0.4ms�? 
		  //4~24
		  try {
			  pwm.setPwm(24); // right
			  Thread.sleep(1000);
			  pwm.setPwm(14); // center
			  Thread.sleep(1000);
			  pwm.setPwm(4); // left
			  Thread.sleep(1000);
			  pwm.setPwm(14); // center
			  Thread.sleep(1000);
		  }
		  catch (Exception e) {
			// TODO: handle exception
			  System.out.println(e);
		  }
	}
}
