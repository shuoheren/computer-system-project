import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class Hash {
    public static String hash(String to_hash_input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Static getInstance method is called with hashing MD5
            byte[] messageDigest = md.digest(to_hash_input.getBytes());
            //digest() is called to calculate message digest
            // of an input digest() return array of byte
            BigInteger num = new BigInteger(1, messageDigest);
            //Convert byte array into signum representation
            String hashtext = num.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String args[]) {
        String str = args[0];
        System.out.println(hash(str));
    }
 }