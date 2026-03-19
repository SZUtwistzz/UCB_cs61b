package deque;

import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T>{

    private static int ARRAY_SIZE = 8;
    public static int RFACTOR = 2;
    private int size;
    private int nextFirst ;
    private int nextLast ;

    private T[] items;
    public ArrayDeque61B(){
        items = (T[]) new Object[ARRAY_SIZE];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    @Override
    public void addFirst(T x) {
        if(NeedToBigeer()){
            this.resizeToBigger();
        }
        items[nextFirst] = x;
        nextFirst-=1;
        nextFirst = Math.floorMod(nextFirst,items.length);
        size+=1;
    }

    @Override
    public void addLast(T x) {
        if(NeedToBigeer()){
            this.resizeToBigger();
        }
        items[nextLast] = x;
        nextLast +=1;
        nextLast = Math.floorMod(nextLast, items.length);
        size +=1;
    }

    @Override
    public List<T> toList() {
        if(isEmpty()) return null;

        List<T> returnList = new ArrayList<>();
        int currentFirst = Math.floorMod(nextFirst+1, items.length);

        for(int i=0;i<size;i++){
            returnList.add(items[currentFirst]);
            currentFirst = Math.floorMod(currentFirst+1, items.length);
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(Needtothinner()){
            this.resizeTothineer();
        }
        int toRemoveIndex = Math.floorMod(nextFirst+1,items.length);
        nextFirst = toRemoveIndex;
        size-=1;
        return items[toRemoveIndex];
    }

    @Override
    public T removeLast() {
        if(Needtothinner()){
            this.resizeTothineer();
        }
        int toRemoveIndex = Math.floorMod(nextLast-1,items.length);
        nextLast = toRemoveIndex;
        size-=1;
        return items[toRemoveIndex];
    }

    @Override
    public T get(int index) {
        if(isEmpty()||index<0 || index>=size) return null;
        int first = Math.floorMod(nextFirst+1, items.length);

        int target = Math.floorMod(first + index, items.length);
        return items[target];
    }

    @Override
    public T getRecursive(int index) {
        if(index<0 || index>=size) return null;

        int first = Math.floorMod(nextFirst+1, items.length);
        return assist(items,index,first);
    }

    public T assist(T[] items,int index,int first){
        if(index==0) return items[first];
        return assist(items,index-1,Math.floorMod(first+1,items.length));
    }

    public boolean NeedToBigeer(){
        if(size== items.length) return true;
        return false;
    }

    public boolean Needtothinner(){
        if(items.length>=16){
            double UsageRatio = (double)size/ (double)items.length;
            if(UsageRatio<0.25){
                return true;
            }
            return false;
        }
        return false;
    }

    public void resizeToBigger(){
        T[] newArray = (T[]) new Object[items.length*RFACTOR];
        int iteratorCounter = 0;
        for(int i =Math.floorMod(nextFirst+1,items.length);iteratorCounter<size;i=Math.floorMod(i+1,items.length)){
            newArray[iteratorCounter] = items[i];
            iteratorCounter+=1;
        }
        nextFirst = newArray.length-1;
        nextLast = size;
        items = newArray;
    }

    public void resizeTothineer(){
        T[] newArray = (T[]) new Object[items.length/RFACTOR];
        int iteratorCounter = 0;
        for(int i =Math.floorMod(nextFirst+1,items.length);iteratorCounter<size;i=Math.floorMod(i+1,items.length)){
            newArray[iteratorCounter] = items[i];
            iteratorCounter+=1;
        }
        nextFirst = newArray.length-1;
        nextLast = size;
        items = newArray;
    }

    private class ArrayDequeIterator implements Iterator{

        private int pos;
        ArrayDequeIterator(){
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return pos<size;
        }

        @Override
        public Object next() {
            T returnItem = get(pos);
            pos +=1;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ArrayDeque61B other){
            if(this.size !=other.size()){
                return false;
            }

            int count = 0;
            for(T x:this){
                if(!x.equals(other.get(count))){
                    return false;
                }
                count+=1;
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        String result = "[";
        for(int i =0;i<size;i++){
            if(i!=size-1){
                result = result+this.get(i).toString()+", ";
            }
            else result = result+this.get(i).toString();
        }

        result+="]";
        return result;
    }
}

