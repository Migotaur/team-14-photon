package backend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPConnection {

	private int portNumber;
	private InetAddress ipAddress;
	public DatagramSocket socket;
	
	public UDPConnection(int portNumber, String ipAddress) throws UnknownHostException
	{
		this.ipAddress = InetAddress.getByName(ipAddress);
		this.portNumber = portNumber;
		this.socket = createDatagramSocket();
	}
	
	
	public static DatagramSocket createDatagramSocket()
	{
		try {
			return new DatagramSocket();
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
	
	public String receiveData()
	{
		// Create byte array for holding received data
		byte[] receivedData = new byte[1024];
		DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
		try {
			this.socket.receive(receivedPacket);
			return new String(receivedPacket.getData());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
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
}
