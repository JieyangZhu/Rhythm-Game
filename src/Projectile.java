import bagel.DrawOptions;
import bagel.Image;

public class Projectile {
    private final bagel.Image Image = new Image("res/arrow.png");
    private final DrawOptions DrawOptions = new DrawOptions();
    private double distance = Integer.MAX_VALUE;
    private double x;
    private double y;
    private int Disappear = -1;
    private int count;
    private double[][] arrows = new double[0][3];
    private final static int RADIANS = 0;
    private final static int X_POSITION = 1;
    private final static int Y_POSITION = 2;
    private final static int RESET = 0;
    private final static int SQUARE = 2;
    private final static double ROOT = 0.5;
    private final static int DISAPPEAR_RESET = -1;
    private final static int START_X = 800;
    private final static int START_Y = 600;
    private final static int OUT = 1024;
    private final static int PIXELS = 6;
    private final static int DISTANCE = 62*62;
    private final static int ENEMY_X = 0;
    private final static int ENEMY_Y = 1;
    /**
     * Used to attack the enemy
     */
    public Projectile() {
    }

    /**
     * Used to reset for a new game
     */
    public void reset(){
        double [][] newarray = new double[0][3];
        arrows = newarray;
        count = RESET;
        Disappear = DISAPPEAR_RESET;
    }

    /**
     * Used to record each arrow to the 2D array
     * @param enemy is used to find the nearest enemy to shoot arrow
     */
    public void fire(int[][] enemy){
        for(int i =0; i< enemy.length;i++){
            if(enemy[i][ENEMY_X]!=0){
                //find the closest enemy
                if(Math.pow(Math.pow(START_X-enemy[i][ENEMY_X],SQUARE)
                        +Math.pow(START_Y-enemy[i][ENEMY_Y],SQUARE),ROOT)<distance){
                    distance = Math.pow(Math.pow(START_X-enemy[i][ENEMY_X],SQUARE)
                            +Math.pow(START_Y-enemy[i][ENEMY_Y],SQUARE),ROOT);
                    x = enemy[i][ENEMY_X];
                    y = enemy[i][ENEMY_Y];
                }
            }
        }
        distance = Integer.MAX_VALUE;
        //when the array is full, add a new space
        if(arrows.length-1 < count){
            double [][] newarray = new double[arrows.length+1][3];
            for(int j = 0; j<arrows.length;j++){
                for (int k = 0; k<arrows[RADIANS].length;k++){
                    newarray[j][k] = arrows[j][k];
                }
            }
            arrows = newarray;
        }
        //when there is enemy, need to shoot
        if(x != 0){
            arrows[count][RADIANS] = Math.atan((y-START_Y)/(x-START_X));
            arrows[count][X_POSITION] = START_X;
            arrows[count][Y_POSITION] = START_Y;
            x = RESET;
            y = RESET;
            count++;
        }
    }


    /**
     * Used to draw the arrows and update their position
     */
    public void draw(){
        for(int i =0; i<arrows.length;i++){
            //Draw the arrows
            if(arrows[i][RADIANS] != 0){
                if(arrows[i][RADIANS]>0){
                    DrawOptions.setRotation((Math.PI)+arrows[i][RADIANS]);
                    Image.draw(arrows[i][X_POSITION],arrows[i][Y_POSITION],DrawOptions);
                    arrows[i][X_POSITION] -= PIXELS*Math.cos(arrows[i][RADIANS]);
                    arrows[i][Y_POSITION] -= PIXELS*Math.sin(arrows[i][RESET]);
                }
                if(arrows[i][RADIANS]<0){
                    DrawOptions.setRotation(arrows[i][RADIANS]);
                    Image.draw(arrows[i][X_POSITION],arrows[i][Y_POSITION],DrawOptions);
                    arrows[i][X_POSITION] += PIXELS*Math.cos(arrows[i][RADIANS]);
                    arrows[i][Y_POSITION] += PIXELS*Math.sin(arrows[i][RADIANS]);
                }
                if(arrows[i][X_POSITION] <0 || arrows[i][X_POSITION] >OUT || arrows[i][Y_POSITION]<0){
                    arrows[i][RADIANS] = RESET;
                }
            }
        }
    }

    /**
     * Used to get which enemy has been shot
     * @return the enemy number
     */
    public int getDisappear() {
        return Disappear;
    }

    /**
     * Used to compare each arrow and each enemy and check out which enemy will be shot
     * @param enemies is used to compare
     */
    public void biu(int[][] enemies){
        for(int i = 0; i<arrows.length;i++){
            if(arrows[i][RADIANS] != 0){
                for(int j = 0; j< enemies.length;j++){
                    if(enemies[j][ENEMY_X] !=0){
                        //find out if there is enemy be shot
                        if(Math.pow(arrows[i][X_POSITION]-enemies[j][ENEMY_X],SQUARE)
                                +Math.pow(arrows[i][Y_POSITION]-enemies[j][ENEMY_Y],SQUARE) <= DISTANCE){
                            arrows[i][RADIANS] = RESET;
                            Disappear = j;
                        }
                    }
                }
            }
        }
    }
}
