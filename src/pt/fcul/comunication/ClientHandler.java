package pt.fcul.comunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

//
import pt.fcul.comunication.RSA;


public class ClientHandler implements Runnable {
	private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private Map<String, BigInteger> secretKeys;
	private Socket socket;
	private Server2 server;
	private String userId;
	private PrintWriter writer;
	private RSA rsa;
	
	private Set<String> friends; // Estás a partilhar a lista de amigos entre clientes? Se não onde é que são adicionados os amigos a cada cliente??
	
	public ClientHandler(Socket socket, Server2 server) {
		this.socket = socket;
		this.server = server;
		this.friends = new HashSet<>();
		this.secretKeys = new HashMap<>(); 
	}

	@Override
	public void run() {
		try {
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			
			writer.println("Please enter a username:");
			String userName = reader.readLine();

			server.addClient(this);
			
			keyGen();



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
	
	public void keyGen(){
		rsa = new RSA();
		HashMap<String, Key> keyPair = rsa.generateKeyPair(); // Call generateKeyPair method
		// Retrieve the keys from the HashMap
		privateKey = (PrivateKey) keyPair.get("privateKey");
		publicKey = (PublicKey) keyPair.get("publicKey");
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
					friendHandler.addUserAsFriend(userId);

					
					writer.println("Friend added: " + friendId);
					
					PrintWriter recipientWriter = friendHandler.getWriter();
					
					if (recipientWriter != null) {
						
						 recipientWriter.println(userId + " added you.");
				         recipientWriter.flush();
						
					}
					
					DiffieHellman.getKeys(userId, friendId, server);
					

					//Adicionar o nome do amigo
					// fazer troca de keys ou seja chamar DH
					

					
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
	
	public void addUserAsFriend(String userId) {
		friends.add(userId);
		
	}
	
	private void sendPM(String command) {
		
		String[] splited = command.split(" ", 3);
		if (splited.length == 3) {
			
			String friendId = splited[1];
			String message = splited[2];
			
			if (secretKeys.containsKey(friendId)) {
				
				try {
					String encryptMessage = rsa.encrypt(message, secretKeys.get(friendId));
					
					ClientHandler friendHandler = server.getClientHandler(friendId);
				
					if (friends.contains(friendId)) {
						
						writer.println("You to " + friendId + ": " + message);
			            writer.flush();
						
						if (friendHandler != null) {
							friendHandler.receiveMessage(userId, friendId, encryptMessage);

						}
						
					} else {
						writer.println("User '" + friendId + "' is not your friend.");
						writer.flush();
		            }
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} else {
            writer.println("Invalid command. Usage: /send friendId message");
            writer.flush();
        }
		
	}
	
	private void receiveMessage(String senderId, String friendId, String message) {
		
		try {
			
			ClientHandler friendHandler = server.getClientHandler(friendId);
			
			String decryptMessage = rsa.decrypt(message, friendHandler.getSecretKey(senderId));
			
			PrintWriter recipientWriter = server.getClientHandler(userId).getWriter();
			
			if (recipientWriter != null) {
				
				 recipientWriter.println(senderId + " to You: " + decryptMessage);
		         recipientWriter.flush();
				
			} else {
				System.err.println("Error: Recipient writer not found for user " + userId);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public PrintWriter getWriter() {
        return writer;
    }
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public void addSecretKeys(String userId, BigInteger secretKey) {
		secretKeys.put(userId, secretKey);
	}
	
	public BigInteger getSecretKey(String userId) {
		if (secretKeys.containsKey(userId)) return secretKeys.get(userId);
		return null;
	}
	
}
