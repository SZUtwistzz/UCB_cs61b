public class starTriangle {
    public static void main(String[] args){
        startriangle();
    }
    static void startriangle(){
        for(int i=0;i<5;i++){
            String message = "";
            for(int j=0;j<=i;j++){
                message += '*';
            }
            System.out.println(message);
        }
    }
}
