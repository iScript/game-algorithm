import java.awt.*;
import java.util.Stack;
import java.util.LinkedList;

public class Main {
    
    private static int DELAY = 20;
    private static int blockSide = 8;

    private MazeData data;
    private AlgoFrame frame;

    // 左 下 右 上
    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};
    
    public static void main(String[] args) {
        String mazeFile = "maze_101_101.txt";
        Main vis = new Main(mazeFile);
        
    }

    public Main(String mazeFile){
        //String mazeFile = "maze_101_101.txt";
        //MazeData data = new MazeData(mazeFile);
        //data.print();

        // 初始化数据
        data = new MazeData(mazeFile);
        int sceneHeight = data.R() * blockSide;
        int sceneWidth = data.C() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            // 启动一个新的线程
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){
        
        setData(-1,-1,false);

        // 进入入口，开始求解迷宫
        // if(!go(data.getEntranceX(), data.getEntranceY())){
        //     System.out.println("迷宫无解");
        // }

        // if(!go2(data.getEntranceX(), data.getEntranceY())){
        //     System.out.println("迷宫无解");
        // }

        if(!go3(data.getEntranceX(), data.getEntranceY())){
            System.out.println("迷宫无解");
        }

        setData(-1,-1,false);
    }

    //广度优先求解迷宫 
    private boolean go3(int x,int y){
        
        System.out.println("go3"); 

        // 队列
        LinkedList<Position> queue = new LinkedList<Position>();
        Position entrance = new Position(x,y);
        queue.addLast(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;
        
        boolean isSolved  = false;

        while(queue.size() != 0){
            Position curPos = queue.pop();// 后进先出，返回出的值，stack里少一个值
            setData(curPos.getX(),curPos.getY(),true);

            // 如果是出口 ，break
            if(curPos.getX() == data.getExitX() && curPos.getY()== data.getExitY()) {
                isSolved = true;
                
                //如果有解，回溯正确的路径
                findPath(curPos);
                break;
            }

            for(int i= 0;i<4;i++){
                int newX = curPos.getX()+ d[i][0];
                int newY = curPos.getY()+ d[i][1];

                if(data.inArea(newX, newY) && data.getMaze(newX,newY) == MazeData.ROAD && !data.visited[newX][newY]){
                    
                    queue.addLast(new Position(newX,newY,curPos)); //压入栈，并记录来源
                    data.visited[newX][newY] = true;
                }
            }
        }

        
        return true;
    }


    // 非递归栈方式求解迷宫
    private boolean go2(int x, int y){
        System.out.println("go2"); 
        // 创建栈
         Stack<Position> stack = new Stack<Position>();
         Position entrance = new Position(x,y);
         stack.push(entrance);
         data.visited[entrance.getX()][entrance.getY()] = true;
        
        boolean isSolved  = false;

        while(!stack.empty()){
            Position curPos = stack.pop();// 后进先出，返回出的值，stack里少一个值
            setData(curPos.getX(),curPos.getY(),true);

            // 如果是出口 ，break
            if(curPos.getX() == data.getExitX() && curPos.getY()== data.getExitY()) {
                isSolved = true;
                
                //如果有解，回溯正确的路径
                findPath(curPos);
                
                break;
            }

            for(int i= 0;i<4;i++){
                int newX = curPos.getX()+ d[i][0];
                int newY = curPos.getY()+ d[i][1];

                if(data.inArea(newX, newY) && data.getMaze(newX,newY) == MazeData.ROAD && !data.visited[newX][newY]){
                    
                    stack.push(new Position(newX,newY,curPos)); //压入栈，并记录来源
                    data.visited[newX][newY] = true;
                }
            }
        }

        return isSolved;

        //if(!isSolved) System.out.println("迷宫无解");
 
    }

    // 往回回溯
    private void findPath(Position des){
        Position cur = des;
        while(cur != null){
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

    // 深度优先递归求解
    // x,y点求解迷宫，返回是否求解成功
    private boolean go(int x, int y){

        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y 越界!");

        data.visited[x][y] = true;
        setData(x, y,true);

        // 是否终点
        if(x == data.getExitX() && y == data.getExitY())
            return true;


        // 遍历4个方向 
        // 不断递归，到了分岔路，先遍历一条路直到尽头，再继续分岔路的另一个没走过的口
        // 如到了2个路口的， for go(1)  go(2)   , go(1) 遇到分岔路继续 go(1.1)..
        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            
            if(data.inArea(newX, newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY])
                if(go(newX, newY)){
                    return true;
                    // 正确的路不执行下面的清除
                }
        }


       
        // 回溯， 判断 上下左右都没路，即走过了或是墙，当前格子清除
        //     ______
        //  ---|
        //     -----
        // 如这样的一个路口2条路，先循环上面的每个格子，一直到头，没路，清除
        // 这个for循环结束，相当于子for循环结束，然后父for循环结束
        setData(x,y,false);

        return false;
    }

    private void setData(int x,int y,boolean isPath){
        
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
}