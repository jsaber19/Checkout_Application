import javax.swing.*;
import java.util.*;

public class BuyerQueue<T> implements Queue<T> {
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

    @Override
    public int size() {
        if (isEmpty()) return 0;
        if (isFull) return arr.length;
        if (firstEmpty > firstElement){
            return firstEmpty - firstElement;
        }
        return arr.length - (firstElement - firstEmpty);
    }

    @Override
    public boolean isEmpty() {
        return firstElement == firstEmpty && !isFull;
    }

    @Override
    public boolean contains(Object o) {
        int j = firstElement;
        for(int i = 0; i < size(); i++){
            if(arr[j++].equals(o)){ return true;}
            if(j >= arr.length){j = 0;}
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {

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

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[arr.length];
        int j = firstElement;
        for(int i = 0; i < size(); i++){
            temp[i] = arr[j++];
            if(j >= arr.length){j = 0;}
        }
        return temp;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Object o) {
        arr[firstEmpty] = o;

        if (++firstEmpty == firstElement){
            resize();
        }

        return true;
    }

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

    @Override
    public boolean remove(Object o) {
        return false;
    }

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

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T t : c){
            add(t);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        firstEmpty = 0;
        firstElement = 0;
    }

    @Override
    public boolean offer(Object o) {
        arr[firstEmpty] = o;

        if (++firstEmpty == firstElement){
            resize();
        }

        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) { throw new NoSuchElementException(); }

        Object temp = arr[firstElement];

        if(++firstElement == arr.length){
            firstElement = 0;
        }


        return (T)temp;
    }

    @Override
    public T poll() {
        if (isEmpty()) { return null; }

        Object temp = arr[firstElement];

        if(++firstElement == arr.length){
            firstElement = 0;
        }

        return (T)temp;
    }

    @Override
    public T element() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        return (T)arr[firstElement];
    }

    @Override
    public T peek() {
        if (isEmpty()) { return null; }
        return (T)arr[firstElement];
    }

}


