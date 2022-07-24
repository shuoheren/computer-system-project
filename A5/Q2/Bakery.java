import java.util.concurrent.Semaphore;
import java.util.*;

public class Bakery extends Thread { 
    public int id;
    public static final int numberOfThreads = 5;
    private static volatile boolean[] choosing = new boolean[numberOfThreads]; 
    private static volatile int[] ticket = new int[numberOfThreads];
    
    public Bakery(int i) {
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
        choosing[i] = true;
        ticket[i] = findMax() + 1;
        choosing[i] = false;
        
        for (int j = 0; j < numberOfThreads; j++) {
            while (choosing[j]) {}
            while (ticket[j] != 0 && (ticket[j] < ticket[i] || (ticket[j] == ticket[i] && j > i))) {}      
        }
    }
    

    private void unlock(int i) {
        ticket[i] = 0;
    }
    
    
    private int findMax() {
        int max = ticket[0];
        
        for (int i = 1; i < ticket.length; i++) {
            if (ticket[i] > max)
                max = ticket[i];
        }
        return max;
    }
    
    
    public static void main(String[] args) {
        // Initialization of the global variables.
        for (int i = 0; i < numberOfThreads; i++) {
            choosing[i] = false;
            ticket[i] = 0;
        }
        
        Bakery[] threads = new Bakery[numberOfThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Bakery(i);
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
