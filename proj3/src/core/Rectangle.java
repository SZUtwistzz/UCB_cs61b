package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rectangle {
    private static final int MIN_AREA = 50;//BSP算法接受的最小的面积
    private static final int MINHEIGHT = 12;
    private static final int MAXWIDTH = 12;
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
    public void split(Random seed){
        //1 递归终止条件
        if(height*width <=MIN_AREA || height<MINHEIGHT || width<MAXWIDTH){
            return;
        }

        //2.决定切割方向 并切割
        int splitLength;
        double spiltRatio = getSpiltRatio(seed);
        //垂直切割
        if(width > 1.5*height){
            splitLength = (int) (width*spiltRatio);
            splitVertically(splitLength);
        }
        //水平切割
        else if(height> 1.5 *width){
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
        leftChild.split(seed);
        rightChild.split(seed);
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
        double min = 0.4;
        double max = 0.6;

        return min + (max-min)*seed.nextDouble();
    }

    public Room generateRoom(Random rand) {
        // 确保宽高是奇数
        int roomW = (int)(this.width * getPaddingRatio(rand)) * 2 + 1;
        int roomH = (int)(this.height * getPaddingRatio(rand)) * 2 + 1;

        // 确保起始坐标是奇数
        int roomX = this.bottomleft.x + rand.nextInt(0, (this.width - roomW) / 2) * 2 + 1;
        int roomY = this.bottomleft.y + rand.nextInt(0, (this.height - roomH) / 2) * 2 + 1;

        Point bottomLeftOfRoom = new Point(roomX, roomY);
        room = new Room(bottomLeftOfRoom, roomW, roomH);
        return room;
    }

    private double getPaddingRatio(Random rand){
        double min = 0.2;
        double max = 0.4;

        return min + (max-min)*rand.nextDouble();
    }
    public List<Room> collectRooms(Random rand){
        List<Rectangle> leafNodes = new ArrayList<>();
        collectRoomsHelper(leafNodes);

        List<Room> rooms = new ArrayList<>();
        for(Rectangle rec:leafNodes){
            if(rand.nextDouble()<0.8){
                rooms.add(rec.generateRoom(rand));
            }else{
                rec.room =null;
            }
        }
        return rooms;
    }

    private void collectRoomsHelper(List<Rectangle> leafNodes){
        if(this==null) return;
        else if(this.isLeaf()){
            leafNodes.add(this);
            return;
        }

        leftChild.collectRoomsHelper(leafNodes);
        rightChild.collectRoomsHelper(leafNodes);
    }

    public boolean isLeaf(){
        if(this.leftChild==null && this.rightChild==null){
            return true;
        }
        return false;
    }

    public Point getAnchor(Random rand){
        if(this.isLeaf() && this.hasRoom()){
            if(rand.nextBoolean()){
                return this.room.getCenter();
            }else{
                return this.room.getPointFromRoom(rand);
            }
        }

        boolean leftHas = leftChild.hasRoom();
        boolean rightHas = rightChild.hasRoom();

        if (leftHas && rightHas) {
            // 两边都有房，随机挑一个分支继续深入
            return rand.nextBoolean() ? leftChild.getAnchor(rand) : rightChild.getAnchor(rand);
        } else if (leftHas) {
            // 只有左边有房
            return leftChild.getAnchor(rand);
        } else {
            // 只有右边有房（connect 逻辑应保证至少一边有房，否则不该调用 getAnchor）
            return rightChild.getAnchor(rand);
        }
    }

    public boolean hasRoom() {
        // 如果是叶子节点，直接检查 room 变量是否被实例化且存在
        if (this.isLeaf()) {
            return this.room != null;
        }
        // 如果不是叶子，只要左子树或右子树中有一个有房间，这个分支就是“活”的
        return (leftChild != null && leftChild.hasRoom()) ||
                (rightChild != null && rightChild.hasRoom());
    }
}
