
public class Board {
    public static char EMPTY = '.';
    private int N, M;   // 行 列
    private char[][] data;
    private Board preBoard = null;
    private String swapString = ""; // 当前的盘面是上一个盘面通过怎样的操作得到的。

    public Board(String[] lines){
        if(lines == null)
            throw new IllegalArgumentException("lines cannot be null in Board constructor.");

        N = lines.length;
        if(N == 0)
            throw new IllegalArgumentException("lines cannot be empty in Board constructor.");

        M = lines[0].length();

        data = new char[N][M];
        for(int i = 0 ; i < N ; i ++){
            if(lines[i].length() != M)
                throw new IllegalArgumentException("All lines' length must be same in Board constructor.");
            for(int j = 0 ; j < M ; j ++)
                data[i][j] = lines[i].charAt(j);
        }
    }

    // 
    public Board(Board board, Board preBoard, String swapString){
        if(board == null)
            throw new IllegalArgumentException("board can not be null in Board constructor!");

        this.N = board.N;
        this.M = board.M;
        this.data = new char[N][M];
        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++)
                this.data[i][j] = board.data[i][j];

        // 用于回溯
        this.preBoard = preBoard;
        this.swapString = swapString;
    }

    public Board(Board board){
        this(board, null, "");
    }

    public char getData(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("x, y are out of index in getData!");

        return data[x][y];
    }

    public int N(){ return N; }
    public int M(){ return M; }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print(){
        for(int i = 0 ; i < N ; i ++)
            System.out.println(String.valueOf(data[i]));
    }

    // 如果都是empty ， 则win
    public boolean isWin(){

        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++)
                if(data[i][j] != EMPTY)
                    return false;


        // 打印出步骤
        printSwapInfo();

        return true;
    }

    public void swap(int x1, int y1, int x2, int y2){

        if(!inArea(x1, y1) || !inArea(x2, y2))
            throw new IllegalArgumentException("x, y are out of index in swap!");

        char t = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = t;

        return;
    }

    public void run(){
        //System.out.println("run");

        // 先处理掉落，再判断是否存在消除。
        do{
            drop();
        }while(match());

        return;
    }

    // 遍历右侧和下侧  。  有3个相同的就标记。 没有左和上因为如果有相同的右上已经标记过了。
    private static int d[][] = {{0, 1}, {1, 0}};
    private boolean match(){

        boolean isMatched = false;

        //用于标记可消除
        boolean tag[][] = new boolean[N][M];
        
        // 遍历所有格子
        for(int x = 0 ; x < N ; x ++)
            for(int y = 0 ; y < M ; y ++)
                if(data[x][y] != EMPTY)
                    // 遍历右侧和下侧
                    for(int i = 0 ; i < 2 ; i ++){
                        // 相邻2个
                        int newX1 = x + d[i][0];
                        int newY1 = y + d[i][1];
                        int newX2 = newX1 + d[i][0];
                        int newY2 = newY1 + d[i][1];
                        
                        // 判断3个格子是否相同，相同则标记
                        if(inArea(newX1, newY1) && inArea(newX2, newY2)
                            && data[x][y] == data[newX1][newY1] && data[x][y] == data[newX2][newY2]){

                            tag[x][y] = true;
                            tag[newX1][newY1] = true;
                            tag[newX2][newY2] = true;

                            isMatched = true;
                        }
                    }
        
        // 将标记的消除
        for(int x = 0 ; x < N ; x ++)
            for(int y = 0 ; y < M ; y ++)
                if(tag[x][y])
                    data[x][y] = EMPTY;

        return isMatched;
    }

    // 处理掉落
    private void drop(){

        // 遍历每一列
        for(int j = 0 ; j < M ; j ++){
            //cur为当前要掉落的位置
            int cur = N-1;
            //从底向上遍历
            for(int i = N-1 ; i >= 0 ; i --)
                //如果不为空，就掉落到cur这个位置。 如果为空，i继续下一个，cur不变，直到不为空有箱子，掉落到cur。cur-- 往上
                if(data[i][j] != EMPTY){
                    data[cur][j] = data[i][j];
                    cur--;  // 当前有箱子的index
                }
            
            for(; cur >= 0 ; cur --)
                data[cur][j] = EMPTY;
        }

        return;
    }

    public void printSwapInfo(){

        if(preBoard != null)
            preBoard.printSwapInfo();
        System.out.println(swapString);
        return;
    }
}
