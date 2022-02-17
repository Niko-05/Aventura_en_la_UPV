// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    public void init(int ki, int cap){}
    public  synchronized void kidSwims() throws InterruptedException {
        /*while(true){
            log.waitingToSwim();
            wait();
        }*/
        
        log.swimming();
    }
    
    public synchronized void kidRests(){
        log.resting();
        //this.notifyAll();
    }
    
    public synchronized void instructorSwims(){
        log.swimming();
        //this.notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException {
        /*while(true){
            log.waitingToRest();
            wait();
        }*/
        
        log.resting();
    }
}
