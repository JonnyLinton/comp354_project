package model;

import java.util.ArrayList;

public class LimitedSizeQueue<K> extends ArrayList<K> {

    private static final int MAX_SIZE = 4;

    public LimitedSizeQueue(){
        super(5);
    }

    public boolean add(K k){
        boolean r = false;
        if(!this.contains(k)) {
            r = super.add(k);
        }
        if (size() > MAX_SIZE){
            removeRange(0, size() - MAX_SIZE - 1);
        }
        return r;
    }

    public K getMostRecent() {
        return get(size() - 1);
    }

    public K getOldest() {
        return get(0);
    }
}
