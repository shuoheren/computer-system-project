import java.lang.Math;

public class exp {
    
    public static double exp(double lambda) {
        if (lambda <= 0) {
            throw new IllegalArgumentException("Invalid lambda");
        }
        return Math.log(1 - Math.random()) / (-lambda);
    }

    public static void main(String[] args) {
        double lambda = Double.parseDouble(args[0]);
        int n = Integer.parseInt(args[1]);
        
        for (int i = 0; i < n; i++) {
            System.out.println(exp(lambda));
        }
    }  
    
}
        

    
    