

public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int R, C;
    public char[][] maze;

    private int entranceX, entranceY;
    private int exitX, exitY;

    public boolean[][] visited;     //这个点是否被访问过
    public boolean[][] inMist;



    // 参数 row ， column
    public MazeData(int R,int C) {
        
        if( R%2 == 0 || C%2 == 0)
            throw new IllegalArgumentException("必须是奇数");

        this.R = R;
        this.C = C;

        maze = new char[R][C];
        visited = new boolean[R][C];
        inMist = new boolean[R][C];
        
        // 初始化迷宫内容
        for(int i = 0 ; i < R ; i ++)
            for(int j = 0 ; j < C ; j ++){
                 // 横纵坐标是奇数，road
                 if(i%2 == 1 && j%2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j] = false;
                inMist[i][j] = true;    //初始化所有都在迷雾中
            }
               

        
        // 指定入口出口 ， 入口为第一行的第0列 ， 出口为倒数第一行，倒数第0列
        entranceX = 1;
        entranceY = 0;
        exitX = R - 2;
        exitY = C - 1;

        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;

    }

    
    public int R(){return R;}
    public int C(){return C;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}

    public char getMaze(int i,int j){
        if(!inArea(i,j))
            throw new IllegalArgumentException("i j 越界");
        return maze[i][j];
    }

    // 是否数组越界
    public boolean inArea(int x,int y){
        return x>=0 && x<R && y>=0 && y<C;
    }

    public void openMist(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("x or y is out of index in openMist function!");

        // 这一点的3*3个格子打开迷雾
        for(int i = x-1 ; i <= x+1 ; i ++)
            for(int j = y-1 ; j <= y+1 ; j++)
                if(inArea(i,j))
                    inMist[i][j] = false;

        return;
    }

}