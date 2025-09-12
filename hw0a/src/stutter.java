import java.util.Scanner;

public class stutter {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(solve(s));
    }
    public static String solve(String s){
        String output = "";
        for(int i = 0; i<s.length() ;i++){
            output+=s.charAt(i);
            output +=s.charAt(i);
        }
        return output;
    }
}
