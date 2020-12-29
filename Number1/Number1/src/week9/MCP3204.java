package week9;

import java.io.IOException;
import com.pi4j.io.spi.*;

// 1. ��������̰� start bit ����
// 2. ��������̰� ��� ADC�� ��� ä�ο��� ������ �������� 4bit�� ����
// 3. ADC�� 1Ŭ�� ���� �����͸� �غ��Ѵ�.
// 4. ADC�� �غ�� �����͸� �����Ѵ�. 


public class MCP3204 {
	public static SpiDevice spi = null;
	public MCP3204() {
		try {
			//SPI ��ü ����
			spi = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);
		}catch (Exception e) {
			System.out.println("Fail to create a SPI instance");
		}		
	}
	
	public static String byteToBinaryString(byte n) {
		// byte�� binary ���� String���� ��ȯ
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
		
		//Start bit, SGL/DIFF bit ����
		sending_data[0] = (byte) (sending_data[0] | 0b11000000);
		
		//adc ä�ο� ����  D1, D0 ǥ��
		if (adcChannel>=2) {
			sending_data[0] = (byte) (sending_data[0] | 0b00010000);
		}
		if (adcChannel%2 == 1){
			sending_data[0] = (byte) (sending_data[0] | 0b00001000);
		}
		

		//�Ʒ����� SPI ��� ����

		//���� �� ����
		receiving_data = spi.write(sending_data);
			
		//������ bytes data ����
		String binaryString =  byteToBinaryString(receiving_data[0])
							   +byteToBinaryString(receiving_data[1])
							   +byteToBinaryString(receiving_data[2]);
			
		//data �κи� ����, Integer ��ȯ
		value =  Integer.parseInt(binaryString.substring(5,19), 2);
		// �������� �������� ġȯ
		
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
