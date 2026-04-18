import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTEst {
    @Test
    public void test(){
        Random r = new Random(1000);
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
        System.out.println(r.nextInt());
    }
}
