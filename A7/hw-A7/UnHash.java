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
            str += i;
            if (Hash.hash(str).equals(to_unhash)) {
                return str;
            }
            str = "";
        }
            
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                str += i;
                str += j;
                if (Hash.hash(str).equals(to_unhash)) {
                    return str;
                }
                str = "";
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    str += i;
                    str += j;
                    str += k;
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
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {  
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            str += i;
                            str += j;
                            str += k;
                            str += l;
                            str += m;
                            if (Hash.hash(str).equals(to_unhash)) {
                                return str;
                            }
                            str = "";
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {  
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            for (int n = 0; n < 10; n++) {
                                str += i;
                                str += j;
                                str += k;
                                str += l;
                                str += m;
                                str += n;
                                if (Hash.hash(str).equals(to_unhash)) {
                                    return str;
                                }
                                str = "";
                            }
                        }
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