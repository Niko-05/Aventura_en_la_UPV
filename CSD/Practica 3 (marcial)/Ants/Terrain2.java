
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Terrain2 implements Terrain {
    private boolean occupied[][];
    Viewer v;
    ReentrantLock lock;
    Condition cola[][];
    Pos dest;

    public Terrain2(int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        cola = new Condition[t][t];
    }

    public void     hi      (int a) {
        try{
            dest = v.getPos(a);
            occupied[dest.x][dest.y] = true;
            cola[dest.x][dest.y] = lock.newCondition();
            lock.lock();
            v.hi(a);
        }finally { lock.unlock(); }
    }
    public void     bye     (int a) {
        dest = v.getPos(a);
        occupied[dest.x][dest.y] = false;
        cola[dest.x][dest.y] = lock.newCondition();
        notifyAll();v.bye(a);
        lock.unlock();
    }

    public void     move    (int a) throws InterruptedException {
        v.turn(a); Pos dest=v.dest(a);
        while (v.occupied(dest)) {
            dest = v.getPos(a);
            occupied[dest.x][dest.y] = false;
            cola[dest.x][dest.y].await();
            v.retry(a);
        }
        v.go(a); cola[dest.x][dest.y].signalAll();
    }
}