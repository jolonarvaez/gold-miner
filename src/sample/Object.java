package sample;

public class Object {

    private int xPos;
    private int yPos;

    public Object(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getX() {return xPos;}

    public int getY() {return yPos;}

    void setX(int x) {xPos = x;}

    void setY(int y) {yPos = y;}
}
