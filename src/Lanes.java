import bagel.Image;

public class Lanes {
    //Image of each lane
    private final bagel.Image LEFT = new Image("res/laneLeft.png");
    private final bagel.Image UP = new Image("res/laneUp.png");
    private final bagel.Image DOWN = new Image("res/laneDown.png");
    private final bagel.Image Right = new Image("res/laneRight.png");
    private final bagel.Image SPECIAL = new Image("res/laneSpecial.png");
    private final static int Y = 384;

    /**
     * Used to draw different lines for the game
     */
    public Lanes() {
    }

    /**
     * Used to draw Left lines for the game
     * @param LEFT_X is the LEFT lane's X position read from CSV
     */
    public void DRAW_LEFT(int LEFT_X){
        LEFT.draw(LEFT_X,Y);
    }

    /**
     * Used to draw Up line for the game
     * @param UP_X is the Up lane's X position read from CSV
     */
    public void DRAW_UP(int UP_X){
        UP.draw(UP_X,Y);
    }

    /**
     * Used to draw Right line for the game
     * @param RIGHT_X is the right lane's X position read from CSV
     */
    public void DRAW_RIGHT(int RIGHT_X){
        Right.draw(RIGHT_X,Y);
    }

    /**
     * Used to draw Down line for the game
     * @param DOWN_X is the Down lane's X position read from CSV
     */
    public void DRAW_DOWN(int DOWN_X){
        DOWN.draw(DOWN_X,Y);
    }

    /**
     * Used to draw Special line for the game
     * @param SPECIAL_X is the Special lane's X position read from CSV
     */
    public void DRAW_SPECIAL(int SPECIAL_X){
        SPECIAL.draw(SPECIAL_X,Y);
    }
}
