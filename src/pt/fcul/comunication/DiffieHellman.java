//https://www.javatpoint.com/diffie-hellman-algorithm-in-java

package pt.fcul.comunication;


public class DiffieHellman {
    public static void exchangeKeys(int clientPublicKeyA, int clientPublicKeyB, int clientPriveteKeyA, int clientPriveteKeyB) {
        long P, G, x, a, y, b, ka, kb;
        System.out.println("Both the users should be agreed upon the public keys G and P");
        
        // take inputs for keys  
        G = clientPublicKeyA;
        P = clientPublicKeyB;
        
        a = clientPriveteKeyA;
        b = clientPriveteKeyB;

        x = calculatePower(G, a, P);
        y = calculatePower(G, b, P);

        ka = calculatePower(y, a, P); //valor privado de A
        kb = calculatePower(x, b, P); //valor privado de A

        System.out.println("Secret key for User1 is:" + ka);
        System.out.println("Secret key for User2 is:" + kb);
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