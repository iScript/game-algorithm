import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class GameData {

    private int maxTurn;
    private int N, M;
    private Board starterBoard;
    private Board showBoard;

    public GameData(String filename){

        if(filename == null)
            throw new IllegalArgumentException("Filename cannot be null!");

        Scanner scanner = null;
        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File " + filename + " doesn't exist!");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String turnline = scanner.nextLine();

            // 第一行  当前问题最多要多少回合解决
            this.maxTurn = Integer.parseInt(turnline);

            ArrayList<String> lines = new ArrayList<String>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }
            starterBoard = new Board(lines.toArray(new String[lines.size()]));

            this.N = starterBoard.N();
            this.M = starterBoard.M();

            starterBoard.print();

            showBoard = new Board(starterBoard);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if(scanner != null)
                scanner.close();
        }
    }

    public boolean solve(){

        if(maxTurn <= 0)
            return false;

        return solve(new Board(starterBoard), maxTurn);
    }

    private static int d[][] = {{1, 0}, {0, 1}, {0,-1}};   // 下 右 左 ， 没有往上，情况1上面空的无法向上，情况2上面有箱子可以上面的箱子向下移动解决
    private boolean solve(Board board, int turn){

        if(board == null)
            throw new IllegalArgumentException("board can not be null in solve function!");

        if(turn == 0)
            return board.isWin();

        if(board.isWin())
            return true;

        // 遍历所有箱子
        for(int x = 0 ; x < N ; x ++)
            for(int y = 0 ; y < M ; y ++)
                if(board.getData(x, y) != Board.EMPTY){
                    // 每个箱子都3个方向尝试
                    for(int i = 0 ; i < 3 ; i ++){
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];
                        
                        if(inArea(newX, newY)){
                            String swapString = String.format("swap (%d, %d) and (%d, %d)", x, y, newX, newY);
                            Board nextBoard = new Board(board,board,swapString);
                            nextBoard.swap(x, y, newX, newY);   
                            nextBoard.run();    // 模拟
                            if(solve(nextBoard, turn-1))
                                return true;
                        }
                    }
                }

        return false;
    }

    public int N(){ return N; }
    public int M(){ return M; }

    public Board getShowBoard(){ return showBoard;}

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
