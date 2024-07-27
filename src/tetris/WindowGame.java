package tetris;

import javax.swing.JFrame;


public class WindowGame {
    public static final int WIDTH = 445;
    public static final int HEIGHT = 629;

    private JFrame window;
    private Board board;
 

    
    
    
    public WindowGame(){
        window = new JFrame("TETRİS GAMEEE");
        window.setSize(445, 629);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);//uygulamanın ekranın ortasında açılmasını sağlar
        board = new Board();
 
        window.add(board);
        window.addKeyListener(board);
   

        
        


        window.setVisible(true);
      
    }


    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        new WindowGame();
    }
}
