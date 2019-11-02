import javax.swing.*;
import java.awt.*;
import java.util.*;
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
    private GameData data;
    public void render(GameData data){
        this.data = data;
        repaint();  // 刷新jframe控件
    }

    private class AlgoCanvas extends JPanel{

        private ArrayList<Color> colorList;
        private HashMap<Character, Color> colorMap;
        public AlgoCanvas(){
            // 双缓存
            super(true);

            colorList = new ArrayList<Color>();
            colorList.add(AlgoVisHelper.Red);
            colorList.add(AlgoVisHelper.Purple);
            colorList.add(AlgoVisHelper.Blue);
            colorList.add(AlgoVisHelper.Teal);
            colorList.add(AlgoVisHelper.LightGreen);
            colorList.add(AlgoVisHelper.Lime);
            colorList.add(AlgoVisHelper.Amber);
            colorList.add(AlgoVisHelper.DeepOrange);
            colorList.add(AlgoVisHelper.Brown);
            colorList.add(AlgoVisHelper.BlueGrey);

            colorMap = new HashMap<Character, Color>();
        }

        // 显示标示该函数是覆盖函数，不写override也可以，自动检测父类是否有
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;
            

            //抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            // 具体绘制
            int w = canvasWidth/data.M();
            int h = canvasHeight/data.N();

            Board showBoard = data.getShowBoard();

            for(int i = 0 ; i < showBoard.N() ; i ++)
                for(int j = 0 ; j < showBoard.M() ; j++){
                    char c  = showBoard.getData(i, j);
                    if(c != Board.EMPTY){

                        // 如果当前不包含，把新的颜色放到colormap中， 如最开始size为0。
                        if(!colorMap.containsKey(c)){
                            int sz = colorMap.size();
                            colorMap.put(c, colorList.get(sz));
                        }

                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j*h+2, i*w+2, w-4, h-4);

                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        String text = String.format("( %d , %d )", i, j);
                        AlgoVisHelper.drawText(g2d, text, j*h + h/2, i*w + w/2);

                    }
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