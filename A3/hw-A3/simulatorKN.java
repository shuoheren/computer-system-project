import java.util.*;

public class simulatorKN {
        
    private double lambda;
    private double serviceTime;
    private int maxLength;
    private int numServers;
    
    public simulatorKN(double lambda, double serviceTime, int maxLength, int numServers) {
        this.lambda = lambda;
        this.serviceTime = serviceTime;
        this.maxLength = maxLength;
        this.numServers = numServers;
    }
    
    public Object getKeyFromValue(HashMap hm, Object value) {
        for (Object ob : hm.keySet()) {
            if (hm.get(ob).equals(value)) {
                return ob;
            }
        }
        return null;
    }
    
    public static int factorial(int n) {
        if (n == 0)
            return 1;
        return n * factorial(n - 1);
    }
    
    public double summation(double input) {
        double result = 0;
        for (int r = 0; r < this.numServers; r++) {
            result += ((Math.pow(input, r)) / (factorial(r)));
        }
        return result;
    }
    
    public static int randomServer(ArrayList<String> serverIndex) {
        Random rand = new Random();
        int x = rand.nextInt(serverIndex.size());
        String s = serverIndex.get(x);
        return Integer.parseInt(s);
    }

    public void simulate(double time) {
        ArrayList<Double> arr_list = new ArrayList<Double>();
        ArrayList<Integer> k_list = new ArrayList<Integer>();
        ArrayList<String> serverEmpty = new ArrayList<String>();
        ArrayList<ArrayList<Double>> Ts = new ArrayList<ArrayList<Double>>();
        
        for (int p = 0; p < this.numServers; p++) {
            Ts.add(p, new ArrayList<Double>());
            serverEmpty.add("" + p);
        }

        HashMap<Integer, Integer> servers = new HashMap<Integer, Integer>();
        HashMap<Integer, Double> corr_end = new HashMap<Integer, Double>();
        HashMap<Integer, Double> corr_start = new HashMap<Integer, Double>();
        
        double miu = 1/this.serviceTime;
        
        double inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
        double service_time = Math.log(1 - Math.random()) / (-miu);
        
        System.out.println("R0" + " ARR: " + inter_arr);
        System.out.println("R0" + " START 0: " + inter_arr);
        servers.put(0, 0); 
        serverEmpty.remove("0");
        
        double arr = inter_arr;
        double t = arr;
        double start = arr;
        double end = start + service_time;
        corr_start.put(0, start);
        corr_end.put(0, end);
          
        int i = 0;
        int j = 0;
        int k = 0;
        int q = 1;
        int num = 0;
        int x = 0;
        int w = 0;
        
        double min_end = 0;
        
        inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
        while (t < time) {
            if (corr_end.size() != 0) {
                min_end = Collections.min(corr_end.values());
            }
            double newArr = arr + inter_arr;
            if (newArr < min_end || corr_end.size() == 0) {
                i++; 
                if (serverEmpty.size() != 0 && w == 0) {
                    System.out.println("R" + i + " ARR: " + newArr);
                    arr = newArr;
                    q++;                
                    t = arr;
                    
                    x = randomServer(serverEmpty);
                    System.out.println("R" + i + " START " + x + ": " + t);
                    start = t;
                    corr_start.put(i, start);
                    serverEmpty.remove("" + x);
                    servers.put(i, x);
                    
                    service_time = Math.log(1 - Math.random()) / (-miu);
                    end = start + service_time;
                    corr_end.put(i, end); 
                    
                } else {      
                    if (q == this.maxLength) {
                        System.out.println("R" + i + " DROP: " + newArr);
                        arr = newArr;
                        t = arr;
                        k = i;
                        k_list.add(k);
                        num++;
                    
                    } else {    
                        System.out.println("R" + i + " ARR: " + newArr);
                        arr = newArr;
                        q++;                
                        t = arr;
                        w++;
                    }
                }
                
                inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
      
            } else {
                j = (Integer) getKeyFromValue(corr_end, min_end);
                Ts.get(servers.get(j)).add(min_end - corr_start.get(j));
                System.out.println("R" + j + " DONE " + servers.get(j) + ": " + min_end);
                t = min_end;
                serverEmpty.add("" + servers.get(j));
                corr_end.remove(j);
                j++;
                q--;  
                                       
                while (k_list.contains(j) || servers.containsKey(j)) {
                    j++;
                }
                
                if (j <= i && !servers.containsKey(j)) {
                    if (serverEmpty.size() != 0) {
                        x = randomServer(serverEmpty);
                        System.out.println("R" + j + " START " + x + ": " + t);
                        start = t;
                        corr_start.put(j, start);
                        serverEmpty.remove("" + x);
                        servers.put(j, x);
                        w--;
                        
                        service_time = Math.log(1 - Math.random()) / (-miu);
                        end = start + service_time;
                        corr_end.put(j, end);
                    }
                    
                } else {
                    if (corr_end.size() != 0) {
                        min_end = Collections.min(corr_end.values());
                    
                        if (min_end > newArr) {
                            i++;  
                            System.out.println("R" + i + " ARR: " + newArr);
                            arr = newArr;
                            q++; 
                            t = arr;
                            w++;
                            
                            if (serverEmpty.size() != 0) {
                                x = randomServer(serverEmpty);
                                System.out.println("R" + i + " START " + x + ": " + t);
                                start = t; 
                                corr_start.put(i, start);
                                serverEmpty.remove("" + x);
                                servers.put(i, x);
                                w--;
                                
                                service_time = Math.log(1 - Math.random()) / (-miu);
                                end = start + service_time;
                                corr_end.put(i, end);
                            }
                            inter_arr = Math.log(1 - Math.random()) / (-this.lambda);
                        }
                    }
                }           
            }
        }                                   
        
        for (int e = 0; e < this.numServers; e++) {
            double sum_service_time = 0;
            for (int b = 0; b < Ts.get(e).size(); b++) {
                sum_service_time += Ts.get(e).get(b);
            }
            double avg_service_time = sum_service_time / Ts.get(e).size();   
            double util = (this.lambda * avg_service_time) / this.numServers;  
            System.out.println("UTIL" + e + ": " + util);
        }
        
        double rho = (this.lambda * (1/miu)) / this.numServers;      
        double K = 1 - ((Math.pow((this.numServers * rho), this.numServers) / factorial(this.numServers)) 
                            / (summation(this.numServers * rho)));        
        double C = (1 - K) / ((1 - rho * K));
        
        double avgQueueLength = (this.numServers * rho) + ((rho / (1 - rho)) * C);
             
        double avgResponseTime = (C/this.numServers) * ((1/miu)/(1-rho)) + (1/miu);
        
        System.out.println("QLEN: " + avgQueueLength);
        System.out.println("TRESP: " + avgResponseTime);
        System.out.println("DROPPED: " + num);
    }
        
    public static void main(String[] args) {
        double time = Double.parseDouble(args[0]);
        double avg_arr_rate = Double.parseDouble(args[1]);
        double avg_ser_time = Double.parseDouble(args[2]);
        int max_que_length = Integer.parseInt(args[3]);
        int num_servers = Integer.parseInt(args[4]);
        
        simulatorKN sim = new simulatorKN(avg_arr_rate, avg_ser_time, max_que_length, num_servers);
        sim.simulate(time);
    }
}