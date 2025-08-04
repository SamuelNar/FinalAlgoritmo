package org.example.Entidad;

import java.util.Iterator;

public interface SortedSet <T extends Comparable<? super T>> extends Iterable<T>{

    public boolean add(T x);
    public int size();
    public boolean removeMin();
    public boolean contains(T key);


    public Iterator<T> iterator();
}
