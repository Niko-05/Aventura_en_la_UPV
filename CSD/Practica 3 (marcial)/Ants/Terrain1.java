
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Terrain1 implements Terrain {
    Viewer v;
    ReentrantLock lock;
    Condition cola;

    public Terrain1(int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        cola = lock.newCondition();
    }

    public void     hi      (int a) {
        try{
            lock.lock();
            v.hi(a);
        }finally { lock.unlock(); }
    }
    public void     bye     (int a) {notifyAll();v.bye(a);    }

    public void     move    (int a) throws InterruptedException {
        v.turn(a); Pos dest=v.dest(a);
        while (v.occupied(dest)) {
            cola.await();
            v.retry(a);
        }
        v.go(a); cola.signalAll();
    }
}