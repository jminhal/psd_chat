package pt.fcul.comunication;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

import pt.fcul.comunication.RSA;





/**
 * SecureChannel
 */
public class SecureChannel {
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static void main(String[] args) {
        RSA rsa = new RSA(); // Create an instance of the RSA class
        HashMap<String, Key> keyPair = rsa.generateKeyPair(); // Call generateKeyPair method

        // Retrieve the keys from the HashMap
        privateKey = (PrivateKey) keyPair.get("privateKey");
        publicKey = (PublicKey) keyPair.get("publicKey");

        // Print the retrieved keys
        System.out.println("Private Key: " + privateKey);
        System.out.println("Public Key: " + publicKey);
    }
}