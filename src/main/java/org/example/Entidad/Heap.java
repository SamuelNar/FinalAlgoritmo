package org.example.Entidad;

public class Heap<T extends Comparable<? super T>> implements PriorityQueue<T>{
    public T[] queue;
    private int size;
    private static final int CAPACITY = 8;

    public Heap(){
        queue =(T[]) new Comparable[CAPACITY+1];
        size=0;
    }


    public boolean less(T a, T b){
        return a.compareTo(b) < 0;
    }

    public void exch(T[] a, int i, int j){
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private boolean IsMaxHeapOrdered(int i){
        if(i>size){
            return true;
        }
        int left = 2*i;
        int right = 2*i+1;
        if (left <= size && less(queue[i],queue[left])){
                return false;
        }
        if (right<=size && less(queue[i],queue[right])){
            return false;
        }
        return IsMaxHeapOrdered(left) && IsMaxHeapOrdered(right);
    }

    public boolean repOk(){
        for (int i=1 ;i<=size; i++){
            if (queue[i] == null){
                return false;
            }
        }
        for (int i = size+1;i< queue.length;i++){
            if (queue[i] != null){
                return false;
            }
        }
        if (queue[0] != null ) return false;
        return IsMaxHeapOrdered(1);
    }

    public void swim(int i){
        while (i>1 && less(queue[i/2],queue[i])){
            exch(queue,i/2,i);
            i= i/2;
        }
    }

    public void sink(int i ){
        while (2*i <=size){
            int j = 2*i;
            if (j<size && less(queue[j],queue[j+1])){
                j++;
            }
            if (!less(queue[i], queue[j])){
                break;
            }
            exch(queue,i,j);
            i=j;
        }
    }

    public void resize(int capacity){
        T[] temp = (T[]) new Comparable[capacity];
        for(int i = 1; i <= size; i++){
            temp[i] = queue[i];
        }
        queue = temp;
    }

    @Override
    public void insert(T x) {
        if (size ==  queue.length-1){
            resize(2*queue.length);
        }
        queue[++size] = x;
        swim(size);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T max() {
        if (isEmpty()){
            return null;
        }
        return queue[1];
    }

    @Override
    public T RemoveMax() {
        if (isEmpty()){
            return null;
        }
        T max = queue[1];
        exch(queue,1,size--);
        queue[size+1] = null;
        sink(1);
        return max;
    }
}
