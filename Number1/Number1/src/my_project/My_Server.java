package my_project;

import org.ws4d.coap.core.rest.CoapResourceServer;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class My_Server {
	private static My_Server coapServer;
	private CoapResourceServer resourceServer;
	
	public static void main(String[] args) {
		coapServer = new My_Server();
		coapServer.start();
	}

	public void start() {
		System.out.println("===Run Test Server ===");

		// create server
		if (this.resourceServer != null)	this.resourceServer.stop();
		this.resourceServer = new CoapResourceServer();
		
		// initialize resource	
		GpioController gpio = GpioFactory.getInstance();
		LED led = new LED();
		Temp_sensor temp_sensor = new Temp_sensor();
		temp_sensor.registerServerListener(resourceServer);
		PIR_sersor pir_seosor = new PIR_sersor(gpio);
		
		// add resource to server
		this.resourceServer.createResource(temp_sensor);
		this.resourceServer.createResource(led);
			
		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �µ������� Observe�� Ŭ���̾�Ʈ���� 5�ʿ� �ѹ��� �µ����� ���� ����
		while(true) {
			try {
				System.out.println("========================================================");
				Thread.sleep(5000);
			}catch(Exception e) {
				System.out.println(e);
			}
			temp_sensor.optional_changed();
			pir_seosor.start();
		}
	}
}

