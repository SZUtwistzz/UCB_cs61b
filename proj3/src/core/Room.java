package core;

import java.awt.*;
import java.util.Random;

public class Room {
    private Point bottomLeft;
    private int width;
    private int height;
    private Point center;

    public Room(Point bottomLeft_,int w,int h){
        bottomLeft = bottomLeft_;
        width = w;
        height = h;
        this.getCenter();
    }
    public Point getCenter(){
        center = new Point(width/2 + bottomLeft.x,height/2 +bottomLeft.y);
        return center;
    }

    public Point getPointFromRoom(Random rand){

        return new Point((int) (bottomLeft.x+width*rand.nextDouble(1)), (int) (bottomLeft.y+height*rand.nextDouble(1)));
    }

    public int getWidth(){return width;};
    public int getHeight(){return height;}
    public Point getBottomLeft(){return bottomLeft;}
}
