package model;

import java.util.ArrayList;

/**
 * A fixed-size Queue of Stock Objects that extends ArrayList.
 * Trims elements as soon as size exceeds the maximum.
 */
public class LimitedSizeStockQueue extends ArrayList<Stock> {

    private static final int MAX_SIZE = 4;

    public LimitedSizeStockQueue(){
        super(MAX_SIZE);
    }

    /**
     * Adds a Stock object to the 'front' of the queue (position 0).
     * If the element is already in the Queue, it is repositioned to the front.
     * If there are more than 5 elements after addition, the 'oldest'
     * elements are removed (Stocks at the 'back' of the queue).
     *
     * @param stock Stock object to be added.
     * @return true if the stock is not already in the queue.
     */
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

    /**
     * Adds a Stock object to the 'back' of the queue (position 4),
     * if the Stock is already in the Queue, it repositions it to
     * be at the back of the Queue. If the maximum size is exceeded,
     * element at the 'front' of the list is removed.
     *
     * @param stock Stock object to be added.
     * @return true if the stock is not already in the queue.
     */
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

    /**
     * Gives the index of the specified Stock,
     * -1 if not an element in the Queue.
     *
     * @param stock Stock to check the index of
     * @return index of the specified Stock, -1 if not in the Queue.
     */
    private int indexOf(Stock stock) {
        for (int i=0; i<this.size(); i++) {
            if (stock.getTicker().equals(this.get(i).getTicker()))
                return i;
        }

        return -1;
    }
}
