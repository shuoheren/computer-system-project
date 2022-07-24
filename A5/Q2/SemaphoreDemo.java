import java.util.concurrent.Semaphore;
import java.util.*;

public class SemaphoreDemo extends Thread { 
    int id;
    static Semaphore sem = new Semaphore(1);
    
    public SemaphoreDemo(int i) {
        id = i;
    }
    
    public void run() {
        for(int k = 0; k < 5; k++) {
            Random randomno = new Random();
            try {
                sem.acquire();
                
                System.out.println("[" + id + ", " + k + "] " + "We hold these truths to be self-evident, that all men are created equal,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that they are endowed by their Creator with certain unalienable Rights,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that among these are Life, Liberty and the pursuit of Happiness.");
                Thread.sleep(randomno.nextInt(20));
                
            } catch (InterruptedException e) {}
            // End of critical section.
            sem.release();
            
            try {
                Thread.sleep(randomno.nextInt(20));
            } catch (InterruptedException e) {}
        }
    }
    
    
    public static void main(String[] args) {
        SemaphoreDemo[] sema = new SemaphoreDemo[5];
        for (int i = 0; i < 5; i++) {
            sema[i] = new SemaphoreDemo(i);
            sema[i].start();
        }
        
        // Wait all threads to finish.
        for (int i = 0; i < 5; i++) {
            try {
                sema[i].join();
            } catch (InterruptedException e) {}
        }    
    }
}
    