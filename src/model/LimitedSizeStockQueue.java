package model;

import java.util.ArrayList;

public class LimitedSizeStockQueue extends ArrayList<Stock> {

    private static final int MAX_SIZE = 4;

    public LimitedSizeStockQueue(){
        super(MAX_SIZE);
    }

    public boolean addToFront(Stock stock){
        boolean isNewStock = true;

        int index = indexOf(stock);

        if(index != -1) { // stock is in queue
            this.remove(index); // remove it, so that it can be put back on top
            isNewStock = false;
        }
        add(0, stock);

        if (size() > MAX_SIZE){
            this.removeRange(MAX_SIZE + 1, size());
        }

        return isNewStock;
    }

    public boolean addToBack(Stock stock){
        boolean isNewStock = true;

        int index = indexOf(stock);

        if(index != -1) { // stock is in queue
            this.remove(index); // remove it, so that it can be put back on top
            isNewStock = false;
        }
        add(stock);

        if (size() > MAX_SIZE){
            this.removeRange(0, size() - MAX_SIZE - 1);
        }

        return isNewStock;
    }

    private int indexOf(Stock stock) {
        for (int i=0; i<this.size(); i++) {
            if (stock.getTicker().equals(this.get(i).getTicker()))
                return i;
        }

        return -1;
    }

    public Stock getMostRecent() {
        return get(0);
    }

    public Stock getOldest() {
        return get(size() - 1);
    }
}
