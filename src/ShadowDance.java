import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Jieyang Zhu
 */
public class ShadowDance extends AbstractGame  {
    //Window setup
    //Title,End,Instruction
    //Magic Number,Strings
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    public final static double Divide_two = 2.0;
    //Title or End
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final static int TITLE_SIZE = 64;
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final Font TITLE = new Font("res/FSO8BITR.TTF",TITLE_SIZE);
    private final static String CLEAR = "CLEAR!";
    private final static String TRY_AGAIN = "TRY AGAIN";
    private final static String PRESS_SPACE = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private final static int PRESS_SPACE_Y = 500;
    private final static int GAME_OVER_STATE_Y = 300;
    //Instruction
    private final static int INSTRUCTION_SIZE = 24;
    private final static String GAME_INSTRUCTION = "SELECT LEVELS WITH\nNUMBER KEYS\n\n    1       2       3";
    private final static int INSTRUCTION_X = TITLE_X + 100;
    private final static int INSTRUCTION_Y = TITLE_Y + 190;
    private final Font INSTRUCTION = new Font("res/FSO8BITR.TTF",INSTRUCTION_SIZE);
    //Read CSV
    //Name Numbers read from CSV
    //Magic Numbers
    //read file
    private final static String LANE = "Lane";
    private final static String LEFT_STRING = "Left";
    private final static String UP_STRING = "Up";
    private final static String DOWN_STRING = "Down";
    private final static String RIGHT_STRING = "Right";
    private final static String SPECIAL_STRING = "Special";
    private final static String NORMAL_STRING = "Normal";
    private final static String HOLD_STRING = "Hold";
    private final static String BOMB_STRING = "Bomb";
    private final static String SPEED_UP_STRING = "SpeedUp";
    private final static String SLOW_DOWN_STRING = "SlowDown";
    private final static String DOUBLE_SCORE_STRING = "DoubleScore";
    private final static String READ_FILE_LEVEL1 = "res/level1.csv";
    private final static String READ_FILE_LEVEL2 = "res/level2.csv";
    private final static String READ_FILE_LEVEL3 = "res/level3.csv";
    //read csv
    private final static int First_column = 0;
    private final static int Second_column = 1;
    private final static int Third_column = 2;
    private final static int LEFT = 1;
    private final static int UP = 2;
    private final static int DOWN = 3;
    private final static int RIGHT = 4;
    public final static int LEFT_BOMB = 7;
    public final static int UP_BOMB = 8;
    public final static int DOWN_BOMB = 9;
    public final static int RIGHT_BOMB = 10;
    public final static int SPECIAL_BOMB = 11;
    public final static int SPEED_UP = 99;
    public final static int SLOW_DOWN = 98;
    public final static int DOUBLE_SCORE = 97;
    //Lines
    private int LANE_LEFT_X;
    private int LANE_Right_X;
    private int LANE_Down_X;
    private int LANE_Up_X;
    private int LANE_Special_X;
    private final static int NoLane = 0;
    //Class use in game
    Normal normal = new Normal();
    Hold hold = new Hold();
    Guardian guardian = new Guardian();
    Enemy enemy = new Enemy();
    Projectile projectile = new Projectile();

    //Numbers use to record or set in game
    public final static String PERFECT = "PERFECT";
    public final static String GOOD = "GOOD";
    public final static String BAD = "BAD";
    public final static String MISS = "MISS";
    public final static int Text_Size = 40;
    public final static int Text_ShowTime = 30;
    public final static int Perfect_score = 10;
    public final static int Good_score = 5;
    public final static int Bad_score = -1;
    public final static int Miss_score = -5;
    public final static int Perfect_Distance = 15;
    public final static int Good_Distance = 50;
    public final static int Bad_Distance = 100;
    public final static int Miss_Distance = 200;
    public final static int Leave = 768;
    // SCORE RECORD
    private final static int SCORE_SIZE = 30;
    private final Font SCORE = new Font("res/FSO8BITR.TTF",SCORE_SIZE);
    private static int score = 0;
    private final static String Score = "SCORE ";
    private final static int SCORE_X = 35;
    private final static int SCORE_Y = 35;
    //Time
    private int time = 0;
    private int Begin = 0;
    private int LEVEL_END;
    //End
    private final static int IsEnd = 2;
    private static int success;
    private final static int RESTART = 0;
    private final static int START_PAGE = 0;
    private final static int LEVEL1 = 1;
    private final static int LEVEL2 = 2;
    private final static int LEVEL3 = 3;
    //Success score
    private final static int LEVEL1_SUCCESS = 150;
    private final static int LEVEL2_SUCCESS = 400;
    private final static int LEVEL3_SUCCESS = 350;

    /** This method is used to extend the constant from the parent class
     */
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }
    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV(String level) {
        try(BufferedReader BR = new BufferedReader(new FileReader(level))){
            String Readline;
            // read the csv file line by line
            while((Readline = BR.readLine()) != null){
                //Separate with ,
                String[] parts = Readline.split(",");
                //Find lanes and record its X position
                if(parts[First_column].equals(LANE)){
                    if(parts[Second_column].equals(LEFT_STRING)){
                        LANE_LEFT_X = Integer.parseInt(parts[Third_column]);
                    }
                    if(parts[Second_column].equals(RIGHT_STRING)){
                        LANE_Right_X = Integer.parseInt(parts[Third_column]);
                    }
                    if(parts[Second_column].equals(UP_STRING)){
                        LANE_Up_X = Integer.parseInt(parts[Third_column]);
                    }
                    if(parts[Second_column].equals(DOWN_STRING)){
                        LANE_Down_X = Integer.parseInt(parts[Third_column]);
                    }
                    if(parts[Second_column].equals(SPECIAL_STRING)){
                        LANE_Special_X = Integer.parseInt(parts[Third_column]);
                    }
                }
                //Find the note is Normal or Hold or Bomb and record their frames and direct
                if(parts[First_column].equals(LEFT_STRING)){
                    if(parts[Second_column].equals(NORMAL_STRING)){
                        normal.Add_Normal(LEFT, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(HOLD_STRING)){
                        hold.ADD_Holds(LEFT, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(BOMB_STRING)){
                        normal.Add_Normal(LEFT_BOMB, Integer.parseInt(parts[Third_column]));
                    }
                }
                if(parts[First_column].equals(UP_STRING)){
                    if(parts[Second_column].equals(NORMAL_STRING)){
                        normal.Add_Normal(UP, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(HOLD_STRING)){
                        hold.ADD_Holds(UP, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(BOMB_STRING)){
                        normal.Add_Normal(UP_BOMB, Integer.parseInt(parts[Third_column]));
                    }
                }
                if(parts[First_column].equals(DOWN_STRING)){
                    if(parts[Second_column].equals(NORMAL_STRING)){
                        normal.Add_Normal(DOWN, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(HOLD_STRING)){
                        hold.ADD_Holds(DOWN, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(BOMB_STRING)){
                        normal.Add_Normal(DOWN_BOMB, Integer.parseInt(parts[Third_column]));
                    }
                }
                if(parts[First_column].equals(RIGHT_STRING)){
                    if(parts[Second_column].equals(NORMAL_STRING)){
                        normal.Add_Normal(RIGHT, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(HOLD_STRING)){
                        hold.ADD_Holds(RIGHT, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(BOMB_STRING)){
                        normal.Add_Normal(RIGHT_BOMB, Integer.parseInt(parts[Third_column]));
                    }
                }
                //Find the special note in CSV
                if(parts[First_column].equals(SPECIAL_STRING)){
                    if(parts[Second_column].equals(SPEED_UP_STRING)){
                        normal.Add_Normal(SPEED_UP, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(SLOW_DOWN_STRING)){
                        normal.Add_Normal(SLOW_DOWN, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(DOUBLE_SCORE_STRING)){
                        normal.Add_Normal(DOUBLE_SCORE, Integer.parseInt(parts[Third_column]));
                    }
                    if(parts[Second_column].equals(BOMB_STRING)){
                        normal.Add_Normal(SPECIAL_BOMB, Integer.parseInt(parts[Third_column]));
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Allows the different level game to start when press different Key number
     */
    @Override
    protected void update(Input input) {
        //Press ESC to close
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        //Prepare to begin the game
        if(Begin == START_PAGE){
            normal.reset();
            hold.reset();
            projectile.reset();
            enemy.reset();
            LANE_LEFT_X = RESTART;
            LANE_Right_X = RESTART;
            LANE_Down_X = RESTART;
            LANE_Up_X = RESTART;
            LANE_Special_X = RESTART;
            score = RESTART;
            time = RESTART;
            LEVEL_END = RESTART;
            //Find which level of game and read CSV
            if(input.wasPressed(Keys.NUM_1)){
                readCSV(READ_FILE_LEVEL1);
                Begin = LEVEL1;
            }
            if(input.wasPressed(Keys.NUM_2)){
                readCSV(READ_FILE_LEVEL2);
                Begin = LEVEL2;
            }
            if(input.wasPressed(Keys.NUM_3)){
                readCSV(READ_FILE_LEVEL3);
                Begin = LEVEL3;
            }
        }
        //Draw background
        BACKGROUND_IMAGE.draw(Window.getWidth() / Divide_two, Window.getHeight() / Divide_two);
        if (Begin == START_PAGE) {
            //draw the Title and Instruction
            TITLE.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION.drawString(GAME_INSTRUCTION, INSTRUCTION_X, INSTRUCTION_Y);
        }
        //As the game start
        if(LEVEL_END != IsEnd && (Begin ==LEVEL1 || Begin ==LEVEL2 || Begin ==LEVEL3)){
            LEVEL_END = normal.getEnd() + hold.getEnd();
            Lanes lanes = new Lanes();
            //Draw the line in the level
            if(LANE_LEFT_X != NoLane){
                lanes.DRAW_LEFT(LANE_LEFT_X);
            }
            if(LANE_Right_X != NoLane){
                lanes.DRAW_RIGHT(LANE_Right_X);
            }
            if(LANE_Down_X != NoLane){
                lanes.DRAW_DOWN(LANE_Down_X);
            }
            if(LANE_Up_X != NoLane){
                lanes.DRAW_UP(LANE_Up_X);
            }
            if(LANE_Special_X != NoLane){
                lanes.DRAW_SPECIAL(LANE_Special_X);
            }
            //Draw the score update
            SCORE.drawString(Score + score, SCORE_X, SCORE_Y);
            time++;
            //Update the picture of each frame
            normal.draw(time, LANE_LEFT_X, LANE_Up_X, LANE_Down_X, LANE_Right_X,LANE_Special_X);
            hold.Hold_Draw(time, LANE_LEFT_X, LANE_Up_X, LANE_Down_X, LANE_Right_X,
                    normal.getBombTime(), normal.getBombLane());
            normal.update_leave();
            hold.update_leave();
            //Calculate the score
            score = normal.getScore() + hold.getScore();
            //Direct press on keyboard
            if (input.wasPressed(Keys.LEFT)) {
                if(normal.getLeft_Position() > hold.getLeft_Position()){
                    normal.update_score(LEFT);
                }else if(normal.getLeft_Position() < hold.getLeft_Position()){
                    hold.update_score(LEFT);
                }
            }else if (input.wasReleased(Keys.LEFT)) {
                hold.release_score(LEFT);
            }
            if (input.wasPressed(Keys.UP)) {
                if(normal.getUp_Position() > hold.getUp_Position()){
                    normal.update_score(UP);
                }else if(normal.getUp_Position() < hold.getUp_Position()){
                    hold.update_score(UP);
                }
            } else if (input.wasReleased(Keys.UP)) {
                hold.release_score(UP);
            }
            if (input.wasPressed(Keys.DOWN)) {
                if(normal.getDown_Position() > hold.getDown_Position()){
                    normal.update_score(DOWN);
                }else if(normal.getDown_Position() < hold.getDown_Position()){
                    hold.update_score(DOWN);
                }
            }else if (input.wasReleased(Keys.DOWN)) {
                hold.release_score(DOWN);
            }
            if (input.wasPressed(Keys.RIGHT)) {
                if(normal.getRight_Position() > hold.getRight_Position()) {
                    normal.update_score(RIGHT);
                }else if(normal.getRight_Position() < hold.getRight_Position()) {
                    hold.update_score(RIGHT);
                }
            } else if (input.wasReleased(Keys.RIGHT)) {
                hold.release_score(RIGHT);
            }
            //level 2 and 3 has special lane
            if(Begin == 2 || Begin ==3){
                if(input.wasPressed(Keys.SPACE)){
                    normal.updateSpecial();
                }
                hold.ChangeSpeed(normal.getNOTES_SPEED());
                hold.ChangeDouble(normal.getScoreDouble());
                normal.CheckDouble(time);
            }
            //Level 3 has guardian and enemy and arrows
            if(Begin ==3){
                guardian.draw();
                normal.meet(enemy.getEnemies(),LANE_LEFT_X,LANE_Up_X,LANE_Down_X,LANE_Right_X);
                enemy.RANDOM(time);
                enemy.EnemyDraw();
                if(input.wasPressed(Keys.LEFT_SHIFT)){
                    projectile.fire(enemy.getEnemies());
                }
                enemy.BiuBiu(projectile.getDisappear());
                projectile.biu(enemy.getEnemies());
                projectile.draw();
            }
            //draw the text of game which is shown later
            if (normal.getScoreTime() > hold.getScoreTime()) {
                normal.text_draw(time);
            } else {
                hold.text_draw(time);
            }
        }
        //When a level end, find out is success or not
        if (LEVEL_END == IsEnd) {
            if(Begin == LEVEL1){
                success = LEVEL1_SUCCESS;
            }
            if(Begin == LEVEL2){
                success = LEVEL2_SUCCESS;
            }
            if(Begin == LEVEL3){
                success = LEVEL3_SUCCESS;
            }
            if (score >= success) {
                //success
                TITLE.drawString(CLEAR, (Window.getWidth() - TITLE.getWidth(CLEAR)) / Divide_two, GAME_OVER_STATE_Y);
            } else {
                //lose
                TITLE.drawString(TRY_AGAIN, (Window.getWidth() - TITLE.getWidth(TRY_AGAIN))
                        / Divide_two, GAME_OVER_STATE_Y);
            }
            INSTRUCTION.drawString(PRESS_SPACE, (Window.getWidth() - INSTRUCTION.getWidth(PRESS_SPACE))
                            / Divide_two, PRESS_SPACE_Y);
            //Press space to back to game select
            if (input.wasPressed(Keys.SPACE)) {
                Begin = START_PAGE;
                LEVEL_END = RESTART;
            }
        }
    }
}
