package pt.fcul.comunication;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

import pt.fcul.comunication.Server.ConnectionHandler;






class Friend {
    private String id;
    private String username;

    public Friend(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

public class Client {
	
	
    private ArrayList<ConnectionHandler> channels;
    private ServerSocket server;
    private boolean done;

    public void Communication() {
    	channels = new ArrayList<>();
        done = false;
    }
	
    
    public void startComunication() {

    
    
    
    }
    
    
    
    
  
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader userInput;
    //private ArrayList<Friend> friendList; //lista de amigos 
    private String clientID; //ID do client
    private HashMap<String, String> friendList = new HashMap<String, String>();
    private ExecutorService pool;
    
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));
            //friendList = new ArrayList<>();

            System.out.println("Please enter a username:");
            String username = userInput.readLine();
            out.println(username);

            String receivedID = in.readLine();
            System.out.println("Received ID from server: " + receivedID);
            clientID =receivedID;



            
            String message;
            while ((message = userInput.readLine()) != null) {
                if ("/showid".equals(message)) {
                    System.out.println("Your ID: " + clientID);

                } else if (message.startsWith("/addfriend")) {
                    //EXEMPLO message /addfriend 1211
                    addFriend(message);          
                
                //Acho que n\ao preciso disto, se o server diz que ele tá online. Eles só partilham id/nome depois de def canal seguro.
                } else if (message.startsWith("/friendreq")) {
                    //EXEMPLO message /friendreq 1211 1111
                    reqFriend(message);

                }else if (message.startsWith("/send")) {
                    //EXEMPLO message /send 1211 Ola
                    sendPM(message);
                    
                } else if (message.startsWith("/receive")) {
                    //EXEMPLO input /receive 1211 1111 Ola
                    receivePM(message);
                } else {
                    out.println("please try again another thing");
                }
                



            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    //De fuituro acho que posso apagar isto não devo usar
    public void addFriendToFriendList(String id) {
        friendList.put(id, "" );
    }


    //update friendlist
    public void updateFriendFriendList(String id,String name) {
        friendList.put(id,name);
    }

    
    public void addFriend(String message) {
        String[] msgSplited=message.split(" ",2);
        String type = msgSplited[0];
        String source = clientID;
        String destination = msgSplited[1];
        String newMsg = "/friendreq "+source + " "+destination;
        //envia pedido para o outro adcionar (o serva deve retornar true ou false com base se ele tá ou não connected)
            if (1){
            	//TODO:Questionar o server se o X id tá conectado
            	//TODO:chamar o startComunication(); -> para criar o canal
            	//TODO:chamar o fazer torca de chaves DH 
            	//TODO: Usar O RSA para cifrar/decifrar todas as pm ponto importante
            	//TODO: são trocados os nomes 
            	//TODO: é chamado o reqFriend para o outro me dar add e eu adiciono à minha lista addFriendToFriendList 


                startComunication();





                
            }
            else{
                System.out.println("The user " + destination + " its not connected, try again later or try with another id");
            }

        
    }


    
    public void reqFriend(String message) {
        String[] msgSplited=message.split(" ",3);
        String type = msgSplited[0];
        String source = clientID;
        String destination = msgSplited[1];
        //simplesmente adciona à lista de amigos 
        
    }



    
    public void sendPM(String message) {
        String[] msgSplited=message.split(" ",3);
        String type = msgSplited[0];
        String source = clientID;
        String destination = msgSplited[1];
        String msgConntent= msgSplited[2];
        if(destination=1){
            String newMsg = "/receive "+source + " "+destination+ " "+msgConntent;
            System.out.println("[PM to " + destination + "]: " + msgConntent);
            // Send to the secure channel
        }
        else{
            System.out.println("You are not friends with " + destination);
        }
    
        
    }





    public void receivePM(String message) {
        String[] msgSplited=message.split(" ",4);
        String type = msgSplited[0];
        String source = msgSplited[1];
        String msgConntent= msgSplited[3];
        System.out.println("[PM from " + source + "]: " + msgConntent);
        // Send to the secure channel
    
        
    }





    //esta função valida o id introduzido está na list de amigos 
    private boolean isFriend(String id) {
        return friendList.containsKey(id);
    }



    
    public void shutdown() {
        try {
            if (userInput != null) userInput.close();
            if (in != null) in.close();
            if (out != null) out.close();
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }





    
    
   




    }













}