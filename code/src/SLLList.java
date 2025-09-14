public class SLLList {
    public class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int f, IntNode node){
            item =f;
            next = node;
        }
    }


    private IntNode sentinel;
    private int size;

    public SLLList(int x){
        sentinel = new IntNode(0,null);
        sentinel.next= new IntNode(x,null);
        size = 1;
    }

    public SLLList(){
        sentinel = new IntNode(0,null);
        size = 0;
    }

    public void addFirst(int x){
        sentinel.next = new IntNode(x,sentinel.next);
        size+=1;
    }

    public int getFirst(){
        return sentinel.next.item;
    }

    public void addLast(int x){
        IntNode p = sentinel;
        size+=1;

        while(p.next!=null){
            p = p.next;
        }
        p.next= new IntNode(x,null);

    }

    public int size(){
        return size;
    }

    private int size(IntNode p){
        if(p.next==null) return 1;
        else{
            return 1+size(p.next);
        }
    }
    public static void main(String[] args){
        SLLList L = new SLLList(5);
        L.addFirst(10);
        L.addFirst(15);
        L.addLast(15);
        System.out.println(L.size());
    }
}
