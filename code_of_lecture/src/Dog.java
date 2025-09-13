package lec2_intro2;

import java.util.concurrent.DelayQueue;

public class Dog {

    public int weightInPounds;
    public static String binomen = "Canis familiaris";
    public Dog(int w) {
        weightInPounds = w;
    }

    public void makeNoise(){
        if(weightInPounds <10){
            System.out.println("yipyipyip!");
        }else if(weightInPounds <30){
            System.out.println("bark!");
        }else{
            System.out.println("aroooooo!");
        }
    }

    public static Dog maxdog(Dog d1,Dog d2){
        if (d1.weightInPounds>d2.weightInPounds){
            return d1;
        }
        else{
            return d2;
        }
    }
    public Dog maxdog(Dog dog){
        if(this.weightInPounds>dog.weightInPounds){
            return this;
        }else{
            return dog;
        }
    }
}
