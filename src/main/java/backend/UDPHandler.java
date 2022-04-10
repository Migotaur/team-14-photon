package backend;

import java.net.UnknownHostException;
import frontend.ActionDisplay;

public class UDPHandler extends Thread {

	private UDPConnection sendUDPPacketConnection;
	private UDPConnection receiveUDPPacketConnection;
	
	private final int broadcastPort = 7500;
	private final int receivingPort = 7501;
	private final String ipAddress = "127.0.0.1";
	
	private ActionDisplay mainDisplay;
	
	public UDPHandler(ActionDisplay display)
	{
		// Create a thread to listen and handle packets
		try
		{
			this.sendUDPPacketConnection = new UDPConnection(this.broadcastPort, this.ipAddress);
			this.receiveUDPPacketConnection = new UDPConnection(this.receivingPort, this.ipAddress);
			this.mainDisplay = display;
		} catch (UnknownHostException e)
		{
			System.out.println(e);
		}
		
	}
	
	@Override
	public void run() {
		while (true)
		{
			//Receives data as long as CombatClock has not expired
			if(!this.mainDisplay.timeUp()){
				this.receiveUDPPacketConnection.receiveData();
				String packet = this.receiveUDPPacketConnection.getSocketData();
				if (!packet.isEmpty())
				{
					handlePacket(packet);
				}
			}
			else{
				end();
			}
			
			
		}
	}
	
	public void end() {
		this.receiveUDPPacketConnection.closeSocket();
		this.sendUDPPacketConnection.closeSocket();
	}
	
	public void handlePacket(String packetToParse)
	{
		// Parse packet to get ID of player transmitting, ID of player who got tagged
		String [] playerIDs = packetToParse.replace("\"", "").split(":");
		
		
		int playerTransmitting = Integer.parseInt(playerIDs[0]);
		int playerHit = Integer.parseInt(playerIDs[1]);
		this.mainDisplay.handleAction(playerTransmitting, playerHit);
		
		// Send a packet back to the client
		this.sendUDPPacketConnection.sendData(String.format("%d", playerHit));
	}
	
	
}
