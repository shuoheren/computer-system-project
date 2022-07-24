import java.util.*;

public class custom {
    
    public static double custom(double[] outcomes, double[] probabilities) { 
        Random RandomGenerator = new Random();
        double rand = RandomGenerator.nextDouble();
        double probSum = probabilities[0];
        for (int z = 0; z < 4; z++) {
            if (rand < probSum) {
                return outcomes[z];
            }
            probSum += probabilities[z+1];
        }
        return outcomes[outcomes.length - 1];    
    }
    
    public static void main(String[] args) {
        double[] outcomes = new double[5];
        double[] probabilities = new double[5];
         
        for (int i = 0; i < args.length - 1; i++) {
            if (i % 2 == 0) {
                outcomes[i/2] = Double.parseDouble(args[i]);
            } else {
                probabilities[i/2] = Double.parseDouble(args[i]);
            }
        }

        for (int k = 0; k < Integer.parseInt(args[args.length - 1]); k++) {
            System.out.println(custom(outcomes, probabilities));
        }
     }
}