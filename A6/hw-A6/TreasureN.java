import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TreasureN {
    private static String file;
    public static byte[] bytes;
    
    public TreasureN(String file_path) {
        file = file_path;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        return true;
}
    
    public int findTreasure(int offset) {
        int new_offset = offset;
        int last_one_offset = offset;
        int last_two_offset = offset;
        String K1 = "";
        
        try {
            Path path = Paths.get(file);
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {};
        
        String text = new String(bytes);
        int size = text.length();
     
        while (new_offset+32 < size) {
            String str = text.substring(new_offset, new_offset+32);
            K1 = UnHash.unhash(str);
            
            if (isInteger(K1)) {
                new_offset = last_one_offset + Integer.parseInt(K1);
                
            } else {
                if (K1.equals("add")) {
                    new_offset = last_one_offset + last_two_offset;
                } else if (K1.equals("sub")) {
                    new_offset = last_one_offset - last_two_offset;
                } else if (K1.equals("div")) {
                    new_offset = last_one_offset / last_two_offset;
                } else if (K1.equals("mul")) {
                    new_offset = last_one_offset * last_two_offset;
                }
            }
            last_two_offset = last_one_offset;
            last_one_offset = new_offset;
        }
        
        System.out.println(K1);
        return Integer.parseInt(K1);
    }
    
    public static void main(String[] args) {
        int start_offset = Integer.parseInt(args[0]);
        String path_to_file = args[1];
        
        TreasureN treasureN = new TreasureN(path_to_file);
        treasureN.findTreasure(start_offset);
    }
}