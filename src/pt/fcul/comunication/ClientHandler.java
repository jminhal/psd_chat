package pt.fcul.comunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
//
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;



public class ClientHandler implements Runnable {
	
	private Socket socket;
	private Server2 server;
	private String userId;
	private PrintWriter writer;
	
	private Set<String> friends; // Estás a partilhar a lista de amigos entre clientes? Se não onde é que são adicionados os amigos a cada cliente??
	
	public ClientHandler(Socket socket, Server2 server) {
		this.socket = socket;
		this.server = server;
		this.friends = new HashSet<>();
	}

	@Override
	public void run() {
		try {
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			
			writer.println("Please enter a username:");
			String userName = reader.readLine();
			RSA rsa = new RSA();

			server.addClient(this);
			
			String input;
			while ((input = reader.readLine()) != null) {
				processCommand(input);
			}
			
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	private void processCommand(String command) {
		if (command.startsWith("/addfriend")) {
			addFriend(command);
		}
		else if (command.startsWith("/send")) {
			sendPM(command);
		}
		else{
			writer.println("Please use one of the following commands: '/addfriend <ID_FRIEND>' or '/send <ID_FRIEND> <YOUR_MSG>'");
		}
	}
	
	private void addFriend(String command) {
		
		String[] splited = command.split(" ", 2);
		if (splited.length == 2) {
			
			String friendId = splited[1];
			ClientHandler friendHandler = server.getClientHandler(friendId);
			if (friendHandler != null) {
				
				if (!friends.contains(friendId)) {
					
					friends.add(friendId);
					
					writer.println("Friend added: " + friendId);
					

					
				} else {
					writer.println("User " + friendId + " is already your friend.");
				}
				
				
			} else {
				writer.println("User not found: " + friendId);
			}
			
		} else {
            writer.println("Invalid command. Usage: /addfriend userId");
        }
	}
	
	private void sendPM(String command) {
		
		String[] splited = command.split(" ", 3);
		if (splited.length == 3) {
			
			String friendId = splited[1];
			String message = splited[2];
			
			if (friends.contains(friendId)) {
				
				writer.println("You to " + friendId + ": " + message);
	            writer.flush();
				
				ClientHandler friendHandler = server.getClientHandler(friendId);
				if (friendHandler != null) {
					//friendHandler.sendMessage(friendId, message);
					friendHandler.receiveMessage(userId, message);
				}
				
			} else {
				writer.println("User '" + friendId + "' is not your friend.");
				writer.flush();
            }
			
		} else {
            writer.println("Invalid command. Usage: /send friendId message");
            writer.flush();
        }
		
	}
	
	private void sendMessage(String friendId, String message) {
		writer.println("You: " + message);
		
		ClientHandler friendHandler = server.getClientHandler(friendId);
		if (friendHandler != null) {
			friendHandler.receiveMessage(userId, message); 
		} else {
			writer.println("Friend not found: " + friendId);
		}
	}
	
	private void receiveMessage(String senderId, String message) {
		//writer.println(senderId + ": " + message);
		
		PrintWriter recipientWriter = server.getClientHandler(userId).getWriter();
		
		if (recipientWriter != null) {
			
			 recipientWriter.println(senderId + " to You: " + message);
	         recipientWriter.flush();
			
		} else {
			System.err.println("Error: Recipient writer not found for user " + userId);
		}
		
	}
	
	public PrintWriter getWriter() {
        return writer;
    }
	
}
