package org.example.Entidad;

import java.util.LinkedList;
import java.util.List;

public class NodeAVL<T> {
    public T key;
    public NodeAVL<T> left,right;
    public int height;

    public NodeAVL(T key,int height){
        this.key = key;
        this.height = height;
    }

    public List<T> inOrder() {
        List<T> result = new LinkedList<T>();
        if (left != null) {
            result.addAll(left.inOrder());
        }
        result.add(key);
        if (right != null) {
            result.addAll(right.inOrder());
        }
        return result;
    }

}
