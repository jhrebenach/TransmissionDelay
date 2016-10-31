import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	static Integer port = 10000;
	static DatagramSocket socket;
	static ServerSocket serverSocket;
	static Socket clientSocket;
	static String clientName;
	static DatagramSocket socketUDP;
	
	public static void main(String... args) {
		
		// read server name, catch if there is no specified server
		try {
			clientName = args[0];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// if a port is specified (optional), else default is port 10000
		if (args.length > 1) {
			port = Integer.parseInt(args[1]);
		} 
		
		try {
			// listen on specified port
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();

			acceptBytesTCP(1);
			acceptBytesTCP(10);
			acceptBytesTCP(100);
			acceptBytesTCP(1000);
			acceptBytesTCP(10000);
			
			serverSocket.close();
			clientSocket.close();
			
			
			socketUDP = new DatagramSocket(port);
			
			acceptBytesUDP(1);
			acceptBytesUDP(10);
			acceptBytesUDP(100);
			acceptBytesUDP(1000);
			acceptBytesUDP(10000);
			
			socketUDP.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void acceptBytesTCP(int length) {

		try {
			// accept sent bytes
			final InputStream fromClient = clientSocket.getInputStream();
		    final DataInputStream dis = new DataInputStream(fromClient);

		    int len = dis.readInt();
		    byte[] data = new byte[len];
		    
		    if (len > 0) {
		        dis.read(data, 0, len);
		    }
		    
		    // return the bytes to client
			byte[] byteArray = new byte[length];
			final OutputStream toClient = clientSocket.getOutputStream();
	
		    final DataOutputStream dos = new DataOutputStream(toClient);
		    dos.writeInt(length);
		    
		    if (length > 0) {
		        dos.write(byteArray, 0, length);
		    }
		    
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void acceptBytesUDP(int length) {

		try {
			
			byte[] data = new byte[length];
			DatagramPacket receivePacket = new DatagramPacket(data, data.length);
			socket.receive(receivePacket);
			
			byte[] byteArray = new byte[length];
			DatagramPacket packet = new DatagramPacket(
					byteArray, 0, byteArray.length, InetAddress.getByName(clientName), port);
			socket.send(packet);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}


}
