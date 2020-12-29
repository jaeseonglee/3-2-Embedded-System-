package week9;


import java.util.*;
import com.pi4j.io.gpio.*;

public class ServoMotor_basic {
	public static void main(String[] args){
		  GpioController gpio = GpioFactory.getInstance();
		  GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_00);
		  
		  pwm.setPwmRange(200);// 100Âµs=1, 20ms(20,000Âµs)=200
		  		  
		  //1ms -> -90 degree, 1.5ms -> 0 degree, 2ms -> 90degree
		  //1ms(1000Âµs)=10, 1.5ms(1500Âµs)=15, 2ms(2000Âµs)=20
		  
		  //?•˜ì§?ë§? ?‚¬?š©? ?¬?Ÿ¼ ?‹¤?ˆ˜?˜ ?œ ??ê°? ?œ„ ê°’ì´ ? •?™•?•˜ì§? ?•Š?‹¤ê³? ë§í•¨.
		  //?˜¤ë¥¸ìª½ 2.4ms, ì¤‘ê°„  1.4ms, ?™¼ìª? 0.4msê°? 
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
