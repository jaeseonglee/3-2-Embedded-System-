package original;

import org.ws4d.coap.core.rest.CoapResourceServer;

public class CoAP_Server {
	private static CoAP_Server coapServer;
	private CoapResourceServer resourceServer;
	
	public static void main(String[] args) {
		coapServer = new CoAP_Server();
		coapServer.start();
	}

	public void start() {
		System.out.println("===Run Test Server ===");

		// create server
		if (this.resourceServer != null)	this.resourceServer.stop();
		this.resourceServer = new CoapResourceServer();


		// initialize resource
		LED led = new LED();
		Temp_sensor temp_sensor = new Temp_sensor();
		
		
		// add resource to server
		this.resourceServer.createResource(temp_sensor);
		this.resourceServer.createResource(led);
		
		
		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

