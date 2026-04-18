package core;

import tileengine.TETile;

import java.awt.*;
import java.util.List;
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
    public void getCenter(){
        center = new Point(width/2 + bottomLeft.x,height/2 +bottomLeft.y);
    }
}
