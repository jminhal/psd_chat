package pt.fcul.comunication;

//https://www.youtube.com/watch?v=R9eerqP78PE
import java.security.PublicKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class RSA {

    public HashMap<String, Key> generateKeyPair() {
        HashMap<String, Key> keyMap = new HashMap<>();
        
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            // Now you can use privateKey and publicKey as needed
            keyMap.put("privateKey", privateKey);
            keyMap.put("publicKey", publicKey);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        
        return keyMap;
    }

    public String encrypt(String message, BigInteger key) throws Exception {
    	
    	SecretKeySpec secretKeySpec = createSecretKeySpec(key);
    	
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);

    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String decrypt(String encryptedMessage, BigInteger key) throws Exception {
    	
    	SecretKeySpec secretKeySpec = createSecretKeySpec(key);
    	
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }
    
    private static SecretKeySpec createSecretKeySpec(BigInteger secretKey) {
    	  byte[] keyBytes = secretKey.toByteArray();
    	  byte[] validKeyBytes = new byte[16];
    	  System.arraycopy(keyBytes, 0, validKeyBytes, 0, Math.min(keyBytes.length, validKeyBytes.length));
    	  return new SecretKeySpec(validKeyBytes, "AES");
    }
    
}
