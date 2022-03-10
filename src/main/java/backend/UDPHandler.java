package backend;

import java.net.UnknownHostException;


public class UDPHandler {

	private UDPConnection sendUDPPacketConnection;
	private UDPConnection receiveUDPPacketConnection;
	
	private final int serverReceievPortNumber = 7500;
	private final int serverSendPortNumber = 7501;
	private final String ipAddress = "127.0.0.1";
	
	public UDPHandler() throws UnknownHostException 
	{
		this.sendUDPPacketConnection = new UDPConnection(this.serverSendPortNumber, this.ipAddress);
		this.receiveUDPPacketConnection = new UDPConnection(this.serverReceievPortNumber, this.ipAddress);
	}
	
}
