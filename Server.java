import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	
	static Integer port = 10000;
	static DatagramSocket socket;
	static Socket clientSocket;
	static String clientName;
	
	public static void main(String... args) {
		
		clientName = args[0];
		port = Integer.parseInt(args[1]);
		
		try {
			// listen on specified port
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();

			System.out.println("RTT for TCP 1 byte : " + sendBytesTCP(1));
			System.out.println("RTT for TCP 10 bytes : " + sendBytesTCP(10));
			System.out.println("RTT for TCP 100 bytes : " + sendBytesTCP(100));
			System.out.println("RTT for TCP 1000 bytes : " + sendBytesTCP(1000));
			System.out.println("RTT for TCP 10000 bytes : " + sendBytesTCP(10000));
			
			
			DatagramSocket socketUDP = new DatagramSocket(port);
			
			System.out.println("RTT for UDP 1 byte : " + sendBytesUDP(1));
			System.out.println("RTT for UDP 10 bytes : " + sendBytesUDP(10));
			System.out.println("RTT for UDP 100 bytes : " + sendBytesUDP(100));
			System.out.println("RTT for UDP 1000 bytes : " + sendBytesUDP(1000));
			System.out.println("RTT for UDP 10000 bytes : " + sendBytesUDP(10000));			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static long sendBytesTCP(int length) {
		
		long startTime = System.currentTimeMillis();
		
		try {
			byte[] byteArray = new byte[length];
			final OutputStream toClient = clientSocket.getOutputStream();
	
		    final DataOutputStream dos = new DataOutputStream(toClient);
		    dos.writeInt(length);
		    
		    if (length > 0) {
		        dos.write(byteArray, 0, length);
		    }
		    
			final InputStream fromClient = clientSocket.getInputStream();
		    final DataInputStream dis = new DataInputStream(fromClient);

		    int len = dis.readInt();
		    byte[] data = new byte[len];
		    
		    if (len > 0) {
		        dis.read(data, 0, len);
		    }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		return endTime - startTime;
	}
	
	
	public static long sendBytesUDP(int length) {
		long startTime = System.currentTimeMillis();
		try {
			byte[] byteArray = new byte[length];
			DatagramPacket packet = new DatagramPacket(
					byteArray, 0, byteArray.length, InetAddress.getByName(clientName), port);
			socket.send(packet);
			
			byte[] data = new byte[length];
			DatagramPacket receivePacket = new DatagramPacket(data, data.length);
			socket.receive(receivePacket);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		return endTime - startTime;
	}
	
	public static long sendBytesToHostTCP(String host) {
		
		int length = 10;
		long startTime = System.currentTimeMillis();
		
		try {
			byte[] byteArray = new byte[length];
			final OutputStream toClient = clientSocket.getOutputStream();
	
		    final DataOutputStream dos = new DataOutputStream(toClient);
		    dos.writeInt(length);
		    
		    if (length > 0) {
		        dos.write(byteArray, 0, length);
		    }
		    
			final InputStream fromClient = clientSocket.getInputStream();
		    final DataInputStream dis = new DataInputStream(fromClient);

		    int len = dis.readInt();
		    byte[] data = new byte[len];
		    
		    if (len > 0) {
		        dis.read(data, 0, len);
		    }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		return endTime - startTime;
	}

}
