package org.example.Entidad;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AVLs<T extends Comparable<? super T>> implements SortedSet<T>{

    public NodeAVL<T> root;
    public int size;

    public AVLs(){

    }

    public int height(NodeAVL<T> x){
        if (x==null){
            return 0;
        }
        return x.height;
    }
    public int BalanceFactor(NodeAVL<T> x){
        return height(x.left) - height(x.right);
    }

    public boolean isAVLs(NodeAVL<T> x){
        if (x == null){
            return true;
        }
        int bf = BalanceFactor(x);
        if (bf > 1 || bf <-1){
            return false;
        }
        return isAVLs(x.left) && isAVLs(x.right);
    }

    public boolean repOk(){
        return isAVLs(root);
    }


    public NodeAVL<T> RotateLeft(NodeAVL<T> x){
        NodeAVL<T> aux = x.right;
        x.right = aux.left;
        aux.left = x;
        x.height = 1 + Math.max(height(x.left),height(x.right));
        aux.height = 1 + Math.max(height(aux.left),height(aux.right));
        return aux;
    }

    public NodeAVL<T> RotateRight(NodeAVL<T> x){
        NodeAVL<T> aux = x.left;
        x.left = aux.right;
        aux.right = x;
        x.height = 1 + Math.max(height(x.left),height(x.right));
        aux.height = 1 + Math.max(height(aux.left),height(aux.right));
        return aux;
    }

    public NodeAVL<T> balance(NodeAVL<T> x){
        if (BalanceFactor(x) < -1){
            if (BalanceFactor(x.right)>0){
                x.right = RotateRight(x.right);
            }
            x = RotateLeft(x);
        } else if (BalanceFactor(x) > 1) {
            if (BalanceFactor(x.left)<0){
                x.left = RotateLeft(x.left);
            }
            x = RotateRight(x);
        }
        return x;
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

    public NodeAVL<T> add(NodeAVL<T> x,T key){
        if (x == null){
            return new NodeAVL<>(key,1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0 ){
            x.left = add(x.left,key);
        } else if (cmp > 0) {
            x.right = add(x.right,key);
        }else {
            assert false;
        }
        x.height = 1 + Math.max(height(x.left),height(x.right));
        return balance(x);
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
        if (isEmpty()){
            return false;
        }
        root = removeMin(root);
        size--;
        return true;
    }

    public NodeAVL<T> removeMin(NodeAVL<T> x){
        if (x.left == null){
            return x.right;
        }
        x.left = removeMin(x.left);
        x.height = 1 + Math.max(height(x.left),height(x.right));
        return balance(x);
    }

    public NodeAVL<T> get(NodeAVL<T> x,T key){
        if (x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            return  get(x.left,key);
        } else if (cmp > 0) {
            return get(x.right,key);
        }
        return x;
    }

    public NodeAVL<T> min(NodeAVL<T> x){
        if (x.left == null){
            return x;
        }else {
            return min(x.left);
        }
    }

    public NodeAVL<T> max(NodeAVL<T> x){
        if (x.right == null){
            return x;
        }else {
            return max(x.right);
        }
    }
    @Override
    public boolean contains(T key) {
        return get(root,key) != null;
    }


    public NodeAVL<T> removeMin(NodeAVL<T> x,T key){
        int cmp = key.compareTo(x.key);
        if (cmp < 0){
            x.left = removeMin(x.left,key);
        } else if (cmp > 0) {
            x.right = removeMin(x.right,key);
        }else {
            if (x.left==null){
                return x.right;
            }
            if (x.right == null){
                return x.left;
            }

            NodeAVL<T> t = x;
            x = min(t);
            x.right = removeMin(x.right);
            x.left = t.left;
        }
        x.height = 1 + Math.max(height(x.left),height(x.right));
        return balance(x);
    }
    @Override
    public Iterator<T> iterator() {
        return null;
    }
    @Override
    public String toString() {
        List<T> result = new LinkedList<>();
        inOrder(root, result);
        return result.toString();
    }

    private void inOrder(NodeAVL<T> x, List<T> result) {
        if (x == null) return;
        inOrder(x.left, result); // izquierdo
        result.add(x.key);     // nodo
        inOrder(x.right, result); // derecho
    }

}
