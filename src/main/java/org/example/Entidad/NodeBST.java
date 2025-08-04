package org.example.Entidad;

import java.util.LinkedList;
import java.util.List;

public class NodeBST<T> {
    public T key;
    public NodeBST<T> left;
    public NodeBST<T> rigth;

    public NodeBST(T key){
        this.key = key;
    }

    public List<T> InOrder(){
        List<T> result = new LinkedList<>();
        if(left != null){
            result.addAll(left.InOrder());
        }
        result.add(key);
        if(rigth != null){
            result.addAll(rigth.InOrder());
        }
        return  result;
    }
}
