# **Project 2B/C: Design Document**

| Name | class id | Email |
| ----- | ----- | ----- |
| Your Name | sp24-s\*\*\* | your.email@berkeley.edu |

## **Data Structures**

Data structures you are planning to use and why. Include any potential helper classes here. If you think something will require code repetition, state how you will mitigate it.

Example (if this was for HW2):

* I will use the boolean\[\]\[\] 2D array to keep track of the number of sites opened.  
* I will use the WQU data structure to connect my sites together.

World类：
类内维护的变量：
    1.使用TETile[][] grid 二维数组表示世界里的block
    2.int HEIGHT 表示世界的高度 60个图块单位
    3.int WIDTH  表示世界的宽度 100个图块单位
    4.Random random 使用seed来初始化他
    5. List<Room> rooms 记录切割好的房间，方便后续进行连接

类内方法:
void getWorldFromInput(String input) //处理"N#######S"格式的输入
处理输入的字符串，调用generate方法

void generate() //总控方法，在该方法内调用现有的方法来生成随机世界
1.初始化 ‘grid’ 为Tileset.Nothing
2. 调用 ‘spiltWorld()’ 构建BSP树并填充 ‘rooms’列表
3.遍历 ‘rooms’ 将地板填入grid
4. 调用‘connetRoom’ 方法 画出走廊
5. 调用‘FillWall'方法 在地板边缘自动生成墙壁

void spiltWorld() //将目前的世界分割为若干个合理大小的小房间并存在 ‘List<Room> rooms’ 里
1.创建一个包含整个地图的初始 Rectangle。
2.将其放入一个列表。
3.循环取出矩形进行 split()，直到矩形数量足够或尺寸达到下限。
4.遍历最终的所有矩形，在每个矩形里使用 ‘generateRoom’方法。

void connectRoom() //将小房间通过走廊连接起来，走廊的宽度为1个图块单位，可生成转弯走廊


void FillWall() //对已经切割好的房间并通过走廊连接好的房间的边缘填充上wall
对每个Tileset.floor调用 'FillSingleBlock()'

辅助方法FillSingleBlock()
遍历一个Block周围一圈的合法TETile，如果是Tileset.Nothing 则改为Tileset.Wall

用于实现 ’可持久化‘ 方法：
void saveWorld() //对当前的世界进行存档

void loadWorld() //加载存档


Rectangle类： //类内维护一个BSP树
类内维护的变量：
    1.Point bottomleft //矩形的左下顶点
    2.int width //宽度
    3.int height //高度
    4.Room room //这个矩形类内的房间
    5.Rectangle leftChild,rightChild //用于辅助BSP算法分割子区域

类内方法：
void generateRoom() //在这个矩形类内生成一个房间赋值给类内的 'Room room' 

void spilt() //根据当前矩形的长宽比，决定是水平切还是垂直切，以避免生成畸形的长条空间。
如果 width / height > 1.25，强制垂直切。
如果 height / width > 1.25，强制水平切。
切割位置不要在 0-100% 之间随机，建议在 30% - 70% 之间随机，确保子空间大小适中。

void collectRooms(Rectangle rectangle) //BSP树的叶子节点才是最终分割出的房间
//1 遍历整个BSP树，并找到叶子节点 辅助方法：void collectRoomsHelper(Rectangle rec,List<Rectangle> leafNodes)
//2 对每个叶子节点使用‘generateRoom’ 方法 得到Rectangle所对应的room并存入待返回的列表
//3 
/

Room类：
类内维护的变量：
    1.Point bottomLeft //记录这个房间的左下角的坐标
    2.int width //记录房间的宽度
    3.int height //记录房间的高度
    4.Point center //房间的中心点
类内方法：
void getCenter() //计算房间的中心点
---

## **Algorithms**

Pseudocode and general logic behind your implementation, and why. Include any potential helper methods here. If you think something will require code repetition, state how you will mitigate it.

**Hyponyms (single-word case)**

* I will … in order to ...

**Hyponyms (multi-word case)**

* …

**Common Ancestors (single-word case)**

* …

**Common Ancestors (multi-word case)**

*  …

**k \> 0**

* …

Example (if this was for HW2):

* I will have virtual sites that will allow me to have quicker runtimes because I don’t need to check every single cell in the top or bottom row for connectedness.  
* I will create a `xyto1D` helper function for my implementation to make it easier to convert between the 2D grid coordinates and the 1D coordinates of the WQU. This ensures that I don’t have to repeat myself as much across my implementation in this coordinate conversion. Some pseudocode is shown below:

```py
xyto1D(int x, int y):
Converts the x and y coordinates inputted into a 1D coordinate that can be used in the WQU. This is done by doing...
```

1.

…