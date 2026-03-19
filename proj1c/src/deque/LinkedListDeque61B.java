package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    @Override
    public void addFirst(T x) {
        Node newnode =  new Node(x);
        Node next = sentinel.next;

        if(next!=null){
            next.prev = newnode;
        }

        newnode.next = next;
        sentinel.next = newnode;
        newnode.prev = sentinel;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newnode = new Node(x);
        Node prev = sentinel.prev;

        if(prev!=null){
            prev.next = newnode;
        }

        newnode.next = sentinel;
        newnode.prev = prev;
        sentinel.prev = newnode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<T>();
        for(Node p = sentinel.next;p!=sentinel;p = p.next){
            returnList.add(p.value);
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(this.size==0) return true;
        else return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) return null;
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size-=1;
        return first.value;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) return null;
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size-=1;
        return last.value;
    }

    @Override
    public T get(int index) {
        if(index<0 || index >=size) return null;

        Node ptr = sentinel.next;
        for(int i=0;i<index;i++){
            ptr = ptr.next;
        }

        return ptr.value;
    }

    @Override
    public T getRecursive(int index) {
        if(index <0 || index >=size) return null;

        return getNodeRecursive(sentinel.next,index);
    }

    public T getNodeRecursive(Node tmp,int remain){
        if(remain==0) return tmp.value;
        return getNodeRecursive(tmp.next,remain-1);
    }

    private class LinkedListIterator implements Iterator<T>{
        private int pos;

        public LinkedListIterator(){
            pos = 0;
        }

        public boolean hasNext(){
            return pos<size;
        }

        public T next(){
            T returnItem = get(pos);
            pos +=1;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof LinkedListDeque61B other){
            if(this.size !=other.size){
                return false;
            }

            int iteratorCounter = 0;
            for(T x:this){
                if(!other.get(iteratorCounter).equals(x)){
                    return false;
                }
                iteratorCounter+=1;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String result = "[";
        for(int i = 0;i<size;i++){
            if(i!=size-1){
                result = result + this.get(i).toString() + ", ";
            }
            else result = result + this.get(i).toString();
        }

        result +="]";
        return result;
    }
    private class Node{
        T value;
        Node prev;
        Node next;
        public Node(T v){
            this.value = v;
            prev = null;
            next = null;
        }
    }

    private int size = 0;
    private Node sentinel = new Node(null);
    public LinkedListDeque61B() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
}

