import bagel.Font;
import bagel.Image;
import bagel.Window;

public class Hold {
    //Text Show
    private final Font Text = new Font("res/FSO8BITR.TTF",ShadowDance.Text_Size);
    //Use for Score
    private final static int NOTES_START_Y = 24;
    private final static int SCORE_POINT_Y = 657;
    private static int NOTES_SPEED = 2;
    private final static int SPEED_RESET = 2;
    private static int ScoreDouble = 1;
    private final static int SCORE_DOUBLE_RESET = 1;
    private final static int UP_LOW_DISTANCE = 82;
    private int Score = 0;
    private int AddScore;
    private int ScoreTime;
    //Image of Hold note
    private final static Image LEFT_HOLD = new Image("res/holdNoteLeft.png");
    private final static Image UP_HOLD = new Image("res/holdNoteUp.png");
    private final static Image DOWN_HOLD = new Image("res/holdNoteDown.png");
    private final static Image RIGHT_HOLD = new Image("res/holdNoteRight.png");
    //column name in array
    private final static int Direct_column = 0;
    private final static int Time_column = 1;
    private final static int Position_column = 2;
    private final static int Low_Passed_column = 3;
    private final static int LEFT = 1;
    private final static int UP = 2;
    private final static int DOWN = 3;
    private final static int RIGHT = 4;
    private final static int LOW_PASS = -1;
    private final static int LOW_LEAVE = -5;
    private final static int UP_PASS = 0;
    private final static int MAX_HOLD = 20;
    private final static int ReSet = 0;
    //build 2D array
    private int Hold_Num;
    private int[][] Holds = new int[MAX_HOLD][4];
    private int End = 0;
    private final static int ISEnd = 1;

    private int Left_Position;
    private int Up_Position;
    private int Down_Position;
    private int Right_Position;

    /**
     * Used to record and make change to Hold note
     */
    public Hold(){

    }

    /**
     * Used to reset each data when start a game
     */
    public void reset(){
        Score =ReSet;
        End = ReSet;
        AddScore = ReSet;
        ScoreTime = ReSet;
        Hold_Num = ReSet;
        ScoreDouble = SCORE_DOUBLE_RESET;
        NOTES_SPEED = SPEED_RESET;
        for(int i = 0; i<MAX_HOLD; i++){
            for(int j = 0; j<=Low_Passed_column;j++){
                Holds[i][j] = ReSet;
            }
        }
    }

    /**
     * Used to Import the data read from csv file
     * @param Direct has already set to int to represent different direct
     * @param Time is when the note show
     */
    public void ADD_Holds(int Direct,int Time){
        Holds[Hold_Num][Direct_column] = Direct;
        Holds[Hold_Num][Time_column] = Time;
        Holds[Hold_Num][Position_column] = NOTES_START_Y;
        Holds[Hold_Num][Low_Passed_column] = Direct;
        Hold_Num++;
    }

    /**
     * Used to draw the notes on the screen
     * @param Time is the game time
     * @param LEFT_X,UP_X,DOWN_X,RIGHT_X are all X position of the Different direct notes
     * @param BombTime,BombLane is from the Normal to consider if the Hold notes been cleared
     */
    public void Hold_Draw(int Time, int LEFT_X, int UP_X, int DOWN_X, int RIGHT_X , int BombTime, int BombLane){
        for(int i = 0; i < MAX_HOLD; i++){
            //when the time achieve the time of notes
            if(Time == Holds[i][Time_column]){
                //According to direct and draw the notes and update its position and time
                if(Holds[i][Direct_column] == LEFT){
                    LEFT_HOLD.draw(LEFT_X,Holds[i][Position_column]);
                    Holds[i][Position_column] += NOTES_SPEED;
                    Holds[i][Time_column]++;
                }
                if(Holds[i][Direct_column] == UP){
                    UP_HOLD.draw(UP_X,Holds[i][Position_column]);
                    Holds[i][Position_column] += NOTES_SPEED;
                    Holds[i][Time_column]++;
                }
                if(Holds[i][Direct_column] == DOWN){
                    DOWN_HOLD.draw(DOWN_X,Holds[i][Position_column]);
                    Holds[i][Position_column] += NOTES_SPEED;
                    Holds[i][Time_column]++;
                }
                if(Holds[i][Direct_column] == RIGHT){
                    RIGHT_HOLD.draw(RIGHT_X,Holds[i][Position_column]);
                    Holds[i][Position_column] += NOTES_SPEED;
                    Holds[i][Time_column]++;
                }
                if(Time == BombTime && Holds[i][Direct_column] == BombLane){
                    Holds[i][Low_Passed_column] = LOW_PASS;
                    Holds[i][Direct_column] = UP_PASS;
                }
            }
        }
    }

    /**
     * Used to update the score when a normal note leave the window
     */
    public void update_leave(){
        for(int i = 0; i < MAX_HOLD; i++){
            if(Holds[i][Low_Passed_column] != LOW_PASS && Holds[i][Low_Passed_column] != LOW_LEAVE) {
                //when the bottom part of hold notes leave the window
                if (Holds[i][Position_column] + UP_LOW_DISTANCE > ShadowDance.Leave) {
                    //update the score and mark bottom has passed
                    Score += ScoreDouble*ShadowDance.Miss_score;
                    AddScore = ShadowDance.Miss_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Low_Passed_column] = LOW_LEAVE;
                }
            }
            if(Holds[i][Direct_column] != UP_PASS){
                //when the Top part of hold notes leave the window
                if ((Holds[i][Position_column] - UP_LOW_DISTANCE > ShadowDance.Leave)) {
                    //when the bottom is not leave and top leave should -5 mark
                    if(Holds[i][Low_Passed_column] != LOW_LEAVE){
                        //update the score and mark top has passed
                        Score += ScoreDouble*ShadowDance.Miss_score;
                        AddScore = ShadowDance.Miss_score;
                        ScoreTime = Holds[i][Time_column];
                    }
                    //as the top leave, mark it has pass
                    Holds[i][Direct_column] = UP_PASS;
                }
            }
        }
    }

    /**
     * Used to update score when press on keyboard
     * @param direct is same as the direct press
     */
    public void update_score(int direct){
        for(int i = 0; i < MAX_HOLD; i++){
            //find the direct what press
            if(Holds[i][Low_Passed_column] == direct){
                //Find the distance interval of bottom and score point after begin
                if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column]+UP_LOW_DISTANCE-SCORE_POINT_Y)
                        <= ShadowDance.Perfect_Distance){
                    //update score and mark bottom has passed
                    Score += ScoreDouble*ShadowDance.Perfect_score;
                    AddScore = ShadowDance.Perfect_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Low_Passed_column] = LOW_PASS;
                    //break the loop for each time only press for one notes
                    break;
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column]+UP_LOW_DISTANCE-SCORE_POINT_Y)
                        <= ShadowDance.Good_Distance){
                    Score += ScoreDouble*ShadowDance.Good_score;
                    AddScore = ShadowDance.Good_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Low_Passed_column] = LOW_PASS;
                    break;
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column]+UP_LOW_DISTANCE-SCORE_POINT_Y)
                        <= ShadowDance.Bad_Distance){
                    Score += ScoreDouble*ShadowDance.Bad_score;
                    AddScore = ShadowDance.Bad_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Low_Passed_column] = LOW_PASS;
                    break;
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column]+UP_LOW_DISTANCE -SCORE_POINT_Y)
                        <= ShadowDance.Miss_Distance){
                    Score += ScoreDouble*ShadowDance.Miss_score;
                    AddScore = ShadowDance.Miss_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Low_Passed_column] = LOW_PASS;
                    break;
                }
            }
        }
    }


    /**
     * Used to keep the multiple of score same as Normal
     * @param scoreDouble is the multiple from Normal
     */
    public void ChangeDouble(int scoreDouble){
        ScoreDouble = scoreDouble;
    }

    /**
     * Used to keep the Speed same as normal
     * @param speed is the speed from Normal
     */
    public void ChangeSpeed(int speed){
        NOTES_SPEED = speed;
    }

    /**
     * To get the lowest note position of the lane used to compare
     * @return the position of lowest note
     */
    public int getLeft_Position() {
        for(int i = 0; i < MAX_HOLD; i++) {
            if (Holds[i][Direct_column] == LEFT && Holds[i][Position_column]>NOTES_START_Y) {
                Left_Position =Holds[i][Position_column];
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
        for(int i = 0; i < MAX_HOLD; i++) {
            if (Holds[i][Direct_column] == UP && Holds[i][Position_column]>NOTES_START_Y) {
                Up_Position =Holds[i][Position_column];
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
        for(int i = 0; i < MAX_HOLD; i++) {
            if (Holds[i][Direct_column] == DOWN && Holds[i][Position_column]>NOTES_START_Y) {
                Down_Position = Holds[i][Position_column];
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
        for(int i = 0; i < MAX_HOLD; i++) {
            if (Holds[i][Direct_column] == RIGHT && Holds[i][Position_column]>NOTES_START_Y) {
                Right_Position = Holds[i][Position_column];
                //When get the lowest position break
                break;
            }
            Right_Position =ReSet;
        }
        return Right_Position;
    }

    /**
     * Used to update score when release from keyboard
     * @param direct is same as the direct release
     */
    public void release_score(int direct){
        for(int i = 0; i < MAX_HOLD; i++){
            //find the direct which release and the bottom should be pressed or leave
            if(Holds[i][Direct_column] == direct
                    && (Holds[i][Low_Passed_column] == LOW_PASS || Holds[i][Low_Passed_column] == LOW_LEAVE)){
                //Find the distance interval of top and score point after begin
                if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column] -UP_LOW_DISTANCE -SCORE_POINT_Y)
                        <= ShadowDance.Perfect_Distance){
                    //update score and mark top has release
                    Score += ScoreDouble*ShadowDance.Perfect_score;
                    AddScore = ShadowDance.Perfect_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Direct_column] = UP_PASS;
                    //break the loop for each time only release for one notes
                    break;
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column] -UP_LOW_DISTANCE -SCORE_POINT_Y)
                        <= ShadowDance.Good_Distance){
                    Score += ScoreDouble*ShadowDance.Good_score;
                    AddScore = ShadowDance.Good_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Direct_column] = UP_PASS;
                    break;
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column] -UP_LOW_DISTANCE -SCORE_POINT_Y)
                        <= ShadowDance.Bad_Distance){
                    Score += ScoreDouble*ShadowDance.Bad_score;
                    AddScore = ShadowDance.Bad_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Direct_column] = UP_PASS;
                    break;
                    //when the bottom has been pressed or leave and when release the distance bigger than 100 is miss
                }else if(Holds[i][Position_column]>NOTES_START_Y
                        && Math.abs(Holds[i][Position_column] -UP_LOW_DISTANCE -SCORE_POINT_Y)
                        > ShadowDance.Bad_Distance){
                    Score += ScoreDouble*ShadowDance.Miss_score;
                    AddScore = ShadowDance.Miss_score;
                    ScoreTime = Holds[i][Time_column];
                    Holds[i][Direct_column] = UP_PASS;
                    break;
                }
            }
        }
    }

    /**
     * get the score of total score from hold notes
     * @return the score
     */
    public int getScore() {
        return Score;
    }

    /**
     * Record the hold Score time to compare to normal score time
     * @return the last score time
     */
    public int getScoreTime() {
        return ScoreTime;
    }

    /**
     * When every note is passed change End equal 1 otherwise keep End = 0
     * @return End or not
     */
    public int getEnd() {
        for (int i = 0; i < MAX_HOLD; i++) {
            if (Holds[i][Direct_column] != UP_PASS) {
                return End;
            }
        }
        End = ISEnd;
        return End;
    }

    /**
     * Used to Draw the kind of what score we get from hold notes
     * @param time is the game time to keep the text show for 30
     */
    public void text_draw(int time){
        //Draw the text for 30 frames
        if(time - ScoreTime <= ShadowDance.Text_ShowTime){
            //prefect
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
            //Miss or leave
            if(AddScore == ShadowDance.Miss_score){
                Text.drawString(ShadowDance.MISS, (Window.getWidth() - Text.getWidth(ShadowDance.MISS))
                                /ShadowDance.Divide_two
                        , (Window.getHeight()-ShadowDance.Text_Size)/ShadowDance.Divide_two);
            }
        }else{
            AddScore = ReSet;
        }
    }
}
