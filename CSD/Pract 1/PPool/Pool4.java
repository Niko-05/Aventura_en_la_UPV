// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    int kidsSwimming = 0;
    int instructorsSwimming = 0;
    int KI = 0;
    int swimmersInPool = 0;
    int instructorsWaiting = 0;
    public static final int MAX_POOL = 5;
    
    public void init(int ki, int cap){KI = ki;}
    
    public  synchronized void kidSwims() throws InterruptedException {
        while(kidsSwimming == KI  * instructorsSwimming || swimmersInPool == MAX_POOL || instructorsWaiting != 0){
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
