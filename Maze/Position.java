public class Position{
    private int x,y ;
    private Position prev;  //当前position从哪个position来的

    public Position(int x,int y,Position prev){
        this.x = x;
        this.y = y;
        this.prev = prev;

    }

    public Position(int x,int y){
        this.x = x;
        this.y = y;
        this.prev = null;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public Position getPrev(){return prev;}
}