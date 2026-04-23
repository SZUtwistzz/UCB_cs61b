package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.*;
import java.util.List;

public class World {

    // build your own world!
    private final List<Point> POSSIBLE_DIR = List.of(
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0),
            new Point(0, 1)
    );//遍历的可能方向
    private final double windingpercent = 0.5;
    private TETile[][] grid;
    private final int HEIGHT = 61;
    private final int WIDTH  =101;
    private final Random random;
    List<Room> rooms;
    Rectangle root;
    private int[][] regionMap; // 记录每个点属于哪个区域（房间1, 房间2, 迷宫A...）
    private int nextRegion = 0;

    public World(long seed){
        random = new Random(seed);
    }

    public TETile[][] getGrid(){return grid;}

    public void generate(){
        grid = new TETile[WIDTH][HEIGHT];
        root = new Rectangle(new Point(3,3),WIDTH-6,HEIGHT-6);
        regionMap = new int[WIDTH][HEIGHT];

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                grid[j][i] = Tileset.NOTHING;
            }
        }

        spiltWorld();
        FillRoom();
        FillWall();
        FillMaze();
        connectRegions();
        pruneDeadEnds();
    }

    private void connectRegions() {

    }

    public void spiltWorld(){
        root.split(random);
        rooms = root.collectRooms(this.random);
    }


    public void FillRoom(){
        for(Room room:rooms){
            nextRegion++;
            Point bottomLeft = room.getBottomLeft();
            for(int i=0;i<room.getHeight();i++){
                for(int j=0;j<room.getWidth();j++){
                    grid[bottomLeft.x+j][bottomLeft.y+i] = Tileset.FLOOR;
                    regionMap[bottomLeft.x+i][bottomLeft.y+j] = nextRegion;
                }
            }
        }
    }

    public void FillWall(){
        for(Room room:rooms){
            for(int i=0;i<room.getWidth();i++){
                for(int j=0;j<room.getHeight();j++){
                    Point pos = room.getBottomLeft();
                    int nx = pos.x+i;
                    int ny = pos.y+j;
                    FillsingleBlock(nx,ny);
                }
            }
        }
    }

    private void FillsingleBlock(int x,int y){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(checkValid(x+i,y+j) && grid[x+i][y+j]==Tileset.NOTHING){
                    grid[x+i][y+j] = Tileset.WALL;
                }
            }
        }
    }
    private boolean checkValid(int x,int y){
        if(x<0 || x>=this.WIDTH || y<0 || y>=this.HEIGHT) return false;
        return true;
    }

    public void FillMaze(){
        for(int x=1;x<WIDTH;x+=2){
            for(int y =1;y<HEIGHT;y+=2){
                Point pos = new Point(x,y);
                if(grid[x][y]==Tileset.NOTHING){
                    GrowMaze(pos);
                }
            }
        }
    }

    private int GrowMaze(Point start) {
        List<Point> cells = new ArrayList<>();
        Point lastDir = null; // 初始设为 null 更好判断

        cells.add(start);

        while (!cells.isEmpty()) {
            Point cell = cells.get(cells.size() - 1);
            List<Point> unmadeCells = new ArrayList<>();

            for (Point dir : POSSIBLE_DIR) {
                // 检查前进 2 格是否合法且未被占用
                if (CanCarve(cell, dir)) {
                    unmadeCells.add(dir);
                }
            }

            if (!unmadeCells.isEmpty()) {
                Point dir;
                // 蜿蜒度逻辑：如果可以继续沿用上次方向，则按概率保留
                if (lastDir != null && unmadeCells.contains(lastDir) && random.nextDouble() > windingpercent) {
                    dir = lastDir;
                } else {
                    dir = unmadeCells.get(random.nextInt(unmadeCells.size()));
                }

                // 连续填充两格，确保路径连通
                FillFloor(new Point(cell.x + dir.x, cell.y + dir.y));     // 破开中间的墙
                FillFloor(new Point(cell.x + dir.x * 2, cell.y + dir.y * 2)); // 到达新的节点

                cells.add(new Point(cell.x + dir.x * 2, cell.y + dir.y * 2));
                lastDir = dir;
            } else {
                cells.remove(cells.size() - 1);
                lastDir = null;
            }
        }
        return 0;
    }

    private boolean CanCarve(Point p, Point dir) {
        int nextX = p.x + dir.x * 2;
        int nextY = p.y + dir.y * 2;

        // 必须同时保证第 1 格和第 2 格都在范围内，且第 2 格是空白
        if (checkValid(nextX, nextY) ) {
            return grid[nextX][nextY] == Tileset.NOTHING;
        }
        return false;
    }

    private void FillFloor(Point p){
        grid[p.x][p.y] = Tileset.WATER;
    }

    private void pruneDeadEnds() {
        boolean done = false;
        while (!done) {
            done = true;
            for (int x = 1; x < WIDTH - 1; x++) {
                for (int y = 1; y < HEIGHT - 1; y++) {
                    if (grid[x][y] == Tileset.WATER || grid[x][y] == Tileset.FLOOR) {
                        int neighbors = 0;
                        if (grid[x+1][y] != Tileset.WALL) neighbors++;
                        if (grid[x-1][y] != Tileset.WALL) neighbors++;
                        if (grid[x][y+1] != Tileset.WALL) neighbors++;
                        if (grid[x][y-1] != Tileset.WALL) neighbors++;

                        // 如果一个地板格三面环墙，它就是死胡同，把它变回墙
                        if (neighbors <= 1) {
                            grid[x][y] = Tileset.WALL;
                            done = false;
                        }
                    }
                }
            }
        }
    }

    public List<Room> getRooms(){return rooms;}
    //用于实现持久化
    //保存存档
    public void saveWorld(){
        System.out.println("存档功能没完成");
    }

    //加载存档
    public void loadWorld(){
        System.out.println("加载存档功能没完成");
    }

}
