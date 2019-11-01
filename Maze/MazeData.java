import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int R,C;    // row column
    private char[][] maze;

    private int entranceX, entranceY;
    private int exitX, exitY;

    public boolean[][] path;        // 已经走过的路径存储
    public boolean[][] visited;     //这个点是否被访问过
    public boolean[][] result;     //这个点是否被访问过

    public MazeData(String filename) {
        
        if(filename == null) throw new IllegalArgumentException("文件不能为空");

        Scanner scanner = null;

        try{
            File file = new File(filename);
            if(!file.exists()) throw new IllegalArgumentException("文件不存在");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis),"UTF-8");

            // 读取一行
            String line1 = scanner.nextLine();
            String[] rc = line1.trim().split("\\s+");

            // 第一行有2个数字，代表数组行列
            R = Integer.parseInt(rc[0]);
            C = Integer.parseInt(rc[1]);

            maze  = new char[R][C];
            visited = new boolean[R][C];
            path = new boolean[R][C];
            result = new boolean[R][C];

            for(int i=0;i<R;i++){   //处理每一行
                String line = scanner.nextLine();
                if(line.length() != C) throw new IllegalArgumentException("该行有误");

                for(int j=0;j<C;j++){   //处理这行的每一列
                    maze[i][j] = line.charAt(j);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(scanner != null) scanner.close();
        }

        entranceX = 1;
        entranceY = 0;
        exitX = R - 2;
        exitY = C - 1;

    }

    public int R() {return R;}
    public int C() {return C;}
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

    public void print(){
        System.out.println(R+ " " +C);

        for (int i=0;i<R;i++){
            for(int j=0;j<C;j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();//换行
        }
        return;
    }
}