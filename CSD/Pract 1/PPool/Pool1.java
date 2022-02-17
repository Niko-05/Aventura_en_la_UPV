// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    int kidsSwimming = 0;
    int instructorsSwimming = 0;
    public void init(int ki, int cap){}
    public  synchronized void kidSwims() throws InterruptedException {
        while(instructorsSwimming == 0){
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
        while(kidsSwimming != 0 && instructorsSwimming == 1){
            log.waitingToRest();
            wait();
        }
        instructorsSwimming--;
        notifyAll();
        log.resting();
    }
}
