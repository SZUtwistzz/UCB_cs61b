package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{

    private Comparator<T> Comparator;
    private static class SizeComparator implements Comparator<MaxArrayDeque61B>{
        public int compare(MaxArrayDeque61B o1, MaxArrayDeque61B o2) {
            return o1.size()- o2.size();
        }
    }

    public static SizeComparator getSizeComparator(){
        return new SizeComparator();
    }
    private static class numComparator<T> implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    }

    public MaxArrayDeque61B(Comparator<T> c){
        super();
        Comparator = c;
    }

    public T max(){
        if (this.size()==0) return null;

        int maxIdx = 0;
        for(int i =1;i<this.size();i++){
            if(Comparator.compare(get(i),get(maxIdx))>0){
                maxIdx = i;
            }
        }

        return get(maxIdx);
    }

    public T max(Comparator<T> c){
        if (this.size()==0) return null;

        int maxIdx = 0;
        for(int i =1;i<this.size();i++){
            if(c.compare(get(i),get(maxIdx))>0){
                maxIdx = i;
            }
        }

        return get(maxIdx);
    }
}
