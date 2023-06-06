package sample;

public class Path extends Object{

    private Path parent;

    public Path(int x, int y) {
        super(x, y);
        this.parent = null;
    }

    public Path(int x, int y, Path parent)
    {
        super(x,y);
        this.parent = parent;
    }

    public Path getParent() {
        return parent;
    }

    public String toString() {return this.getX() + " " + this.getY();}
}
