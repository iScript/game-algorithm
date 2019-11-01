import javax.swing.*;
import java.awt.*;
//import java.awt.geom.Ellipse2D;

public class AlgoFrame extends JFrame{
    
    private int canvasWidth;
    private int canvasHeight;
    
    public AlgoFrame(String title,int canvasWidth,int canvasHeight){
        
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        

        // jpanel 主内容
        AlgoCanvas canvas = new AlgoCanvas();

        // 
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        this.setContentPane(canvas);
        pack(); // 自动根据panel调整窗口

        //this.setSize(canvasWidth,canvasHeight);
        this.setResizable(false);  // 用户不可改变大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 点x关闭
        this.setVisible(true);


    }

    public int getCanvasWidth(){return this.canvasWidth;}
    public int getCanvasHeight(){return this.canvasHeight;}

    // data
    private MazeData data;
    public void render(MazeData data){
        this.data = data;
        repaint();  // 刷新jframe控件
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        // 显示标示该函数是覆盖函数，不写override也可以，自动检测父类是否有
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;
            
            //System.out.println("paint");

            //抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            int w = canvasWidth/data.C();
            int h = canvasHeight/data.R();
            
            for(int i = 0 ; i < data.R() ; i ++ )
                for(int j = 0 ; j < data.C() ; j ++){
                    
                    // 是否在迷雾中
                    if ( data.inMist[i][j] ){
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
                    }
                    //标示迷宫墙
                    else if ( data.maze[i][j] == MazeData.WALL){
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                    //标示迷宫路
                    }else{
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                    }

                    AlgoVisHelper.fillRectangle(g2d, j * w, i * h, w, h);
                }
       
            
        }

        // 返回画布大小 , 设置画布大小无效？
        // @Override
        // public Dimension getPreferredSize(){
        //     //System.out.println(canvasWidth);
        //     return new Dimension(canvasWidth,canvasHeight);
        // }


       

    }
}