//https://www.javatpoint.com/diffie-hellman-algorithm-in-java

package pt.fcul.comunication;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellman {
    
    public static void getKeys(String userId1, String userId2, Server2 server) {
    	
    	ClientHandler user1Handler = server.getClientHandler(userId1);
    	ClientHandler user2Handler = server.getClientHandler(userId2);
    	
    	RSAPublicKey publicKeyUser1 = (RSAPublicKey) user1Handler.getPublicKey();
    	RSAPrivateKey privateKeyUser1 = (RSAPrivateKey) user1Handler.getPrivateKey();
    	RSAPublicKey publicKeyUser2 = (RSAPublicKey) user2Handler.getPublicKey();
    	RSAPrivateKey privateKeyUser2 = (RSAPrivateKey) user2Handler.getPrivateKey();	
    	
    	long P, G, x, a, y, b, ka, kb;
    	G = publicKeyUser1.getModulus().longValue();
        P = publicKeyUser2.getModulus().longValue();
        
        a = privateKeyUser1.getModulus().longValue();
        b = privateKeyUser2.getModulus().longValue();

        x = calculatePower(G, a, P);
        y = calculatePower(G, b, P);

        ka = calculatePower(y, a, P);
        kb = calculatePower(x, b, P);
        
        BigInteger bigIntegerKa = new BigInteger(Long.toString(ka));
        BigInteger bigIntegerKb = new BigInteger(Long.toString(kb));
    	
    	user1Handler.addSecretKeys(userId2, bigIntegerKa);
    	user2Handler.addSecretKeys(userId1, bigIntegerKb);
    	
    }

    private static long calculatePower(long x, long y, long P) {
        long result = 0;
        if (y == 1) {
            return x;
        } else {
            result = ((long) Math.pow(x, y)) % P;
            return result;
        }
    }
    
    
    
}