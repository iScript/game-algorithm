import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    private static int DELAY = 5;
    private static int blockSide = 80;

    private GameData data;
    private AlgoFrame frame;

    public static void main(String[] args) {
        
       String filename = "boston_09.txt";
       Main vis = new Main(filename);
    }

    public Main(String filename){
        data = new GameData(filename);
        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Move the Box Solver", sceneWidth,sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
       
    }

    public void run(){
        // frame.render(data);
        // AlgoVisHelper.pause(DELAY);
        setData();
        
        if(data.solve())
            System.out.println("The game has a solution!");
        else
            System.out.println("The game does NOT have a solution.");

        setData();
    }

    


    private void setData(){
       


        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // // 内部类，鼠标事件
    // private class AlgoMouseListener extends MouseAdapter{

    //     // 鼠标点击
    //     @Override
    //     public void mouseReleased(MouseEvent event){

    //         // 点击偏移，  本来是基于窗口的，而不是canvas
    //         event.translatePoint(
    //                 -(int)(frame.getBounds().width - frame.getCanvasWidth()),
    //                 -(int)(frame.getBounds().height - frame.getCanvasHeight())
    //         );

    //         Point pos = event.getPoint();

    //         // w为CanvasWidth/列数
    //         int w = frame.getCanvasWidth() / data.M();
    //         int h = frame.getCanvasHeight() / data.N();

    //         int x = pos.y / h;  //行
    //         int y = pos.x / w;
    //         // System.out.println(x + " , " + y);

    //         // 是否左键点击
    //         if(SwingUtilities.isLeftMouseButton(event))
    //             setData(true, x, y);
    //         else if(SwingUtilities.isRightMouseButton(event))
    //             setData(false, x, y);

    //     }
    // }
}