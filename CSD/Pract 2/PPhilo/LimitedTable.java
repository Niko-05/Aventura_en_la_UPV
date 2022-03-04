// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    private int aforoActual = 0;
    public LimitedTable(StateManager state) {super(state);}
    public synchronized void enter(int id) throws InterruptedException{
        while(aforoActual == 4){state.wenter(id); wait();}
        aforoActual++;
        state.enter(id);
    }
    public synchronized void exit(int id){aforoActual--; state.exit(id);}
}
