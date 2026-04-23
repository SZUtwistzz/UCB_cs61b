import core.Room;
import core.World;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    public void testRooms(){
        World dummyWorld = new World(1324433);
        dummyWorld.generate();

        List<Room> rooms = dummyWorld.getRooms();
        for(Room room:rooms){
            String output = "x: " + room.getBottomLeft().x+" y: " +room.getBottomLeft().y + "\n";
            output +="width: "+room.getWidth() + " height: "+ room.getHeight()+"\n";

            System.out.println(output);
        }
    }
}
