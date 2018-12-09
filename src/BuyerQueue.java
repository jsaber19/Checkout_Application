import javax.swing.*;
import java.util.*;

// class that represents a queue of buyers
public class BuyerQueue<T> implements Queue<T> {
    // fields; queue is backed by an array
    protected Object[] arr;
    protected boolean isFull;
    protected int firstEmpty;
    public int firstElement;


    // constructors
    public BuyerQueue(){
        arr = new Object[500];
        firstEmpty = 0;
        firstElement = 0;
        isFull = false;
    }
    public BuyerQueue(int initialSize){
        arr = new Object[initialSize];
        firstEmpty = 0;
        firstElement = 0;
        isFull = false;
    }

    // Queue implementation methods – we made a lot of them and then didn't use most of them

    // returns size of queue
    @Override
    public int size() {
        if (isEmpty()) return 0;
        if (isFull) return arr.length;
        if (firstEmpty > firstElement){
            return firstEmpty - firstElement;
        }
        return arr.length - (firstElement - firstEmpty);
    }

    // returns true if queue is empty, false otherwise
    @Override
    public boolean isEmpty() {
        return firstElement == firstEmpty && !isFull;
    }

    // returns true if queue contains o, false otherwise
    @Override
    public boolean contains(Object o) {
        int j = firstElement;
        for(int i = 0; i < size(); i++){
            if(arr[j++].equals(o)){ return true;}
            if(j >= arr.length){j = 0;}
        }
        return false;
    }

    // iterator object to iterate through the queue
    @Override
    public Iterator<T> iterator() {

        // nested class for an iterator object
        class QueueIterator<T> implements Iterator<T>{
            private BuyerQueue<T> queue;
            private int index = 0;

            public QueueIterator(BuyerQueue<T> queue){
                this.queue = queue;
            }

            @Override
            public boolean hasNext() {
                if (index + 1 >= queue.size()) { return false; }
                return true;
            }

            @Override
            public T next() {
                return queue.remove();
            }
        }

        return new QueueIterator<T>(this);
    }

    // converts queue to an array
    @Override
    public Object[] toArray() {
        Object[] temp = new Object[arr.length];
        int j = firstElement;
        for(int i = 0; i < size(); i++){
            temp[i] = arr[j++];
            if(j >= arr.length){j = 0;} // to reorder since we used ring buffer
        }
        return temp;
    }

    // didn't code
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    // adds an object to the queue
    @Override
    public boolean add(Object o) {
        arr[firstEmpty] = o;
        if (++firstEmpty == firstElement){
            resize();
        }
        return true;
    }

    // to resize queue since we use a ring buffer
    private boolean resize(){
        Object[] temp = new Object[arr.length*2];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[firstElement];
            if(++firstElement == arr.length){
                firstElement = 0;
            }
        }
        firstElement = 0;
        firstEmpty = arr.length;
        arr = temp;

        return true;
    }

    // didn't code
    @Override
    public boolean remove(Object o) {
        return false;
    }

    // returns true if queue contains all of a collection
    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator i = c.iterator();
        while(i.hasNext()){
            if(!contains(i.next())){
                return false;
            }
        }
        return true;
    }

    // adds every object in a collection to the queue
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T t : c){
            add(t);
        }
        return true;
    }

    // didn't code
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    // didn't code
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    // clears the queue
    @Override
    public void clear() {
        firstEmpty = 0;
        firstElement = 0;
    }

    // adds object o to queue
    @Override
    public boolean offer(Object o) {
        arr[firstEmpty] = o;

        if (++firstEmpty == firstElement){
            resize();
        }

        return true;
    }

    // removes an element from the queue and returns the element
    @Override
    public T remove() {
        if (isEmpty()) { throw new NoSuchElementException(); }

        Object temp = arr[firstElement];

        if(++firstElement == arr.length){
            firstElement = 0;
        }

        return (T)temp;
    }

    // removes an element from the queue and returns the element
    @Override
    public T poll() {
        if (isEmpty()) { return null; }

        Object temp = arr[firstElement];

        if(++firstElement == arr.length){
            firstElement = 0;
        }

        return (T)temp;
    }

    // returns the first element casted as type T
    @Override
    public T element() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        return (T)arr[firstElement];
    }

    // returns the first element casted as type T
    @Override
    public T peek() {
        if (isEmpty()) { return null; }
        return (T)arr[firstElement];
    }

}


