public class SLLList<Pineapple> {

    public class IntNode {
        public Pineapple item;
        public IntNode next;

        public IntNode(Pineapple f, IntNode node){
            item =f;
            next = node;
        }
    }


    private IntNode sentinel;
    private int size;

    public SLLList(Pineapple x){
        sentinel = new IntNode(null,null);
        sentinel.next= new IntNode(x,null);
        size = 1;
    }

    public SLLList(){
        sentinel = new IntNode(null,null);
        size = 0;
    }

    public void addFirst(Pineapple x){
        sentinel.next = new IntNode(x,sentinel.next);
        size+=1;
    }

    public Pineapple getFirst(){
        return sentinel.next.item;
    }

    public void addLast(Pineapple x){
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
        SLLList<String> L = new SLLList<String>("What");
        L.addFirst("do");
        L.addFirst("dog");
        L.addLast("doing");
        System.out.println(L.getFirst());
    }
}
