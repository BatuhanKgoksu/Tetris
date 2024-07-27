package tetris;

import java.awt.Graphics;
import java.awt.Color;


public class Shape {
    public static final int BOARD_WİDTH=10; 
    public static final int BOARD_HEIGHT=20; 
    public static final int BLOCK_SIZE =30;
    private int x=4,y=0;
    private int normal =600;
    private int fast = 50;
    private int delayTimeForMovement=normal;
    private long beginTime;

    private int deltaX=0;
    private boolean collision = false;

    private int[][] coords;
    private Board board;
    private Color color;

    public Shape(int[][] coords,Board board,Color color){
        this.coords = coords;
        this.board = board;
        this.color = color;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void reset(){
        this.x=4;
        this.y=0;
        collision = false;
    }

    public void update(){
        if(collision){
        for(int row=0;row<coords.length;row++){
            for(int col=0;col<coords[0].length;col++){
                if(coords[row][col]!=0){
                    board.getBoard()[y + row][x + col]= color;
                }
            }
        }
        checkLine();
        board.addScore();

        board.setCurrentShape();  

            return;
        }

        boolean moveX=true;
        if(!(x+deltaX+coords[0].length > 10) && !(x+deltaX<0)){
            for(int row=0;row<coords.length;row++){
                for(int col=0;col<coords[row].length;col++){
                    if(coords[row][col]!=0){
                    if(board.getBoard()[y+row][x+deltaX+col]!=null){
                        moveX = false;
                    }
                }
                }
            } 
            if(moveX){   
            x+=deltaX;
            }
        
        }
        
        
        deltaX=0;
        
        
        if(System.currentTimeMillis() - beginTime>delayTimeForMovement){
            if(!(y + 1 + coords.length > BOARD_HEIGHT)){
               for(int row=0;row<coords.length;row++){
                for(int col=0;col<coords[row].length;col++){
                    if(coords[row][col]!=0){
                        if(board.getBoard()[y+1+row][x+deltaX+col]!=null){
                            collision=true;
                        }
                    }
                }
               }
               if(!collision){
                y++; 
               }
            }
            else{
                collision=true;
            }
            
            beginTime = System.currentTimeMillis();
        }
    }
    

    private void checkLine(){
        int bottonLine = board.getBoard().length-1;
        for(int topLine = board.getBoard().length-1;topLine>0;topLine--){
            int count = 0;
            for(int col = 0; col < board.getBoard()[0].length;col++){
                if(board.getBoard()[topLine][col]!=null){
                    count++;
                }
                board.getBoard()[bottonLine][col] = board.getBoard()[topLine][col];

            }
            if(count <board.getBoard()[0].length){


                bottonLine--;
            }
        }
    }

    public void rotateShape(){
        int[][] rotatedShape = transpozeMatrix(coords);
        reverseRows(rotatedShape);
        
        if((x+rotatedShape[0].length >BOARD_WİDTH)||(y+rotatedShape.length>20)){
            return;
        }


        for(int row=0;row <rotatedShape.length;row++){
            for(int col=0;col<rotatedShape[row].length;col++){
                if(rotatedShape[row][col]!=0){
                    if(board.getBoard()[y+row][x+col]!=null){
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    private int[][] transpozeMatrix(int[][] matrix){
    int[][] temp = new int[matrix[0].length][matrix.length];
    for(int row=0;row<matrix.length;row++){
        for(int col=0; col<matrix[0].length;col++){
            temp[col][row]= matrix[row][col];

        }
    }
    return temp;


    }


    private void reverseRows(int[][] matrix){
        int middle = matrix.length /2;
        for(int row=0;row<middle;row++){
            int[] temp = matrix[row];
            matrix[row]=matrix[matrix.length-row-1];
            matrix[matrix.length-row-1]=temp;
        }

    }
    
    public void render(Graphics g){
        for(int row=0;row<coords.length;row++){
            for(int col=0;col<coords[0].length;col++){
                if(coords[row][col]!=0) {
                    g.setColor(color);
                    g.fillRect(col *BLOCK_SIZE + x*BLOCK_SIZE, row*BLOCK_SIZE+ y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
    public int[][] getcoords(){
        return coords;
    }
    public void speedUp(){
        delayTimeForMovement=fast;
    }
    public void speedDown(){
        delayTimeForMovement=normal;
    }
    public void moveRight(){
        deltaX=1;
    }
    public void moveLeft(){
        deltaX=-1;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    

}
