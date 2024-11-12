import bagel.Font;
import bagel.Image;
import bagel.Window;

public class Normal {
    //Text show on
    private final Font Text = new Font("res/FSO8BITR.TTF",ShadowDance.Text_Size);
    //MAX normal notes
    private final static int MAX_NORMAL = 100;
    private final static int SCORE_POINT_Y = 657;
    private int Normal_Num = 0;
    private final static int NOTES_START_Y = 100;
    private static int NOTES_SPEED = 2;
    private final static int SpeedRestart = 2;
    //Image
    private final static Image LEFT_NORMAL = new Image("res/noteLeft.png");
    private final static Image UP_NORMAL = new Image("res/noteUp.png");
    private final static Image DOWN_NORMAL = new Image("res/noteDown.png");
    private final static Image RIGHT_NORMAL = new Image("res/noteRight.png");
    private final static Image Bomb_NORMAL = new Image("res/noteBomb.png");
    private final static Image SPEEDUP = new Image("res/noteSpeedUp.png");
    private final static Image SLOWDOWN = new Image("res/noteSlowDown.png");
    private final static Image DOUBLE = new Image("res/note2x.png");
    //Set an 2D array
    private static int[][] normals = new int[MAX_NORMAL][3];
    private final static int Direct_column = 0;
    private final static int Time_column = 1;
    private final static int Position_column = 2;
    private final static int LEFT = 1;
    private final static int UP = 2;
    private final static int DOWN = 3;
    private final static int RIGHT = 4;
    private final static int PASS = 0;
    private final static int SPECIAL_DISTANCE = 50;
    private final static int DOUBLE_TIME = 480;
    private final static int ENEMY_MEET_DISTANCE_SQUARE = 104*104;
    private final static int SPECIAL_SCORE = 15;
    //Use for Score
    private final static int ReStart = 0;
    private final static int ReSet = 0;
    private final static int SPECIAL = 0;
    private final static int SPECIAL_MARK =1;
    private final static int SQUARE = 2;
    private final static int BOMB_DIRECT = 6;
    private int Score = 0;
    private int AddScore;
    private int ScoreTime;
    private int BombTime = 0;
    private int BombLane = 0;
    private int speedup;
    private int slowdown;
    private int ClearLane;
    private int DoubleScore;
    private int ScoreDouble = 1;
    private final static int ScoreDoubleRestart = 1;
    private final static int Double = 2;
    private final static int SPEED_UP_DOWN_SCORE = 15;
    private final static int SPEED_UP_DOWN = 1;
    //To find the lowest position of each lane
    private int Left_Position;
    private int Up_Position;
    private int Down_Position;
    private int Right_Position;
    //End
    private int End = 0;
    private final static int ISEnd = 1;
    private static int DoubleTime = 0;
    //Text of Special
    private final static String SPEEDUP_TEXT = "Speed Up";
    private final static String SLOWDOWN_TEXT = "Slow Down";
    private final static String CLEAR_LANE_TEXT = "Lane Clear";
    private final static String DOUBLE_SCORE_TEXT = "Double Score";

    /**
     * Used to record and make change to normal note and special note
     */
    //Constructor
    public Normal() {
    }

    /**
     * Used to reset this when restart a level
     */
    public void reset(){
        Score =ReStart;
        End = ReStart;
        AddScore = ReStart;
        ScoreTime = ReStart;
        Normal_Num = ReStart;
        ScoreDouble = ScoreDoubleRestart;
        NOTES_SPEED = SpeedRestart;
        BombTime = ReStart;
        BombLane = ReStart;
        DoubleTime = ReStart;
        for(int i = 0; i<MAX_NORMAL; i++){
            for(int j = 0; j<=Position_column;j++){
                normals[i][j] = ReStart;
            }
        }
    }

    /**
     *Read from CSV
     * @param Direct has already set to int to represent different direct
     * @param Time is when the note show
     */
    //Import the data read from csv file
    public void Add_Normal(int Direct, int Time){
        normals[Normal_Num][Direct_column] = Direct;
        normals[Normal_Num][Time_column] = Time;
        normals[Normal_Num][Position_column] = NOTES_START_Y;
        Normal_Num++;

    }

    /**
     *
     * @param time is the game time
     * @param Left_X,Up_X,Down_X,Right_X,Special_X are all X position of the Different direct notes
     */
    //Draw the notes on the screen
    public void draw(int time, int Left_X, int Up_X, int Down_X, int Right_X, int Special_X){
        //Search the array line by line
        for(int i = 0; i < MAX_NORMAL; i++){
            //when the time achieve the time of notes
            if(time == normals[i][Time_column]){
                //According to direct and draw the notes and update its position and time
                if(normals[i][Direct_column] == LEFT){
                    LEFT_NORMAL.draw(Left_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == UP){
                    UP_NORMAL.draw(Up_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == DOWN){
                    DOWN_NORMAL.draw(Down_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == RIGHT){
                    RIGHT_NORMAL.draw(Right_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.LEFT_BOMB){
                    Bomb_NORMAL.draw(Left_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.UP_BOMB){
                    Bomb_NORMAL.draw(Up_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.DOWN_BOMB){
                    Bomb_NORMAL.draw(Down_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.RIGHT_BOMB){
                    Bomb_NORMAL.draw(Right_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.SPECIAL_BOMB){
                    Bomb_NORMAL.draw(Special_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.SPEED_UP){
                    SPEEDUP.draw(Special_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.SLOW_DOWN){
                    SLOWDOWN.draw(Special_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
                if(normals[i][Direct_column] == ShadowDance.DOUBLE_SCORE){
                    DOUBLE.draw(Special_X, normals[i][Position_column]);
                    normals[i][Position_column] += NOTES_SPEED;
                    normals[i][Time_column]++;
                }
            }
        }
    }

    /**
     * Used to update the score when a note leave the screen
     */
    public void update_leave(){
        for(int i = 0; i < MAX_NORMAL; i++){
            if(normals[i][Direct_column] != PASS) {
                //when the position of notes leave the window
                if (normals[i][Position_column] > ShadowDance.Leave) {
                    if(normals[i][Direct_column] == LEFT || normals[i][Direct_column] == UP
                            || normals[i][Direct_column] == DOWN || normals[i][Direct_column] == RIGHT){
                        Score += ScoreDouble*ShadowDance.Miss_score;
                        AddScore = ShadowDance.Miss_score;
                        ScoreTime = normals[i][Time_column];
                        normals[i][Direct_column] = PASS;
                    }else{
                        normals[i][Direct_column] = PASS;
                    }
                }
            }
        }
    }

    /**
     * Use to update the score when press on keyboard
     * Update when press to a bomb
     * @param direct is same as the direct press
     */
    //update score when press on keyboard
    public void update_score(int direct){
        for(int i = 0; i < MAX_NORMAL; i++){
            //find the direct what press
            if(normals[i][Direct_column] == direct){
                //Find the distance interval of notes and score point after begin
                if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= ShadowDance.Perfect_Distance){
                    //update score and mark it has passed
                    Score += ScoreDouble*ShadowDance.Perfect_score;
                    AddScore = ShadowDance.Perfect_score;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    //break the loop for each time only press for one notes
                    break;
                }else if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= ShadowDance.Good_Distance){
                    Score += ScoreDouble*ShadowDance.Good_score;
                    AddScore = ShadowDance.Good_score;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    break;
                }else if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= ShadowDance.Bad_Distance){
                    Score += ScoreDouble*ShadowDance.Bad_score;
                    AddScore = ShadowDance.Bad_score;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    break;
                }else if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= ShadowDance.Miss_Distance){
                    Score += ScoreDouble*ShadowDance.Miss_score;
                    AddScore = ShadowDance.Miss_score;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    break;
                }
            }else if(normals[i][Direct_column] == direct + BOMB_DIRECT){
                //When press a bomb clear lane
                if(normals[i][Position_column] > NOTES_START_Y && Math.abs(normals[i][Position_column]-SCORE_POINT_Y)
                        <= SPECIAL_DISTANCE){
                    normals[i][Direct_column] = PASS;
                    ScoreTime = normals[i][Time_column];
                    BombTime = normals[i][Time_column];
                    BombLane = direct;
                    AddScore = SPECIAL;
                    ClearLane = SPECIAL_MARK;
                    for(int j = 0; j < MAX_NORMAL;j++){
                        if(normals[j][Direct_column] == direct && normals[j][Position_column] > NOTES_START_Y){
                            normals[j][Direct_column] = PASS;
                        }
                    }
                }
            }
        }
    }

    /**
     * Update when press Space to a special note
     */
    public void updateSpecial(){
        for(int i = 0; i < MAX_NORMAL; i++){
            //Press speed up at special lane
            if(normals[i][Direct_column] == ShadowDance.SPEED_UP){
                if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= SPECIAL_DISTANCE){
                    Score += ScoreDouble*SPECIAL_SCORE;
                    AddScore = SPECIAL_SCORE;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    speedup = SPECIAL_MARK;
                    NOTES_SPEED += SPEED_UP_DOWN;
                    break;
                }
            }
            //Press slow down at special lane
            if(normals[i][Direct_column] == ShadowDance.SLOW_DOWN){
                if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= SPECIAL_DISTANCE){
                    Score += ScoreDouble*SPECIAL_SCORE;
                    AddScore = SPECIAL_SCORE;
                    ScoreTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    slowdown = SPECIAL_MARK;
                    if(NOTES_SPEED >SPEED_UP_DOWN){
                        NOTES_SPEED -=SPEED_UP_DOWN;
                    }
                    break;
                }
            }
            //Press double score at special lane
            if(normals[i][Direct_column] == ShadowDance.DOUBLE_SCORE){
                if(normals[i][Position_column] > NOTES_START_Y
                        && Math.abs(normals[i][Position_column]-SCORE_POINT_Y) <= SPECIAL_DISTANCE){
                    DoubleScore = SPECIAL_MARK;
                    ScoreDouble = Double;
                    AddScore = SPECIAL;
                    ScoreTime = normals[i][Time_column];
                    DoubleTime = normals[i][Time_column];
                    normals[i][Direct_column] = PASS;
                    break;
                }
            }
            //If press a bomb at special lane
            if(normals[i][Direct_column] == ShadowDance.SPECIAL_BOMB){
                if(normals[i][Position_column] > NOTES_START_Y && Math.abs(normals[i][Position_column]-SCORE_POINT_Y)
                        <= SPECIAL_DISTANCE){
                    normals[i][Direct_column] = PASS;
                    ScoreTime = normals[i][Time_column];
                    BombTime = normals[i][Time_column];
                    AddScore = SPECIAL;
                    ClearLane = SPECIAL_MARK;
                    for(int j = 0; j < MAX_NORMAL;j++){
                        if((normals[j][Direct_column] == ShadowDance.SPEED_UP || normals[j][Direct_column]
                                == ShadowDance.SLOW_DOWN || normals[j][Direct_column] == ShadowDance.DOUBLE_SCORE)
                                && normals[j][Position_column] > NOTES_START_Y){
                            normals[j][Direct_column] = PASS;
                        }
                    }
                }
            }
        }
    }

    /**
     * Use to keep the Double score time for 480
     * @param time is the game time
     */
    public void CheckDouble(int time){
        if(DoubleTime !=0 && time - DoubleTime > DOUBLE_TIME){
            ScoreDouble = ScoreDoubleRestart;
        }
    }

    /**
     * Use to make the normal notes disappear when an enemy colliding with it
     * @param enemy is the array from Enemy
     * @param Left_X,UP_X,DOWN_X,Right_X are all X position of the Different direct notes
     */
    public void meet(int[][] enemy,int Left_X,int UP_X,int DOWN_X,int Right_X){
        for(int i = 0;i<enemy.length;i++){
            if(enemy[i][0] != 0){
                for(int j = 0; j < MAX_NORMAL; j++){
                    //Enemy colliding with a normal note
                    if(normals[j][Direct_column] == LEFT && normals[j][Position_column] > NOTES_START_Y){
                        if(Math.pow(enemy[i][0] - Left_X,SQUARE) + Math.pow(enemy[i][1]
                                - normals[j][Position_column],SQUARE)<=ENEMY_MEET_DISTANCE_SQUARE){
                            normals[j][Direct_column] = PASS;
                        }
                    }else if(normals[j][Direct_column] == RIGHT && normals[j][Position_column] > NOTES_START_Y){
                        if(Math.pow(enemy[i][0] - Right_X,SQUARE) + Math.pow(enemy[i][1]
                                - normals[j][Position_column],SQUARE)<=ENEMY_MEET_DISTANCE_SQUARE){
                            normals[j][Direct_column] = PASS;
                        }
                    }else if(normals[j][Direct_column] == UP && normals[j][Position_column] > NOTES_START_Y){
                        if(Math.pow(enemy[i][0] - UP_X,SQUARE) + Math.pow(enemy[i][1]
                                - normals[j][Position_column],SQUARE)<=ENEMY_MEET_DISTANCE_SQUARE){
                            normals[j][Direct_column] = PASS;
                        }
                    }else if(normals[j][Direct_column] == DOWN && normals[j][Position_column] > NOTES_START_Y){
                        if(Math.pow(enemy[i][0] - DOWN_X,SQUARE) + Math.pow(enemy[i][1]
                                - normals[j][Position_column],SQUARE)<=ENEMY_MEET_DISTANCE_SQUARE){
                            normals[j][Direct_column] = PASS;
                        }
                    }
                }
            }
        }
    }

    /**
     * Use to get the bomb lane for Hold
     * @return which lane pressed a bomb
     */
    public int getBombLane() {
        return BombLane;
    }

    /**
     * Use to get the bomb time for Hold
     * @return when pressed a bomb
     */
    public int getBombTime() {
        return BombTime;
    }

    /**
     * Use to get the multiple of score
     * @return the number of multiple
     */
    public int getScoreDouble() {
        return ScoreDouble;
    }

    /**
     * Use to get the Speed for Hold to have same speed
     * @return the speed of the note
     */
    public int getNOTES_SPEED() {
        return NOTES_SPEED;
    }

    /**
     * To get the lowest note position of the lane and used to compare
     * @return the position of lowest note
     */
    public int getLeft_Position() {
        for(int i = 0; i < MAX_NORMAL; i++) {
            if ((normals[i][Direct_column] == LEFT || normals[i][Direct_column] == ShadowDance.LEFT_BOMB)
                    && normals[i][Position_column] > NOTES_START_Y) {
                Left_Position = normals[i][Position_column];
                //When get the lowest position break
                break;
            }
            Left_Position = ReSet;
        }
        return Left_Position;
    }
    /**
     * To get the lowest note position of the lane used to compare
     * @return the position of lowest note
     */
    public int getUp_Position() {
        for(int i = 0; i < MAX_NORMAL; i++) {
            if ((normals[i][Direct_column] == UP || normals[i][Direct_column] == ShadowDance.UP_BOMB)
                    && normals[i][Position_column] > NOTES_START_Y) {
                Up_Position = normals[i][Position_column];
                //When get the lowest position break
                break;
            }
            Up_Position = ReSet;
        }
        return Up_Position;
    }
    /**
     * To get the lowest note position of the lane used to compare
     * @return the position of lowest note
     */
    public int getDown_Position() {
        for(int i = 0; i < MAX_NORMAL; i++) {
            if ((normals[i][Direct_column] == DOWN || normals[i][Direct_column] == ShadowDance.DOWN_BOMB)
                    && normals[i][Position_column] > NOTES_START_Y) {
                Down_Position = normals[i][Position_column];
                //When get the lowest position break
                break;
            }
            Down_Position = ReSet;
        }
        return Down_Position;
    }
    /**
     * To get the lowest note position of the lane used to compare
     * @return the position of lowest note
     */
    public int getRight_Position() {
        for(int i = 0; i < MAX_NORMAL; i++) {
            if ((normals[i][Direct_column] == RIGHT || normals[i][Direct_column] == ShadowDance.RIGHT_BOMB)
                    && normals[i][Position_column] > NOTES_START_Y) {
                Right_Position = normals[i][Position_column];
                //When get the lowest position break
                break;
            }
            Right_Position = ReSet;
        }
        return Right_Position;
    }

    /**
     * get the score of total score from normal notes
     * @return score number
     */
    public int getScore() {
        return Score;
    }

    /**
     * Recording score time to compare with hold score time
     * @return the time of last score time
     */
    public int getScoreTime() {
        return ScoreTime;
    }


    /**
     * To get when every note is passed change End equal 1 otherwise keep End = 0
     * @return Number of End
     */
    public int getEnd() {
        for (int i = 0; i < MAX_NORMAL; i++) {
            if (normals[i][Direct_column] != PASS) {
                return End;
            }
        }
        End = ISEnd;
        return End;
    }


    /**
     * Used to Draw the kind of what we press
     * @param time is the game time to keep the text show for 30
     */
    public void text_draw(int time){
        //Draw the text for 30 frames
        if(time - ScoreTime <= ShadowDance.Text_ShowTime){
            //perfect
            if(AddScore == ShadowDance.Perfect_score){
                Text.drawString(ShadowDance.PERFECT, (Window.getWidth() - Text.getWidth(ShadowDance.PERFECT))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            //Good
            if(AddScore == ShadowDance.Good_score){
                Text.drawString(ShadowDance.GOOD, (Window.getWidth() - Text.getWidth(ShadowDance.GOOD))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            //Bad
            if(AddScore == ShadowDance.Bad_score){
                Text.drawString(ShadowDance.BAD, (Window.getWidth() - Text.getWidth(ShadowDance.BAD))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            if(AddScore == SPEED_UP_DOWN_SCORE && speedup == SPECIAL_MARK){
                Text.drawString(SPEEDUP_TEXT, (Window.getWidth() - Text.getWidth(SPEEDUP_TEXT))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            if(AddScore == SPEED_UP_DOWN_SCORE && slowdown == SPECIAL_MARK){
                Text.drawString(SLOWDOWN_TEXT, (Window.getWidth() - Text.getWidth(SLOWDOWN_TEXT))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            if(AddScore == SPECIAL && DoubleScore == SPECIAL_MARK){
                Text.drawString(DOUBLE_SCORE_TEXT, (Window.getWidth() - Text.getWidth(DOUBLE_SCORE_TEXT))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            if(AddScore == SPECIAL && ClearLane == SPECIAL_MARK){
                Text.drawString(CLEAR_LANE_TEXT, (Window.getWidth() - Text.getWidth(CLEAR_LANE_TEXT))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
            //Miss or leave
            if(AddScore == ShadowDance.Miss_score){
                Text.drawString(ShadowDance.MISS, (Window.getWidth() - Text.getWidth(ShadowDance.MISS))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
        }else{
            speedup = ReSet;
            slowdown = ReSet;
            DoubleScore = ReSet;
            ClearLane = ReSet;
            AddScore = ReSet;
        }
    }
}
