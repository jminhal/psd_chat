package pt.fcul.comunication;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    public void run() {
        try {
        	
            server = new ServerSocket(9999);
            System.out.println("Server started. Waiting for clients...");

            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void broadcast(String message) {
    	// corrigir isto, a pm nãod eve ser enviada para todos mas sim para o destino certo
        for (ConnectionHandler ch : connections) {
            ch.sendMessage(message);
        }
    }
    public Map<String, Object> connectionVerify(String id) {
        Map<String, Object> result = new HashMap<>();
        for (ConnectionHandler ch : connections) {
            // Assuming id is a public attribute or has a getter method within ConnectionHandler
            if (ch.getId().equals(id)) { // Replace 'id' with the actual attribute name
                result.put("ClientStatus", true);
                result.put("ClientConnection", ch);
                return result;
            }
        }
        result.put("ClientStatus", false);
        result.put("ClientConnection", null); // Using null to represent no connection found
        return result;
    }

    private void shutdown() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // No need to close connections here
    }

    class ConnectionHandler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                sendID(this);

                String message;
                while ((message = in.readLine()) != null) {
                    if ("/addfriend".startsWith(message)) { 
                    //EXEMPLO message /addfriend 1211 1111
                        String[] msgSplited=message.split(" ",3);
                        String type = msgSplited[0];
                        String source = msgSplited[1];
                        String destination = msgSplited[2];

                        // Enviar esta message para o respetivo id conectado 

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    client.close();
                    connections.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        private void sendID(ConnectionHandler handler) {
            String randomID = generateRandomID();
            handler.sendMessage("Your generated ID: " + randomID);
            System.out.println("ID: " + randomID + " is connected");
        }

        private String generateRandomID() {

            // muda isto para gerar um random com letras grandes e pequenas e numeros 
            // tamanho maximo 5
            Random random = new Random();
            return String.format("%05d", random.nextInt(100000));
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
