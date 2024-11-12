import bagel.Image;

public class Guardian {
    //Position of guardian
    private final static int X = 800;
    private final static int Y = 600;
    //Image of guardian
    private final bagel.Image Image = new Image("res/guardian.png");

    /**
     * Used to draw a guardian Image when level 3
     */
    public Guardian() {
    }
    /**
     * the guardian position is fixed
     */
    public void draw() {
        Image.draw(X, Y);
    }
}
