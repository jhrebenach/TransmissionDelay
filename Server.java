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
	
	static InputStream fromClient;
	static DataInputStream dis;
	static OutputStream toClient;
	static DataOutputStream dos;
	
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
			
			fromClient = clientSocket.getInputStream();
		    dis = new DataInputStream(fromClient);
		    
			toClient = clientSocket.getOutputStream();
		    dos = new DataOutputStream(toClient);

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

//		    int len = dis.readInt();
//		    byte[] data = new byte[len];
//		    if (len > 0) {
//		        dis.read(data, 0, len);
//		    }
		    
			int bytes_read;
			byte[] reply = new byte[length];

			while ( (bytes_read = fromClient.read(reply)) != -1) {
				toClient.write(reply, 0, bytes_read);
				toClient.flush();
			}
		    
		    // return the bytes to client
//			byte[] byteArray = new byte[length];
//		    dos.writeInt(length);
//		    if (length > 0) {
//		        dos.write(byteArray, 0, length);
//		        dos.flush();
//		    }
		    
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void acceptBytesUDP(int length) {

		try {
			
			byte[] data = new byte[length];
			DatagramPacket receivePacket = new DatagramPacket(data, data.length);
			socketUDP.receive(receivePacket);
			System.out.println("received packet");
			
			byte[] byteArray = new byte[length];
			DatagramPacket packet = new DatagramPacket(
					byteArray, 0, byteArray.length, InetAddress.getByName(clientName), port);
			socketUDP.send(packet);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}


}
