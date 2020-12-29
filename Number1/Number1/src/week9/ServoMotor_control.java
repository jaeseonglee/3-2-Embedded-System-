package week9;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

public class ServoMotor_control {
	public static void main(String [] args) {
		MCP3204 adc = new MCP3204();
		
		GpioController gpio = GpioFactory.getInstance();
		GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_00);  
		pwm.setPwmRange(200);
		
		while(true) {
			try {
				int res = adc.readMCP3204(0);
				// 0 ~ 4000
				// 4 ~ 24
				float mapped_value = ((float)res/4000 *20) + 4;
				pwm.setPwm((int)mapped_value);
				Thread.sleep(500);
				
				
			}catch(Exception e) {
				System.out.println(e);
			}
			
			
		}
		
		
		
	}
}
