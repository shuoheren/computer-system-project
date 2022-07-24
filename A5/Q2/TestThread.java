import java.util.concurrent.Semaphore;
import java.util.*;

class MyProcess extends Thread {
    private int id;
    
    public MyProcess(int i) {
        id = i;
    }
    
    public void run() {
        try {
            Random randomno = new Random();  
            for(int k = 0; k < 5; k++) {
                System.out.println("[" + id + ", " + k + "] " + "We hold these truths to be self-evident, that all men are created equal,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that they are endowed by their Creator with certain unalienable Rights,");
                Thread.sleep(randomno.nextInt(20));
                System.out.println("[" + id + ", " + k + "] " + "that among these are Life, Liberty and the pursuit of Happiness.");
                Thread.sleep(randomno.nextInt(20));
            }   
        } catch (InterruptedException e) {}
    }
}

public class TestThread {
    public static void main(String[] args) {
        final int N = 2;
        MyProcess[] p = new MyProcess[N];
        for (int i = 0; i < N; i++) {
            p[i] = new MyProcess(i);
            p[i].start();
        }
    }
}


