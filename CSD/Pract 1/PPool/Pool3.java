// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    int kidsSwimming = 0;
    int instructorsSwimming = 0;
    int KI = 0;
    int swimmersInPool = 0;
    public static final int MAX_POOL = 5;
    public void init(int ki, int cap){KI = ki;}
    
    public  synchronized void kidSwims() throws InterruptedException {
        while(kidsSwimming == KI  * instructorsSwimming || swimmersInPool == MAX_POOL){
            log.waitingToSwim();
            wait();
        }
        kidsSwimming++;
        swimmersInPool++;
        notifyAll();
        log.swimming();
    }
    
    public synchronized void kidRests(){
        kidsSwimming--;
        swimmersInPool--;
        notifyAll();
        log.resting();
    }
    
    public synchronized void instructorSwims() throws InterruptedException{
        while(swimmersInPool == MAX_POOL){
            log.waitingToSwim();
            wait();
        }
        instructorsSwimming++;
        swimmersInPool++;
        notifyAll();
        log.swimming();
    }
    
    public synchronized void instructorRests() throws InterruptedException {
        while(kidsSwimming != 0 || kidsSwimming == KI * instructorsSwimming){
            log.waitingToRest();
            wait();
        }
        instructorsSwimming--;
        swimmersInPool--;
        notifyAll();
        log.resting();
    }
}
