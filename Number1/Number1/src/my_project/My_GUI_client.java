package my_project;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

public class My_GUI_client extends JFrame implements CoapClient{
	private static final boolean exitAfterResponse = false;
	
	JButton btn_pass = new JButton("PASS");
	JButton btn_stop = new JButton("STOP!");
	JButton btn_off = new JButton("OFF");
		
	CoapClientChannel clientChannel = null;

	public My_GUI_client (String serverAddress, int serverPort) {
		//제목 설정
		super("my_project");
		//레이아웃 설정
		this.setLayout(null);
		String sAddress = serverAddress;
		int sPort = serverPort;

		CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();

		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(sAddress), sPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		if (null == clientChannel) {
			return;
		}
	
		btn_pass.setBounds(30, 40, 100, 50);
		btn_stop.setBounds(150, 40, 100, 50);
		btn_off.setBounds(270, 40, 100, 50);

		btn_pass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/led", true);
				request.setPayload(new CoapData("green", CoapMediaType.text_plain));
				clientChannel.sendMessage(request);
			}
		});
		btn_stop.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/led", true);
				request.setPayload(new CoapData("red", CoapMediaType.text_plain));
				clientChannel.sendMessage(request);
			}
		});
		btn_off.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/led", true);
				request.setPayload(new CoapData("off", CoapMediaType.text_plain));
				clientChannel.sendMessage(request);
			}
		});

		this.add(btn_pass);
		this.add(btn_stop);
		this.add(btn_off);
		
		//프레임 크기 지정	
		this.setSize(450, 200);

		//프레임 보이기
		this.setVisible(true);

		//swing에만 있는 X버튼 클릭시 종료
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
		System.exit(-1);
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		if (My_GUI_client.exitAfterResponse) {
			System.exit(0);
		}
	}
	
	@Override
	public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args){
		//프레임 열기
		My_GUI_client gui = new My_GUI_client("192.168.0.6", CoapConstants.COAP_DEFAULT_PORT);
	}
}