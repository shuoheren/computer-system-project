import java.util.concurrent.Semaphore;
import java.util.*;

public class Peterson extends Thread {
    public int id;
    public static final int numberOfThreads = 2;
    private static volatile boolean[] flag = new boolean[numberOfThreads]; 
    private static volatile int turn;
    
    public Peterson(int i) {
        id = i;
    }
    
    public void run() {
        for(int k = 0; k < 5; k++) {
            lock(id);
            // Start of critical section.
            try {
                Random randomno = new Random();
                
                System.out.println("[" + id + ", " + k + "] " + "We hold these truths to be self-evident, that all men are created equal,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that they are endowed by their Creator with certain unalienable Rights,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that among these are Life, Liberty and the pursuit of Happiness.");
                Thread.sleep(randomno.nextInt(20));
            } catch (InterruptedException e) {}
            // End of critical section.
            unlock(id);   
        }
    }
    
    public void lock(int i) {
        int j;
        if (i == 0) {
            j = 1;
        } else {
            j = 0;
        }
        
        flag[i] = true;
        turn = j;
        while (flag[j] && turn == j) {};
    }
    
    private void unlock(int i) {
        int j;
        if (i == 0) {
            j = 1;
        } else {
            j = 0;
        }
        
        flag[i] = false;
    }
    
    public static void main(String[] args) {
        // Initialization of the global variables.
        turn = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            flag[i] = false;
        }
        
        Peterson[] threads = new Peterson[numberOfThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Peterson(i);
            threads[i].start();
        }
        
        // Wait all threads to finish.
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }    
    }  
}

