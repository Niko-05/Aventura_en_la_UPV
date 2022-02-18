// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    int kidsSwimming = 0;
    int instructorsSwimming = 0;
    int KI = 0;
    public void init(int ki, int cap){KI = ki;}
    
    public  synchronized void kidSwims() throws InterruptedException {
        while(kidsSwimming == KI  * instructorsSwimming){
            log.waitingToSwim();
            wait();
        }
        kidsSwimming++;
        notifyAll();
        log.swimming();
    }
    
    public synchronized void kidRests(){
        kidsSwimming--;
        notifyAll();
        log.resting();
    }
    
    public synchronized void instructorSwims(){
        instructorsSwimming++;
        notifyAll();
        log.swimming();
    }
    
    public synchronized void instructorRests() throws InterruptedException {
        while(kidsSwimming != 0 || kidsSwimming == KI * instructorsSwimming){
            log.waitingToRest();
            wait();
        }
        instructorsSwimming--;
        notifyAll();
        log.resting();
    }
}
