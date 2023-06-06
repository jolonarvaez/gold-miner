
import sample.Miner;
import sample.Grid;

public class Main
{
    public static void main(String[] args)
    {
        Grid grid = new Grid(8, new Miner(0,0, 8));
    }
}
