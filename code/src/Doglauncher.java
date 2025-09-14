public class Doglauncher {
    public static void main(String[] args){
        Dog chester = new Dog(17);
        Dog yusuf = new Dog(150);
        Dog larger = chester.maxdog(yusuf);
        larger.makeNoise();

        System.out.println(Dog.binomen);
    }
}
