public class MineSweeperData {

    public static final String blockImageURL = "resources/block.png";
    public static final String flagImageURL = "resources/flag.png";
    public static final String mineImageURL = "resources/mine.png";
    public static String numberImageURL(int num){
        if(num < 0 || num >= 8)
            throw new IllegalArgumentException("No such a number image!");
        return "resources/" + num + ".png";
    }

    private int N, M;
    private boolean[][] mines;  // 雷
    public boolean[][] open;   // 格子是否被打开
    public boolean[][] flags;   // 位置是否被标记为旗子
    private int[][] numbers;  // 

    // 宽高 多少雷
    public MineSweeperData(int N, int M, int mineNumber){

        if(N <= 0 || M <= 0)
            throw new IllegalArgumentException("Mine sweeper size is invalid!");

        if(mineNumber < 0 || mineNumber > N*M)
            throw new IllegalArgumentException("Mine number is larger than the size of mine sweeper board!");

        this.N = N;
        this.M = M;

        mines = new boolean[N][M];
        open = new boolean[N][M];
        flags = new boolean[N][M];
        numbers = new int[N][M];
        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++){
                mines[i][j] = false;
                open[i][j] = false;
                flags[i][j] = false;
                numbers[i][j] = 0;
            }

        //mines[0][0] = true;
        
        // 布置雷
        this.generateMines(mineNumber);
        //计算雷周围的数字
        this.calculateNumbers();

    }

    public int N(){ return N; }
    public int M(){ return M; }

    public boolean isMine(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("Out of index in isMine function!");
        return mines[x][y];
    }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public int getNumber(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("Out of index in getNumber function!");
        return numbers[x][y];
    }

    //随机生成雷
    // private void generateMines(int mineNumber){

    //     for(int i = 0 ; i < mineNumber ; i ++){
    //         while(true){
    //             int x = (int)(Math.random() * N);
    //             int y = (int)(Math.random() * M);
    //             if(!mines[x][y]){
    //                 mines[x][y] = true;
    //                 break;
    //             }
    //         }
    //     }
    // }

    // 计算雷周围的数字
    private void calculateNumbers(){
        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++){

                // 是雷
                if(mines[i][j])
                    numbers[i][j] = -1;

                numbers[i][j] = 0;
                
                // 计算8个方向
                for(int ii = i-1 ; ii <= i+1 ; ii ++)
                    for(int jj = j-1 ; jj <= j+1 ; jj ++)
                        if(inArea(ii, jj) && isMine(ii, jj))
                            numbers[i][j] ++;
            }

        return;
    }

    // knuth洗牌算法生成雷
    private void generateMines(int mineNumber){
        // 雷顺序排列
        for(int i = 0 ; i < mineNumber ; i ++){
            int x = i/M;    // 行
            int y = i%M;    // 列
            mines[x][y] = true;
        }

        // 从最后一个格子开始循环
        for(int i = N*M-1; i >= 0 ; i-- ){

            // 格子位置
            int iX = i/M;   
            int iY = i%M;

            // 从0到i生成一个随机数字
            int randNumber = (int)(Math.random()*(i+1));

            int randX = randNumber/M;
            int randY = randNumber%M;

            // 交换
            swap(iX, iY, randX, randY);
        }
    }

    private void swap(int x1, int y1, int x2, int y2){
        boolean t = mines[x1][y1];
        mines[x1][y1] = mines[x2][y2];
        mines[x2][y2] = t;
    }

    public void open(int x, int y){

        if(!inArea(x, y))
            throw new IllegalArgumentException("Out of index in open function!");

        if(isMine(x, y))
            throw new IllegalArgumentException("Cannot open an mine block in open function.");

        open[x][y] = true;
        if(getNumber(x, y) == 0){
            //往8个方向拓展
            for(int i = x-1 ; i <= x + 1 ; i ++)
                for(int j = y-1 ; j <= y+1 ;j ++)
                    if(inArea(i, j) && !open[i][j] && !isMine(i, j))
                        open(i, j);
        }
    }
}
