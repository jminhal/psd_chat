package pt.fcul.comunication;
import pt.fcul.comunication.RSA;





/**
 * SecureChannel
 */
public class SecureChannel {

    public static void main(String[] args) {
        RSA rsa = new RSA();
        
        try{
            String encryptedMessage = rsa.encrypt("Hello World");
            String decryptedMessage = rsa.decrypt(encryptedMessage);
    
            System.err.println("Encrypted:\n"+encryptedMessage);
            System.err.println("Decrypted:\n"+decryptedMessage);
          
         }catch (Exception ingored){}
    
        
    
    }    
} 
