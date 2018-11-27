import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

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
        return null;
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
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
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

    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }

}
