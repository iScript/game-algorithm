import java.awt.*;
import java.util.Stack;
import java.util.LinkedList;

public class Main {
    private static int DELAY = 20;
    private static int blockSide = 8;
    private MazeData data;
    private AlgoFrame frame;
    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};   // 左 下 右 上

    public static void main(String[] args) {
        

        int R = 101;
        int C = 101;

        Main vis = new Main(R,C);
        
    }

    public Main(int R,int C){


        // 初始化数据
        data = new MazeData(R,C);
        int sceneHeight = data.R() * blockSide;
        int sceneWidth = data.C() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Visualization", sceneWidth, sceneHeight);

            // 启动一个新的线程
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){
        
        setData(-1,-1);

        go3(data.getEntranceX(),data.getEntranceY()+1);

        setData(-1,-1);
        // setData(-1,-1,false);
    }

    // 广度优先生成迷宫 
    private void go3(int x,int y){
        RandomQueue2<Position> queue = new RandomQueue2<Position>();
        Position first = new Position(x, y);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());

        while(queue.size() != 0){
            Position curPos = queue.remove();

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.maze[newX][newY] == MazeData.ROAD){
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;

                    data.openMist(newX,newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

    }

    // 深度优先非递归生成迷宫
    private void go2(int x,int y){
        Stack<Position> stack = new Stack<Position>();
        Position first = new Position(x, y);
        stack.push(first);
        data.visited[first.getX()][first.getY()] = true;

        while(!stack.empty()){
            Position curPos = stack.pop();

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;

                if(data.inArea(newX, newY) && !data.visited[newX][newY]){
                    stack.push(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }

    }

    // 深度优先递归生成迷宫
    private void go(int x,int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y 越界!");

        data.visited[x][y] = true;
        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0]*2;   //间隔1个
            int newY = y + d[i][1]*2;
            if(data.inArea(newX, newY) && !data.visited[newX][newY]){
                setData(x + d[i][0], y + d[i][1]);  //间隔之间有个墙，打破墙
                go(newX, newY);
            }
        }

    }

    private void setData(int x,int y){
        
        // if(data.inArea(x, y))
        //     data.path[x][y] = isPath;

        if(data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
}