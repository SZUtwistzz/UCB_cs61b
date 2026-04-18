package core;

import tileengine.TETile;

import java.util.List;
import java.util.Random;

public class World {

    // build your own world!
    private TETile[][] grad;
    private int HEIGHT = 60;
    private int WIDTH  =100;
    private Random random;
    List<Room> rooms;


    public void getWorldFromInput(String input){
        //尚未完成
    }

    public void generate(){

    }

    public void spiltWorld(){

    }

    public void connectRoom(){

    }

    public void FillWall(){

    }

    private void FillSingleBlock(){

    }

    //用于实现持久化
    //保存存档
    public void saveWorld(){

    }

    //加载存档
    public void loadWorld(){

    }

}
