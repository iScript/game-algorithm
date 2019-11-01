import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    private static int DELAY = 20;
    private static int blockSide = 32;

    private MineSweeperData data;
    private AlgoFrame frame;

    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) {
        
        int N = 20;
        int M = 20;
        int mineNumber = 20;

        Main vis = new Main(N, M, mineNumber);
       
    }

    public Main(int N,int M,int mineNumber){

        data = new MineSweeperData(N, M, mineNumber);
        int sceneWidth = M * blockSide;
        int sceneHeight = N * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Visualization", sceneWidth, sceneHeight);
            frame.addMouseListener(new AlgoMouseListener());
            // 启动一个新的线程
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){
        // frame.render(data);
        // AlgoVisHelper.pause(DELAY);
        setData(false,-1,-1);
    }

    


    private void setData(boolean isLeftClicked,int x,int y){
        // 左键点击开，右键标旗子
        if(isLeftClicked){
            if(data.inArea(x, y))
                if(data.isMine(x, y)){
                    // Game Over
                    data.open[x][y] = true;
                }
                else
                    data.open(x, y);
        }
        else{
            if(data.inArea(x, y))
                data.flags[x][y] = !data.flags[x][y];
        }


        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // 内部类，鼠标事件
    private class AlgoMouseListener extends MouseAdapter{

        // 鼠标点击
        @Override
        public void mouseReleased(MouseEvent event){

            // 点击偏移，  本来是基于窗口的，而不是canvas
            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height - frame.getCanvasHeight())
            );

            Point pos = event.getPoint();

            // w为CanvasWidth/列数
            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();

            int x = pos.y / h;  //行
            int y = pos.x / w;
            // System.out.println(x + " , " + y);

            // 是否左键点击
            if(SwingUtilities.isLeftMouseButton(event))
                setData(true, x, y);
            else if(SwingUtilities.isRightMouseButton(event))
                setData(false, x, y);

        }
    }
}