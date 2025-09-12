import java.util.Scanner;

public class quadrant {
    public static void main(String[] args){
        Scanner scanner  =new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println(solve(a,b));
    }
    public static int solve(int x,int y){
        if(x==0 && y==0){
            return 0;
        }
        else if(y>0 && x>0){
            return 1;
        }
        else if(y>0 && x<0){
            return 2;
        }
        else if(y< 0 && x<0){
            return 3;
        }
        else{
            return 4;
        }
    }
}
