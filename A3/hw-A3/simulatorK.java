import java.util.*;

public class simulatorK {
        
    private double lambda;
    private double serviceTime;
    private int maxLength;
    
    public simulatorK(double lambda, double serviceTime, int maxLength) {
        this.lambda = lambda;
        this.serviceTime = serviceTime;
        this.maxLength = maxLength;
    }
           
    public void simulate(double time) {
        ArrayList<Double> arr_list = new ArrayList<Double>();
        ArrayList<Double> start_list = new ArrayList<Double>();
        ArrayList<Double> end_list = new ArrayList<Double>();
        ArrayList<Integer> k_list = new ArrayList<Integer>();
        
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
        int k = 0;
        int q = 1;
        int s = 1;
        int num = 0;
              
        inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
        while (t < time) {
            if (arr + inter_arr < end) {
                i++;
                if (q == this.maxLength) {
                    System.out.println("R" + i + " DROP: " + (arr + inter_arr));
                    arr = arr + inter_arr;
                    t = arr;
                    k = i;
                    k_list.add(k);
                    num++;
                } else {
                    System.out.println("R" + i + " ARR: " + (arr + inter_arr));
                    arr = arr + inter_arr;
                    arr_list.add(arr);
                    q++;
                    t = arr;
                    start = t;
                    
                    if (s == 0) {
                        System.out.println("R" + i + "START: " + t);
                        start = t;
                        start_list.add(start);
                        s++;
                    }
                }
                
                inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
                
            } else {
                System.out.println("R" + j + " DONE: " + end);
                end_list.add(end);
                t = end;
                j++;
                q--;
                s--;
                                       
                while (k_list.contains(j) && j <= i) {
                    j++;
                }
                    
                if (j <= i) {
                    System.out.println("R" + j + " START: " + t);
                    start = t;
                    start_list.add(start);
                    s++;
                    
                } else {
                    i++;
       
                    System.out.println("R" + i + " ARR: " + (arr + inter_arr));
                    arr = arr + inter_arr;
                    arr_list.add(arr);
                    q++; 
                    t = arr;
                    
                    System.out.println("R" + i + " START: " + t);
                    start = t;
                    start_list.add(start);    
                    s++;
                    
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
        for (int r = 0; r < end_list.size(); r++) {
            sum_service_time += (end_list.get(r) - start_list.get(r));
        }
        double avg_service_time = sum_service_time / end_list.size();
        
        double util = avg_arr_rate * avg_service_time;        
        System.out.println("UTIL: " + util);
        
        double responseTime = 0;
        for (int y = 0; y < end_list.size(); y++) {
            responseTime += (end_list.get(y) - arr_list.get(y));
        }        
        double avgResponseTime = responseTime / end_list.size();
        
        util = this.lambda * this.serviceTime;
        double avgQueueLength = (util/(1-util)) - (((this.maxLength+1)*Math.pow(util, this.maxLength+1))
                                                              / (1 - Math.pow(util, this.maxLength+1)));
        
        System.out.println("QLEN: " + avgQueueLength);
        System.out.println("TRESP: " + avgResponseTime);
        System.out.println("DROPPED: " + num);
    }
        
    public static void main(String[] args) {
        double time = Double.parseDouble(args[0]);
        double avg_arr_rate = Double.parseDouble(args[1]);
        double avg_ser_time = Double.parseDouble(args[2]);
        int max_que_length = Integer.parseInt(args[3]);
        
        simulatorK sim = new simulatorK(avg_arr_rate, avg_ser_time, max_que_length);
        sim.simulate(time);
    }
}