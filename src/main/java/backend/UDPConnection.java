package backend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UDPConnection {

	private int portNumber;
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private String socketData = "";
	
	public UDPConnection(int portNumber, String ipAddress) throws UnknownHostException
	{
		this.ipAddress = InetAddress.getByName(ipAddress);
		this.portNumber = portNumber;
		this.socket = createDatagramSocket(this.portNumber, this.ipAddress);
	}
	
	
	public static DatagramSocket createDatagramSocket(int portNumber, InetAddress ipAddress)
	{
		try {
			System.out.println(String.format("Bind %s on port %d", ipAddress.toString(), portNumber));
			return new DatagramSocket(portNumber, ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean sendData(String data)
	{
		// Convert string to bytes array
		byte[] dataAsByteArray = data.getBytes();
		
		DatagramPacket packet = new DatagramPacket(dataAsByteArray, dataAsByteArray.length, this.ipAddress, this.portNumber);
		try {
			this.socket.send(packet);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void receiveData()
	{
		System.out.println(String.format("Holding until data is received at %d", this.portNumber));
		// Create byte array for holding received data
		byte[] receivedData = new byte[1024];
		DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
		try {
			this.socket.receive(receivedPacket);
			receivedData = receivedPacket.getData();
			this.socketData = new String(receivedData, 0, receivedPacket.getLength());
		} catch (IOException e) {
			e.printStackTrace();
			this.socketData = "";
		}
		
	}
	
	public int getPort() 
	{
		return this.portNumber;
	}
	
	public String getIPAddress()
	{
		return this.ipAddress.getHostAddress();
	}
	
	public static void main(String[] args)
	{

		
	}
	
	public String getSocketData()
	{
		return this.socketData;
	}
	
	public void closeSocket()
	{
			this.socket.close();
	}
}
