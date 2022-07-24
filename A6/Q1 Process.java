import java.util.concurrent.Semaphore;
import java.util.*;

class SemPriority extends Thread {
    private static int K; //number of processes
    private volatile static int N; //tracks the number of processes that are waiting or processing
    private volatile static int[] Priority; //an array of integers representing priority of processes
    private volatile static boolean[] V; //record the processes that are waiting
    private volatile static Semaphore[] B; //blocking semaphore
    private volatile static Semaphore mutex; //ensures mutual exclusion
    
    public SemPriority(int k) {
        K = k;
        N = 0;
        Priority = new int[K];
        B = new Semaphore[K];
        V = new boolean[K];
        for (int t = 0; t < K; t++) {
            Priority[t] = t;
            V[t] = false;
            B[t] = new Semaphore(0);
        }
        mutex = new Semaphore(1);
    }
    
    public void wait(int i) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {}    
        System.out.println("P" + i + " is requesting CS");
        N++;
        if (N == 1) {
            mutex.release();
        } else {
            mutex.release();
            V[i] = true;
            try {
                B[i].acquire();
            } catch (InterruptedException e) {}
        }
    }
    
    public void signal(int i) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {}  
        System.out.println("P" + i + " is exiting CS");
        V[i] = false;
        N--;
        if (N > 0) {
            int k = 4;
            for (int j = K - 1; j >= 0; j--) {
                if (V[j]) {
                    if (Priority[j] < Priority[k]) {
                        k = j;
                    }
                }
            }
            B[k].release();
        }
        mutex.release();
    }
}
    
    
public class Process extends Thread {
    private int id;
    private static SemPriority sem = new SemPriority(5);
    
    public Process(int i) {
        id = i;
    }
    
    public void run() {
        for (int z = 0; z < 5; z++) {
            sem.wait(id);
            
            // critical section
            System.out.println("P" + id + " is in the CS");
            
            sem.signal(id);
        }
    }
    
    public static void main(String[] args) {
        final int N = 5;
        Process[] p = new Process[N];
        for (int i = 0; i < N; i++) {
            p[i] = new Process(i);
            p[i].start();
        }
    }
}
