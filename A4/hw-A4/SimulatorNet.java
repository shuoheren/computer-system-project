import java.util.*;

public class SimulatorNet {
    
    private double lambda;
    static Double[] doneTime = new Double[4];
    static HashMap<Integer, Double[]> cTime = new HashMap<Integer, Double[]>();
    static ArrayList<Double> Ts_CPU = new ArrayList<Double>();
    static ArrayList<Double> Ts_Disk = new ArrayList<Double>();
    static ArrayList<Double> Ts_Network = new ArrayList<Double>();

    public SimulatorNet(double lambda) {
        this.lambda = lambda;
    }
    
    public static double generator(double x) {
        return Math.log(1 - Math.random()) / (-x);
    }
    
    public Object getKeyFromValue(HashMap hm, Object value) {
        for (Object ob : hm.keySet()) {
            if (hm.get(ob).equals(value)) {
                return ob;
            }
        }
        return null;
    }
    
    public double done_time(int i, double t) {
        double ser_time_CPU = 15;
        double miu_CPU = 1/ser_time_CPU;
        double ser_time_Disk = 60;
        double miu_Disk = 1/ser_time_Disk;
        double ser_time_Network = 85;
        double miu_Network = 1/ser_time_Network;
   
        for (int h = 0; h < 4; h++) {
            doneTime[h] = t;
        }
        cTime.put(i, doneTime);
        
        while (true) {
            double service_time = generator(miu_CPU); 
            Ts_CPU.add(service_time);
            t += service_time;
            doneTime[0] = t; // CPU done的时间                     
            double rand = Math.random();       
            while (rand <= 0.2) {
                while (true) {
                    if (t < cTime.get(i-1)[1]) {
                        t = cTime.get(i-1)[1];
                    }
                    service_time = generator(miu_Disk);
                    Ts_Disk.add(service_time);
                    t += service_time;
                    doneTime[1] = t;  // Disk done的时间
                    rand = Math.random();
                    if (rand <= 0.7) {
                        if (t < cTime.get(i-1)[2]) {
                            t = cTime.get(i-1)[2];
                        }
                        service_time = generator(miu_Network);
                        Ts_Network.add(service_time);
                        t += service_time;
                        doneTime[2] = t; // Network done的时间
                        rand = Math.random();
                        if (rand > 0.4 && rand <= 1.0) {   
                            break;
                        }
                    } else {                 
                        break;
                    }
                }
                if (t < cTime.get(i-1)[3]) {
                    t = cTime.get(i-1)[3];
                }
                service_time = generator(miu_CPU);
                Ts_CPU.add(service_time);
                t += service_time;
                doneTime[3] = t;  // finally done time
                rand = Math.random();
            }
            return t;
        }
    }
                           
    public void simulate_p(double time) {
        HashMap<Integer, Double> corr_end = new HashMap<Integer, Double>();    
        
        cTime.put(-1, doneTime);        
        double inter_arr = generator(this.lambda);      
        double newArr = inter_arr;
        double arr = newArr;
        double t = arr;
        double start = t; 
        double end = done_time(0, t);
        corr_end.put(0, end);
        
        System.out.println("R0" + " ARR: " + t);
        System.out.println("R0" + " START: " + t);   
       
        int i = 0;
        int j = 0;
        int w = 0; // number of arrivals at the current time
        double min_end = 0;
        
        inter_arr = generator(this.lambda);
        newArr = arr + inter_arr; 
        while (t < time) {
            if (corr_end.size() != 0) {
                min_end = Collections.min(corr_end.values());
                
                if (min_end < newArr) {
                    int k = (Integer) getKeyFromValue(corr_end, min_end);
                    System.out.println("R" + k + " DONE: " + min_end);
                    t = min_end;
                    corr_end.remove(k);
                }
            }
            
            if (newArr >= cTime.get(j)[0] && w == 0) {
                i++;
                System.out.println("R" + i + " ARR: " + newArr);
                arr = newArr;                
                t = arr;
                
                System.out.println("R" + i + " START: " + t);
                start = t;
                end = done_time(i, t);
                corr_end.put(i, end);
                
                inter_arr = generator(this.lambda);
                newArr = arr + inter_arr;
                
            } else if (newArr >= cTime.get(j)[0] && w > 0) {
                j++;
                System.out.println("R" + j + " START: " + t);
                start = t;
                end = done_time(j, t);
                corr_end.put(j, end);
                w--;
                
            } else {
                i++;
                System.out.println("R" + i + " ARR: " + newArr);
                arr = newArr;                
                t = arr;
                w++;
                
                inter_arr = 1/this.lambda;
                newArr = arr + inter_arr;
            }
        }
    }
    
    public void simulate_c(double time) {
        HashMap<Integer, Double> corr_end = new HashMap<Integer, Double>();    
        
        cTime.put(-1, doneTime);        
        double inter_arr = 1/this.lambda;      
        double newArr = inter_arr;
        double arr = newArr;
        double t = arr;
        double start = t; 
        double end = done_time(0, t);
        corr_end.put(0, end);
        
        System.out.println("R0" + " ARR: " + t);
        System.out.println("R0" + " START: " + t);   
       
        int i = 0;
        int j = 0;
        int w = 0; // number of arrivals at the current time
        double min_end = 0;
        
        inter_arr = 1/this.lambda;
        newArr = arr + inter_arr; 
        while (t < time) {
            if (corr_end.size() != 0) {
                min_end = Collections.min(corr_end.values());
                
                if (min_end < newArr) {
                    int k = (Integer) getKeyFromValue(corr_end, min_end);
                    System.out.println("R" + k + " DONE: " + min_end);
                    t = min_end;
                    corr_end.remove(k);
                }
            }
            
            if (newArr >= cTime.get(j)[0] && w == 0) {
                i++;
                System.out.println("R" + i + " ARR: " + newArr);
                arr = newArr;                
                t = arr;
                
                System.out.println("R" + i + " START: " + t);
                start = t;
                end = done_time(i, t);
                corr_end.put(i, end);
                
                inter_arr = 1/this.lambda;
                newArr = arr + inter_arr;
                
            } else if (newArr >= cTime.get(j)[0] && w > 0) {
                j++;
                System.out.println("R" + j + " START: " + t);
                start = t;
                end = done_time(j, t);
                corr_end.put(j, end);
                w--;
                
            } else {
                i++;
                System.out.println("R" + i + " ARR: " + newArr);
                arr = newArr;                
                t = arr;
                w++;
                
                inter_arr = 1/this.lambda;
                newArr = arr + inter_arr;
            }             
        }
    }
    
    public static void main(String[] args) { 
        for (int z = 5; z < 55; z += 5) {
            double time = Double.parseDouble(args[1]);    
            SimulatorNet sim = new SimulatorNet(z);
            
            if (args[0].equals("p")) {
                sim.simulate_p(time);
            }
            
            if (args[0].equals("c")) {
                sim.simulate_c(time);
            }
        }
        
        double sum_Ts_CPU = 0;
        for (int w = 0; w < Ts_CPU.size(); w++) {
            sum_Ts_CPU += Ts_CPU.get(w);
        }
        double avg_Ts_CPU = sum_Ts_CPU / Ts_CPU.size();
        
        double sum_Ts_Disk = 0;
        for (int r = 0; r < Ts_Disk.size(); r++) {
            sum_Ts_Disk += Ts_Disk.get(r);
        }
        double avg_Ts_Disk = sum_Ts_Disk / Ts_Disk.size();
        
        double sum_Ts_Network = 0;
        for (int q = 0; q < Ts_Network.size(); q++) {
            sum_Ts_Network += Ts_Network.get(q);
        }
        double avg_Ts_Network = sum_Ts_Network / Ts_Network.size();
        
        for (int y = 5; y < 55; y += 5) {
            double lambda_CPU = y / 0.8;
            double lambda_Disk = 25/72 * y;
            double lambda_Network = 35/144 * y;
            
            double rho_CPU = lambda_CPU * (avg_Ts_CPU/1000);
            double qCPU = rho_CPU / (1 - rho_CPU);
            
            double rho_Disk = lambda_Disk * (avg_Ts_Disk/1000);
            double qDisk = rho_Disk / (1 - rho_Disk);
            
            double rho_Network = lambda_Network * (avg_Ts_Network/1000);
            double qNetwork = rho_Network / (1 - rho_Network);
            
            double qTotal = qCPU + qDisk + qNetwork;
            double Tq = qTotal / y;
            
            double slowdown = Tq / (0.01875 + 1/48 + 119/5760);
            System.out.println("S, L" + y + " " + slowdown); 
        }
        
        System.out.println("L 50"); 
    }
}