import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Treasure {
    private static String file;
    public static byte[] bytes;
    
    public Treasure(String file_path) {
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
        try {
            Path path = Paths.get(file);
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {};
        
        String text = new String(bytes);
     
        String str = text.substring(offset, offset+32);
        String K1 = UnHash.unhash(str); 
        
        System.out.println(Integer.parseInt(K1));
        return Integer.parseInt(K1);
    }
    
    public static void main(String[] args) {
        int start_offset = Integer.parseInt(args[0]);
        String path_to_file = args[1];
        
        Treasure treasure = new Treasure(path_to_file);
        treasure.findTreasure(start_offset);
    }
}