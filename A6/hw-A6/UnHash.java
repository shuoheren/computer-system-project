import java.util.*;

public class UnHash {
    public static String unhash(String to_unhash) {
        String str = "";
        String letter = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    str += letter.charAt(i);
                    str += letter.charAt(j);
                    str += letter.charAt(k);
                    if (Hash.hash(str).equals(to_unhash)) {
                        return str;
                    }
                    str = "";
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        str += i;
                        str += j;
                        str += k;
                        str += l;
                        if (Hash.hash(str).equals(to_unhash)) {
                            return str;
                        }
                        str = "";
                    }
                }
            }
        }
        
        return "";
    }
    
    public static void main(String args[]) {
        String str = args[0];
        System.out.println(unhash(str));
    }
}