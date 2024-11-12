import bagel.Image;

import java.util.Random;
public class Enemy {
    //Enemy position
    Random random = new Random();
    private final static int X_min = 100;
    private final static int X_max = 900;
    private final static int Y_min = 100;
    private final static int Y_max = 500;
    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private static int[][] enemies = new int[0][3];
    private final static int X_POSITION = 0;
    private final static int Y_POSITION = 1;
    private final static int Direct = 2;
    private static int EnemiesCount = 0;
    private final bagel.Image Image = new Image("res/enemy.png");
    private final static int ENEMY_SHOW_TIME = 600;
    private final static int RESET = 0;

    /**
     * Used to make some enemy in game
     */
    public Enemy() {
    }

    /**
     * Used to reset when start a game
     */
    public void reset(){
        int [][] newarray = new int[0][3];
        enemies = newarray;
        EnemiesCount =RESET;
    }

    /**
     * Used to create an enemy each 600 frames
     * @param time is the game frames
     */
    public void RANDOM(int time){
        //Each 600 frames create an enemy
        if(time%ENEMY_SHOW_TIME ==0 && time != 0){
            //If the array is full, add a new lane
            if(enemies.length-1 < EnemiesCount){
                int [][] newarray = new int[enemies.length+1][3];
                for(int j = 0; j<enemies.length;j++){
                    for (int k = 0; k<enemies[0].length;k++){
                        newarray[j][k] = enemies[j][k];
                    }
                }
                enemies = newarray;
            }
            //Add a new enemy into the array
            enemies[EnemiesCount][X_POSITION] = random.nextInt(X_max-X_min+1)+X_min;
            enemies[EnemiesCount][Y_POSITION] = random.nextInt(Y_max-Y_min+1)+Y_min;
            enemies[EnemiesCount][Direct] = random.nextInt(2);
            EnemiesCount++;
        }
    }

    /**
     * Used to draw the enemies and update their position
     */
    public void EnemyDraw(){
        //Draw the enemy
        for(int i = 0;i<enemies.length;i++){
            if(enemies[i][X_POSITION] !=0){
                Image.draw(enemies[i][X_POSITION],enemies[i][Y_POSITION]);
                //As the enemy move to boundary and change direct
                if(enemies[i][Direct] == 0){
                    enemies[i][X_POSITION]-=1;
                    if(enemies[i][X_POSITION]==X_min){
                        enemies[i][Direct] = RIGHT;
                    }
                }else{
                    enemies[i][X_POSITION] += 1;
                    if(enemies[i][X_POSITION] == X_max){
                        enemies[i][Direct] = LEFT;
                    }
                }
            }
        }
    }

    /**
     * Used to mark which enemy has been shot
     * @param disappear is from the compare of projectile
     */
    public void BiuBiu(int disappear){
        if(disappear != -1){
            enemies[disappear][X_POSITION] = RESET;
        }
    }

    /**
     * Used to get the enemy array to compare each position of arrow
     * @return the 2D array to compare
     */
    public int[][] getEnemies() {
        return enemies;
    }
}
