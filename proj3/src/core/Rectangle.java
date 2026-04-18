package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rectangle {
    private static final int MIN_AREA = 6;//BSP算法接受的最小的面积
    private Point bottomleft;
    private int width;
    private int height;
    Room room;
    Rectangle leftChild,rightChild;

    public Rectangle(Point bottomleft_,int w,int h){
        bottomleft = bottomleft_;
        width = w;
        height = h;
        leftChild = null;
        rightChild = null;
    }

    //对于当前每一个Rectangle
    //1 递归终止条件 面积小于等于MIN_AREA
    //2 决定切割方向 并切割
    //3 递归向下
    public void split(Rectangle rectangle,Random seed){
        //1 递归终止条件
        if(height*width <=MIN_AREA){
            return;
        }

        //2.决定切割方向 并切割
        int splitLength;
        double spiltRatio = getSpiltRatio(seed);
        //垂直切割
        if(width > 1.25*height){
            splitLength = (int) (width*spiltRatio);
            splitVertically(splitLength);
        }
        //水平切割
        else if(height> 1.25 *width){
            splitLength = (int)(height*spiltRatio);
            spiltHorizontally(splitLength);
        }
        //否则随机切割
        else{
            if (seed.nextBoolean()) { // 50% 概率
                splitLength = (int) (width * spiltRatio);
                splitVertically(splitLength);
            } else {
                splitLength = (int) (height * spiltRatio);
                spiltHorizontally(splitLength);
            }
        }

        //3 对左孩子和右孩子递归使用 split 方法
        split(leftChild,seed);
        split(rightChild,seed);
    }

    //垂直切割辅助方法
    private void splitVertically(int splitLength){
        leftChild = new Rectangle(bottomleft,splitLength,height);
        Point BottomLeftOfRightChild = new Point(bottomleft.x+splitLength,bottomleft.y);
        rightChild = new Rectangle(BottomLeftOfRightChild,width-splitLength,height);
    }
    //水平切割辅助方法
    private void spiltHorizontally(int splitLength){
        leftChild = new Rectangle(bottomleft,width,splitLength);
        Point BottomLeftOfRightChild = new Point(bottomleft.x, bottomleft.y+splitLength);
        rightChild = new Rectangle(BottomLeftOfRightChild,width,height-splitLength);
    }
    private double getSpiltRatio(Random seed){
        double min = 0.3;
        double max = 0.7;

        return min + (max-min)*seed.nextDouble();
    }

    public Room generateRoom(){
        Point bottomLeftOfRoom = new Point(this.bottomleft.x+1,this.bottomleft.y+1);
        room = new Room(bottomLeftOfRoom,width-2,height-2);
        return room;
    }

    public List<Room> collectRooms(Rectangle rectangle){
        List<Rectangle> leafNodes = new ArrayList<>();
        collectRoomsHelper(rectangle,leafNodes);

        List<Room> rooms = new ArrayList<>();
        for(Rectangle rec:leafNodes){
           rooms.add(rec.generateRoom());
        }

        return rooms;
    }

    private void collectRoomsHelper(Rectangle rec,List<Rectangle> leafNodes){
        if(rec==null) return;
        else if(rec.leftChild==null && rec.rightChild ==null){
            leafNodes.add(rec);
        }

        collectRoomsHelper(rec.leftChild,leafNodes);
        collectRoomsHelper(rec.rightChild,leafNodes);
    }
}
