import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain1 implements Terrain{
    Viewer v;
    private ReentrantLock lock;
    private Condition ocupado;
    
    
    public Terrain1(int t, int ants, int movs, String msg){
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        ocupado = lock.newCondition();
    }
    
    public void hi(int a){
        try{
            lock.lock();
            v.hi(a);
        }finally{lock.unlock();}
    }
    
    public void bye(int a){
        try{
            lock.lock();
            ocupado.signalAll();
            v.bye(a);
        }finally{lock.unlock();}
    }
    
    public void move(int a){
        try{
            lock.lock();
            v.turn(a);
            Pos dest = v.dest(a);
            try{
                while(v.occupied(dest)){
                    ocupado.await();
                    v.retry(a);
                }
            }catch(InterruptedException e){}
            v.go(a);
            ocupado.signalAll();
        }finally{lock.unlock();}
    }
}