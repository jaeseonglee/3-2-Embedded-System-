package week9;

import java.io.IOException;
import com.pi4j.io.spi.*;

// 1. 라즈베리파이가 start bit 전송
// 2. 라즈베리파이가 어느 ADC의 어느 채널에사 정보를 받을껀지 4bit로 전송
// 3. ADC가 1클락 높인 데이터를 준비한다.
// 4. ADC가 준비된 데이터를 전송한다. 


public class MCP3204 {
	public static SpiDevice spi = null;
	public MCP3204() {
		try {
			//SPI 객체 선언
			spi = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);
		}catch (Exception e) {
			System.out.println("Fail to create a SPI instance");
		}		
	}
	
	public static String byteToBinaryString(byte n) {
		// byte의 binary 값을 String으로 반환
	    StringBuilder sb = new StringBuilder("00000000");
	    for (int bit = 0; bit < 8; bit++) {
	        if (((n >> bit) & 1) > 0) {
	            sb.setCharAt(7 - bit, '1');
	        }
	    }
	    return sb.toString();
	}
	
	public int readMCP3204(int adcChannel) throws IOException {
		byte[] sending_data = {0, 0, 0};
		byte[] receiving_data;
		int value = -1;
		
		//Start bit, SGL/DIFF bit 설정
		sending_data[0] = (byte) (sending_data[0] | 0b11000000);
		
		//adc 채널에 따른  D1, D0 표시
		if (adcChannel>=2) {
			sending_data[0] = (byte) (sending_data[0] | 0b00010000);
		}
		if (adcChannel%2 == 1){
			sending_data[0] = (byte) (sending_data[0] | 0b00001000);
		}
		

		//아래부터 SPI 통신 시작

		//전송 및 수신
		receiving_data = spi.write(sending_data);
			
		//수신한 bytes data 병합
		String binaryString =  byteToBinaryString(receiving_data[0])
							   +byteToBinaryString(receiving_data[1])
							   +byteToBinaryString(receiving_data[2]);
			
		//data 부분만 추출, Integer 변환
		value =  Integer.parseInt(binaryString.substring(5,19), 2);
		// 이진수를 십진수로 치환
		
		return value;
	}
/*
	public static void main(String[] args) {
		MCP3204 obj = new MCP3204();
		while(true) {
			try {
				int value = obj.readMCP3204(0);
				System.out.println(value);
				Thread.sleep(1000);
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}*/
}
