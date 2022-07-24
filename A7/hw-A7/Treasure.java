import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
            
public class Treasure {
    private static String file;
    private static byte[] bytes;
    private static String text;
    private static int size;
    public static int left_offset;
    public static int right_offset;
    public static LinkedList<Integer> q = new LinkedList<Integer>();

    public Treasure(String file_path) {
        file = file_path;
        
        try {
            Path path = Paths.get(file);
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {};
        
        text = new String(bytes);
        size = text.length();
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
    
    public void findTreasure(int offset) {
        String str1 = text.substring(offset, offset+32);
        String str2 = text.substring(offset+32, offset+64);
        String K1_1 = UnHash.unhash(str1); 
        String K1_2 = UnHash.unhash(str2);
        q.add(Integer.parseInt(K1_1));
        q.add(Integer.parseInt(K1_2));
        
        while (true) {
            int new_offset = q.poll();
            
            if (new_offset < size) {
                String newStr1 = text.substring(new_offset, new_offset+32);
                String newStr2 = text.substring(new_offset+32, new_offset+64);
                String left = UnHash.unhash(newStr1);
                String right = UnHash.unhash(newStr2);
                
                if (isInteger(left)) {
                    left_offset = Integer.parseInt(left);    
                } else {
                    if (left.equals("add")) {
                        left_offset = Integer.parseInt(right) + Integer.parseInt(right);
                    } else if (left.equals("mul")) {
                        left_offset = Integer.parseInt(right) * 3;
                    } else if (left.equals("div")) {
                        left_offset = Integer.parseInt(right) / 3;
                    }
                }
                q.add(left_offset);
                
                
                if (isInteger(right)) {
                    right_offset = Integer.parseInt(right);
                } else {
                    if (right.equals("add")) {
                        right_offset = Integer.parseInt(left) + Integer.parseInt(left);
                    } else if (right.equals("mul")) {
                        right_offset = Integer.parseInt(left) * 3;
                    } else if (right.equals("div")) {
                        right_offset = Integer.parseInt(left) / 3;
                    }
                }
                q.add(right_offset);
            
            } else {
                if (new_offset % 2 == 1) {
                    System.out.println(new_offset);
                    return;
                }
            }         
        }
    }

    public static void main(String[] args) {
        int start_offset = Integer.parseInt(args[0]);
        String path_to_file = args[1];
        
        Treasure treasure = new Treasure(path_to_file);
        treasure.findTreasure(start_offset);
    }
}