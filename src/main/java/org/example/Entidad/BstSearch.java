package org.example.Entidad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BstSearch<T extends Comparable<T>> implements SortedSet<T> {
    public NodeBST<T> root;
    public int size;

    public BstSearch(){
        root = null;
        size=0;
    }

    public boolean isBST(NodeBST<T> x, T min, T max){
        if (x==null){
            return true;
        }
        if (min != null && x.key.compareTo(min)<=0){
            return false;
        }
        if (max != null && x.key.compareTo(max)>=0){
            return false;
        }
        return isBST(x.left,min,x.key) && isBST(x.rigth,x.key,max);
    }
    public boolean repOK() {
        return isBST(root, null, null);
    }

    @Override
    public boolean add(T x) {
        if (contains(x)){
            return false;
        }
        root = add(root,x);
        size++;
        return true;
    }

    public NodeBST<T> add(NodeBST<T> x,T key){
        if (x==null){
            return new NodeBST<>(key);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0){
            x.left = add(x.left,key);
        } else if (cmp > 0) {
            x.rigth=add(x.rigth,key);
        }else {
            assert false;
        }
        return x;
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean removeMin() {
        if(isEmpty()){
          return false;
        }
        root = removeMin(root.left);
        size--;
        return false;
    }


    public NodeBST<T> removeMin(NodeBST<T> x){
        if (x.left == null){
            return x.rigth;
        }
        x.left = removeMin(x.left);
        return x;
    }


    public boolean remove(T key){
        if (!contains(key)){
            return false;
        }
        root = remove(root,key);
        size--;
        return true;
    }

    public NodeBST<T> remove(NodeBST<T>x,T key){
        int cmp = key.compareTo(x.key);
        if (cmp < 0){
            x.left = remove(x.left,key);
        } else if (cmp > 0) {
            x.rigth = remove(x.rigth, key);
        }else {
            if (x.rigth == null){
                return x.left;
            }
            if (x.left == null){
                return x.rigth;
            }
            NodeBST<T> aux = x;
            x = min(x.rigth);
            x.rigth = removeMin(x.rigth);
            x.left = aux.left;
        }
        return x;
    }

    @Override
    public boolean contains(T key) {
        return get(root,key) != null;
    }


    public NodeBST<T> get(NodeBST<T>x , T key){
        if (x==null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0 ){
            return get(x.left,key);
        } else if (cmp > 0) {
            return get(x.rigth,key);
        }
        return x;
    }

    public T min(){
        if (isEmpty()){
            return null;
        }
        return min(root).key;
    }

    public NodeBST<T> min(NodeBST<T> x){
        if (x.left == null){
            return x;
        }else {
            return min(x.left);
        }
    }

    public T max(){
        if (isEmpty()){
            return null;
        }
        return max(root).key;
    }

    public NodeBST<T> max(NodeBST<T> x){
        if (x.rigth == null){
            return x;
        }else {
            return min(x.rigth);
        }
    }

    public List<T> inOrder() {
        if (root == null) {
            return new LinkedList<>();
        }
        return root.InOrder();
    }

    @Override
    public Iterator<T> iterator() {
        List<T> list = new ArrayList<>();
        list = root.InOrder();
        return list.iterator();
    }

    public String toString() {
        String res = "{";
        boolean first = true;
        List<T> elems = inOrder();
        for (T item : elems) {
            if (!first) {
                res += ", ";
            } else {
                res += item.toString();
                first = false;
            }
        }
        res += "}";
        return res;
    }
}
