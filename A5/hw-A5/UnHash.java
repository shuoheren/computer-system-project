import java.util.*;

public class UnHash {
    static ArrayList<String> arr = new ArrayList<String>();
    
    public static void permute(String str, int start, int end) {
        if (start == end) {
             arr.add(str);
        } else {
            for (int k = start; k <= end; k++) {
                str = swap(str, start, k);
                permute(str, start+1, end);
                str = swap(str, start, k);
            }
        }
    }
    
    public static String swap(String str, int i, int j) {
        char temp;
        char[] charArray = str.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }
    
    public static String unhash(String to_unhash) {
        String str = "";
        String letter = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 26; i++) {
            for (int j = i+1; j < 26; j++) {
                for (int k = j+1; k < 26; k++) {
                    str += letter.charAt(i);
                    str += letter.charAt(j);
                    str += letter.charAt(k);
                    permute(str, 0, 2);
                    str = "";
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = i+1; j < 10; j++) {
                for (int k = j+1; k < 10; k++) {
                    for (int l = k+1; l < 10; l++) {
                        str += i;
                        str += j;
                        str += k;
                        str += l;
                        permute(str, 0, 3);
                        str = "";
                    }
                }
            }
        }
        
        for (int q = 0; q < arr.size(); q++) {
            if (Hash.hash(arr.get(q)).equals(to_unhash)) {
                return arr.get(q);
            }
            arr.remove(q);
        }
        return "";
    }
    
    public static void main(String args[]) {
        String str = args[0];
        System.out.println(unhash(str));
    }
}