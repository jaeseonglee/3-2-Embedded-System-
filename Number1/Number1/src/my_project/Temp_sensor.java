package my_project;

import java.util.List;

import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;
import week6.DHT11;

public class Temp_sensor extends BasicCoapResource{

	private String value = "0";
	DHT11 dht = new DHT11();
	
	private Temp_sensor(String path, String value, CoapMediaType mediaType) {
		super(path, value, mediaType);
	}

	public Temp_sensor() {
		this("/temperature", "0", CoapMediaType.text_plain);
	}

	@Override
	public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
		return get(mediaTypesAccepted);
	}
	
	@Override
	public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
		float[] sensing_data = dht.getTemperature(15);
		this.value = Float.toString(sensing_data[1]);
		return new CoapData(Encoder.StringToByte(this.value), CoapMediaType.text_plain);
	}

	public synchronized void optional_changed() {
		// 온도값
 		// 온도값 변하면 Observe response
		// 안변했으면 , no nothing
		
		float[] sensing_data = dht.getTemperature(15);
		String temp = Float.toString(sensing_data[1]);
		
		if(this.value.equals(temp)) {
			// do nothing
			System.out.println("temperature is not changed.");
		}
		else if(temp.equals("-99.0")) {
			// do nothing
			System.out.println("failed to read temp data.");		
		} else {
			this.value = temp;
			this.changed(this.value);
		}
		System.out.println();
	}
	

	@Override
	public synchronized boolean setValue(byte[] value) {
		this.value = Encoder.ByteToString(value);
		return true;
	}


	@Override
	public synchronized String getResourceType() {
		return "Raspberry pi 4 Temperature Sensor";
	}
}
