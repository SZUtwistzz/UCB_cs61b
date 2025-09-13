import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int res = 0;
        for(int i=0;i<L.size();i++){
            res +=L.get(i);
        }
        return res;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> even = new ArrayList<>();
        for(int i : L){
            if(L.get(i)%2==0){
                even.add((L.get(i)));
            }
        }
        return even;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> res = new ArrayList<>();
        for(int i:L1){
            for(int j:L2){
                if(L1.get(i)==L2.get(j)){
                    res.add(L1.get(i));
                }
            }
        }
        return res;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int cnt = 0;
        for(String i:words){
            for(int j = 0; j <i.length(); j++){
                if(c ==i.charAt(j)){
                    cnt+=1;
                }
            }
        }
        return cnt;
    }
}
