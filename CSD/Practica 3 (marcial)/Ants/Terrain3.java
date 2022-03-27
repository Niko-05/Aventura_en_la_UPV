import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class Terrain3 implements Terrain{
    Viewer v;
    private ReentrantLock lock;
    private Condition[][] matriix;

    public Terrain3(int t, int ants, int movs, String msg){
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        matriix = new Condition[t][t];
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
            Pos dest = v.dest(a);
            matriix[dest.x][dest.y].signal();
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
                    if(matriix[dest.x][dest.y].await(300,TimeUnit.MILLISECONDS)){
                        v.retry(a);
                    } else {
                        v.chgDir(a);
                        dest = v.dest(a);
                        v.retry(a);
                    }
                }
            }catch(InterruptedException e){}
            v.go(a);
            matriix[dest.x][dest.y].signal();
        }finally{lock.unlock();}
    }
}

/*if (c[getX][getY].await(300,TimeUnit.MILLISECONDS))
        v.retry(a);
        else{
        v.chgDir(a);
        dest = v.dest(a);
        v.retry(a);
        }*/