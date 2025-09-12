import java.util.Scanner;

public class printIndexed {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        solve(s);
    }
    static void solve(String s){
        String output = "";
        for(int i = 0;i<s.length();i++){
            output +=s.charAt(i);
            output +=s.length()-1-i;
        }
        System.out.println(output);
    }
}
