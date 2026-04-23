# **Project 2B/C: Design Document**

| Name | class id | Email |
| ----- | ----- | ----- |
| Your Name | sp24-s\*\*\* | your.email@berkeley.edu |

## **Data Structures**

## World类：
类内维护的变量：
1.使用TETile[][] grid 二维数组表示世界里的block
2.int HEIGHT 表示世界的高度 60个图块单位
3.int WIDTH  表示世界的宽度 100个图块单位
4.Random random 使用seed来初始化他
5.List<Room> rooms 记录切割好的房间，方便后续进行连接
6.Rectangle root BSP树的根

类内方法:
World(long seed) //使用seed实例化World类的 ”random“ 变量

public TETile[][] getGrid() //返回 “grid”

void generate() //总控方法，在该方法内调用现有的方法来生成随机世界
1.初始化 ‘grid’ 为Tileset.Nothing
2. 调用 ‘spiltWorld()’ 构建BSP树并填充 ‘rooms’列表
3. 调用 ‘FillRoom' 遍历 ‘rooms’ 将 'Tileset.Floor' 填入grid
4. 调用‘connet’ 方法 画出走廊
5. 调用‘FillWall'方法 在地板边缘自动生成墙壁

void spiltWorld() //将目前的世界分割为若干个合理大小的小房间并存在 ‘List<Room> rooms’ 里
1 对 ’root’ Rectangle变量使用 'spilt'方法 得到一颗BSP树
2 使用 ‘cllectRooms’ 方法遍历BSP树 将room存入 “rooms"

void connect(Rectangle node) //将小房间通过走廊连接起来，走廊的宽度为1个图块单位，可生成转弯走廊
1 如果node是叶子节点或者为空 return
2 对左子树和右子树递归调用 ‘connect’
3 如果node的左子树和右子树hasRoom ， 则调用‘drawCorridor’ 缝合当前两个子区域

public void drawCorridor(Point p1, Point p2) 画当前两个点的走廊,随机先水平后垂直或者先垂直后水平
1 根据'rand.nextBoolean()' 随机调用'drawHorizontal' 或者 ‘drawVertical'

辅助方法：
private void drawHorizontal(int x1, int x2, int y) 水平画走廊
private void drawVertical(int y1, int y2, int x) 垂直画走廊

public void FillRoom() //遍历整个 ‘rooms’ 数组，将所有的room铺上Tileset.Floor

void FillWall() //对已经切割好的房间并通过走廊连接好的房间的边缘填充上wall
对每个Tileset.floor调用 'FillSingleBlock()'

辅助方法
private void FillSingleBlock(int x,int y) //对一个TETile周围铺上WALL
如果当前点为Tileset.FLOOR且合法 则对这个TEtile周围遍历一圈

private boolean checkValid(int x,int y) //检查点的合法性


用于实现 ’可持久化‘ 方法：
void saveWorld() //对当前的世界进行存档

void loadWorld() //加载存档


## Rectangle类： //类内维护一个BSP树
类内维护的变量：
1.private static final int MIN_AREA = 50;//BSP算法接受的最小的面积
2.private static final int MINHEIGHT = 15;
3.private static final int MAXWIDTH = 15;
4.private Point bottomleft; 左下角顶点
5.private int width; 宽度
6.private int height; 高度
7.Room room; 矩形对应的room
8.Rectangle leftChild,rightChild; 左子节点和右子节点

类内方法：
public Rectangle(Point bottomleft_,int w,int h) //初始化Rectangle

void generateRoom() //在这个矩形类内生成一个房间赋值给类内的 'Room room' 

void spilt() //根据当前矩形的长宽比，决定是水平切还是垂直切，以避免生成畸形的长条空间。
1.递归终止条件：height*width <=MIN_AREA || height<MINHEIGHT || width<MAXWIDTH
2。 决定切割方向并切割
如果 width / height > 1.25，强制垂直切。
如果 height / width > 1.25，强制水平切。
否则随机切割，切割位置不要在 0-100% 之间随机，建议在 30% - 70% 之间随机，确保子空间大小适中。

辅助方法：
private double getSpiltRatio(Random seed) //根据传入的为随机数生成切割的比例， 30% - 70% 之间
private void splitVertically(int splitLength) 垂直切割 参数为切割的长度
private void spiltHorizontally(int splitLength) 水平切割 参数为切割的长度

public Room generateRoom(Random rand) //根据伪随机数在矩阵内确定一个房间
辅助方法：private double getPaddingRatio(Random rand)

public List<Room> collectRooms(Random rand) //BSP树的叶子节点才是最终分割出的房间
//1 遍历整个BSP树，并找到叶子节点 辅助方法：void collectRoomsHelper(Rectangle rec,List<Rectangle> leafNodes)
//2 根据生成的伪随机数，80%的概率对一个叶子节点使用‘generateRoom’ 方法 得到Rectangle所对应的room并存入待返回的列表

辅助方法：
private void collectRoomsHelper(List<Rectangle> leafNodes)
递归终止条件：1当前节点为null 2当前节点是叶子节点 把当前节点放入 ‘leafNodes' 中 return
对左子树和右子树递归调用函数

private boolean isLeaf() //判断当前节点是否是叶子节点

public Point getAnchor() //返回当前BSP树的锚点
1 如果当前节点是叶子节点并且‘room’变量被实例化 ：随机返回当前房间的中心点或者房间内的随机一点
2 获取左子树以及右子树是否存在房间
3 两边都有房，随机挑一个分支继续深入 || // 只有左边有房 || // 只有右边有房
public boolean hasRoom() //判断当前结点是否有房间
1 如果是叶子节点，检查 ’room‘变量是否被实例化
2 如果不是 只要左子树或右子树中有一个有房间，这个分支就是“活”的

## Room类：
类内维护的变量：
    1.Point bottomLeft //记录这个房间的左下角的坐标
    2.int width //记录房间的宽度
    3.int height //记录房间的高度
    4.Point center //房间的中心点
类内方法：
public Point getCenter() //计算并返回房间的中心点
public Point getPointFromRoom(Random rand) //返回房间内的随机一点

获取类内变量的接口
int getWidth()
int getHeight()
Point getBottomLeft()
---

## **Algorithms**

# *随机地图生成* 算法流程 link：https://zhuanlan.zhihu.com/p/30724817

1. 在给定大小的地图区域内随机生成一些房间：
实现的方法：‘splitWorld()'
2. 将除房间以外的区域用随机生成的迷宫填充:
实现方法：
总控方法 public void FillMaze()
辅助方法 public int GrowMaze() // 从一个合格的节点出发，生成迷宫，直到这段迷宫不能继续生长为止。

3. 将所有房间和迷宫通过少数节点连接起来；
实现方法
public int ConectRegions
4. 删除不必要的迷宫死胡同，降低地图复杂度。

…