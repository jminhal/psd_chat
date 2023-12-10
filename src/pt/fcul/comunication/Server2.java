package pt.fcul.comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server2 {

	private ServerSocket serverSocket;
	private Map<String, ClientHandler> clients;
	
	public Server2() {
		clients = new HashMap<>();
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("Server started. Waiting for clients...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(clientSocket, this);
				new Thread(clientHandler).start();
			}
		}catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public synchronized void addClient(ClientHandler clientHandler) {
		String userId = generateRandomId();
		clients.put(userId, clientHandler);
		clientHandler.setUserId(userId);
		System.out.println("ID: " + userId + " is connected");
	}
	
	private synchronized void removeClient(String userId) {
		clients.remove(userId);
		System.out.println("User " + userId + " disconnected.");
	}
	
	private String generateRandomId() {
		Random random = new Random();
        return String.format("%05d", random.nextInt(100000));
	}
	
	public synchronized ClientHandler getClientHandler(String userId) {
		return clients.get(userId);
	}
	
	public static void main(String[] args) {
		new Server2();
	}
	
}
