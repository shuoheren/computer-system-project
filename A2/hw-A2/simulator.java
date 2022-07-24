import java.util.*;

public class simulator {
    
    private double lambda;
    private double serviceTime;
    
    public simulator(double lambda, double serviceTime) {
        this.lambda = lambda;
        this.serviceTime = serviceTime;
    }
           
    public void simulate(double time) {
        ArrayList<Double> arr_list = new ArrayList<Double>();
        ArrayList<Double> start_list = new ArrayList<Double>();
        ArrayList<Double> end_list = new ArrayList<Double>();
        
        double miu = 1/this.serviceTime;
        
        double inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
        double service_time = Math.log(1 - Math.random()) / (-miu);
        
        System.out.println("R0" + " ARR: " + inter_arr);
        System.out.println("R0" + " START: " + inter_arr);
        
        double arr = inter_arr;
        double t = arr;
        double start = arr;
        double end = start + service_time;
        arr_list.add(arr);
        start_list.add(start);
        
        int i = 0;
        int j = 0;
            
        inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
        while (t < time) {
            if (arr + inter_arr < end) {
                i++;
                System.out.println("R" + i + " ARR: " + (arr + inter_arr));
                arr_list.add(arr + inter_arr);
                arr = arr + inter_arr;
                
                t = arr;
                start = arr;
                
                inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
            } else {
                System.out.println("R" + j + " DONE: " + end);
                end_list.add(end);
                t = end;
                j++;
                
                if (j <= i) {
                    System.out.println("R" + j + " START: " + t);
                    start = t;
                    start_list.add(start);
                } else {
                    i++;
                    System.out.println("R" + i + " ARR: " + (arr + inter_arr));
                    arr = arr + inter_arr;
                    arr_list.add(arr);
                    System.out.println("R" + i + " START: " + arr);
                    t = arr;
                    start = arr;
                    start_list.add(start);
                    
                    inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
                }            
                
                service_time = Math.log(1 - Math.random()) / (-miu);
                end = start + service_time;
            }                
        }
        
        double sum_inter_arr = 0;
        for (int z = 1; z < arr_list.size(); z++) {
            sum_inter_arr += (arr_list.get(z) - arr_list.get(z-1));
        }
        double avg_inter_arr = sum_inter_arr / (arr_list.size()-1);
        double avg_arr_rate = 1 / avg_inter_arr;
            
        double sum_service_time = 0;
        for (int w = 0; w < end_list.size(); w++) {
            sum_service_time += (end_list.get(w) - start_list.get(w));
        }
        double avg_service_time = sum_service_time / end_list.size();
        
        double util = avg_arr_rate * avg_service_time;        
        System.out.println("UTIL: " + util);
        
        double responseTime = 0;
        for (int y = 0; y < end_list.size(); y++) {
            responseTime += (end_list.get(y) - arr_list.get(y));
        }        
        double avgResponseTime = responseTime / end_list.size();
        
        double q = avg_arr_rate * avgResponseTime;
        double avgQueueLength = q - util;
        
        System.out.println("QLEN: " + avgQueueLength);
        System.out.println("TRESP: " + avgResponseTime);
               
    }
    
    public static void main(String[] args) {
        double time = Double.parseDouble(args[0]);
        double avg_arr_rate = Double.parseDouble(args[1]);
        double avg_ser_time = Double.parseDouble(args[2]);
        
        simulator sim = new simulator(avg_arr_rate, avg_ser_time);
        sim.simulate(time);
    }
}