package sample;

import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Grid extends JFrame{

    static int size;
    private Gold gold;
    private Miner miner;
    private ArrayList<Pit> pitList = new ArrayList<>();
    private ArrayList<Beacon> beacList = new ArrayList<Beacon>();
    private List<Path> pathList = new ArrayList<Path>();

    Options op;
    DashBoard db;

    public Grid(int n, Miner m) {
        size = n;
        miner = m;

        setVisible(true);
        setSize(970, 550);
        setTitle("Gold Miner");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable(false);

        op = new Options();
        db = new DashBoard();

        add(op, BorderLayout.SOUTH);
        add(db, BorderLayout.EAST);

        setVisible(true);
        addBtnListeners();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int limit = 30 * size;

        for (int x = 30; x <= limit; x += 30)
            for (int y = 30; y <= limit; y += 30)
                g.drawRect(x, y, 30, 30);

        if(gold != null) {
            Graphics2D go = (Graphics2D) g;
            go.setColor(Color.YELLOW);
            go.fillRect(gold.getX() * 30 + 31, gold.getY() * 30 + 31, 29, 29);
        }


        for(int i = 0; i < pitList.size(); i++) {
            Graphics2D p = (Graphics2D) g;
            p.setColor(Color.BLACK);
            p.fillRect(pitList.get(i).getX() * 30 + 31, pitList.get(i).getY() * 30 + 31, 29, 29);
        }

        if(pathList != null) {
            for(int i = 0; i < beacList. size(); i++) {
                Graphics2D b = (Graphics2D) g;
                b.setColor(Color.GREEN);
                b.fillRect(beacList.get(i).getX() * 30 + 31, beacList.get(i).getY() * 30 + 31, 29, 29);
            }
        }

        for(int i = 0; i < pathList.size(); i++) {
            Graphics2D pt = (Graphics2D) g;
            pt.setColor(Color.GRAY);
            pt.fillRect(pathList.get(i).getX() * 30 + 31, pathList.get(i).getY() * 30 + 31, 29, 29);
        }

        Graphics2D m = (Graphics2D) g;
        m.setColor(Color.BLUE);
        m.fillRect(miner.getX() * 30 + 31, miner.getY() * 30 + 31, 29, 29);
    }

    private void resetMiner(Miner m) {miner = m; }

    private void setMiner(Miner m) {
        miner = m;
        char[][] map = new char[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                map[i][j] = '-';

        for(int i = 0; i < beacList.size(); i++)
            map[beacList.get(i).getX()][beacList.get(i).getY()] = 'B';

        for(int i = 0; i < pitList.size(); i++)
            map[pitList.get(i).getX()][pitList.get(i).getY()] = 'P';

        map[gold.getX()][gold.getY()] = 'G';

        map[miner.getX()][miner.getY()] = 'x';

        miner.setMap(map);
    }

    public void setGold(Gold g) {gold = g;}

    public void setBeacon(ArrayList<Beacon> b) {beacList = b;}

    public void setPit(ArrayList<Pit> p) {pitList = p;}

    public void setSize(int s) {size = s;}

    public void addPath(Path p) {pathList.add(p);}

    public void addBtnListeners() {
        op.getUpdate().addActionListener(e ->{
            setUp();
        });

        db.getStart().addActionListener(e -> {
            if(op.getAlgo().isSelected()) {
                smart();
            }
            else
                random();
        });

        db.getReset().addActionListener(e -> {
            beacList.clear();
            pitList.clear();
            pathList.clear();
            gold = null;
            resetMiner(new Miner(0,0, size));
            repaint();
        });
    }

    public boolean checkLocation(Gold gold, ArrayList<Pit> pit, ArrayList<Beacon> beac) {
        for(int i = 0; i < beac.size(); i++) {
            if(beac.get(i).getX() == gold.getX() && beac.get(i).getY() == gold.getY())
                return false;

            if(beac.get(i).getX() == gold.getX() || beac.get(i).getY() == gold.getY()) {
                if(beac.get(i).getX() == gold.getX()) {
                    for(int j = 0; j < pit.size(); j++) {
                        if(pit.get(j).getX() == beac.get(i).getX()) {
                            if(beac.get(i).getY() < pit.get(j).getY() && pit.get(j).getY() < gold.getY())
                                return false;
                            else if(beac.get(i).getY() > pit.get(j).getY() && pit.get(j).getY() > gold.getY())
                                return false;
                        }
                    }
                }

                if(beac.get(i).getY() == gold.getY()) {
                    for(int j = 0; j < pit.size(); j++) {
                        if(pit.get(j).getY() == beac.get(i).getY()) {
                            if(beac.get(i).getX() < pit.get(j).getX() && pit.get(j).getX() < gold.getX())
                                return false;
                            else if(beac.get(i).getX() > pit.get(j).getX() && pit.get(j).getX() > gold.getX())
                                return false;
                        }
                    }
                }
            }
            else
                return false;
        }
        return true;
    }


    public void setUp()
    {
        beacList.clear();
        pitList.clear();
        int num;
        if(op.getGridSize().isEmpty())
            num = size;
        else {
            num = Integer.parseInt(op.getGridSize());
            if(num >=8 && num <= 64)
                setSize(num);
            else
                JOptionPane.showMessageDialog(this, "Invalid Grid Size");
        }

        String[] gCor = op.getGoldText().split(",");
        String[] bCor = op.getBeaconText();
        String[] pCor = op.getPitText();

        if(Integer.parseInt(gCor[0]) - 1 > size || Integer.parseInt(gCor[1]) - 1 > size)
            JOptionPane.showMessageDialog(this, "Invalid Gold Location");

        else
            setGold(new Gold(Integer.parseInt(gCor[0]) - 1, Integer.parseInt(gCor[1]) - 1));

        for(int i = 0; i < bCor.length; i++) {
            int xCor, yCor;
            String[] cor = bCor[i].split(",");

            xCor = Integer.parseInt(cor[0]) - 1;
            yCor = Integer.parseInt(cor[1]) - 1;

            if((xCor >= 0 && xCor < size) && (yCor >= 0 && yCor < size))
                beacList.add(new Beacon(xCor, yCor));
            else
                JOptionPane.showMessageDialog(this, "Invalid Beacon Location\n" + (xCor+1) + "," + (yCor+1));
        }

        for(int i = 0; i < pCor.length; i++) {
            int xCor, yCor;
            String[] cor = pCor[i].split(",");

            xCor = Integer.parseInt(cor[0]) - 1;
            yCor = Integer.parseInt(cor[1]) - 1;

            if((xCor >= 0 && xCor < size) && (yCor >= 0 && yCor < size))
                pitList.add(new Pit(xCor, yCor));
            else
                JOptionPane.showMessageDialog(this, "Invalid Pit Location\n" + (xCor+1) + "," + (yCor+1));

        }

        setBeacon(beacList);
        setPit(pitList);
        setMiner(miner);

        if(checkLocation(gold, pitList, beacList))
            repaint();
        else
            JOptionPane.showMessageDialog(this, "Invalid Configuration");
    }

    public void random() {
        int rd, ctrM = 0, ctrR = 0;
        pathList.clear();
        setMiner(new Miner(0,0, size));
        db.getMovesCtr().setText("0");
        db.getRotateCtr().setText("0");
        db.getScanCtr().setText("0");
        repaint();

        if(!op.getGoldText().isEmpty() && !op.getGoldText().isEmpty() && !op.getPit().isEmpty() && !op.getBeacon().isEmpty()) {
            Random rand = new Random();
                while(!miner.getStatus()) {
                    addPath(new Path(miner.getX(), miner.getY()));
                    rd = rand.nextInt(2);
                    if(rd == 0) {
                        miner.rotate();
                        System.out.println("rotated");
                        ctrR++;
                    }

                    else {
                        if(miner.getDirection() == "right" && miner.getX() + 1 < size) {
                            System.out.println("moved right");
                            miner.move(this);
                            ctrM++;
                        }
                        else if(miner.getDirection() == "left" && miner.getX() - 1 > 0) {
                            System.out.println("moved left");
                            miner.move(this);
                            ctrM++;
                        }
                        else if(miner.getDirection() == "up" && miner.getY() - 1 > 0) {
                            System.out.println("moved up");
                            miner.move(this);
                            ctrM++;
                        }
                        else if(miner.getDirection() == "down" && miner.getY() + 1 < size) {
                            System.out.println("moved down");
                            miner.move(this);
                            ctrM++;
                        }
                    }
                    db.getMovesCtr().setText(String.valueOf(ctrM));
                    db.getRotateCtr().setText(String.valueOf(ctrR));
                    repaint();
                }
            System.out.println("------------------------");
        }
        else
            JOptionPane.showMessageDialog(this, "Missing Configurations");

    }

    public void smart() {
       List<Path> track = solve();
       Collections.reverse(track);
       pathList = track;
       int ctrM = 0, ctrR = 0;
        db.getMovesCtr().setText("0");
        db.getRotateCtr().setText("0");

       for(Path spot : track) {
           if(spot.getX() == 0 && spot.getY() == 0)
               continue;

           if(miner.getX()+1 == spot.getX() && miner.getY() == spot.getY()) {
               while(miner.getDirection() != "right") {
                   miner.rotate();
                   System.out.println("rotated");
                   ctrR++;
               }
               miner.move(this);
               ctrM++;
               System.out.println("moved right");
           }

           else if(miner.getX()-1 == spot.getX() && miner.getY() == spot.getY()) {
               while(miner.getDirection() != "left") {
                   miner.rotate();
                   System.out.println("rotated");
                   ctrR++;
               }
               miner.move(this);
               ctrM++;
               System.out.println("moved left");
           }

           else if(miner.getX() == spot.getX() && miner.getY()+1 == spot.getY()) {
               while(miner.getDirection() != "down") {
                   miner.rotate();
                   System.out.println("rotated");
                   ctrR++;
               }
               miner.move(this);
               ctrM++;
               System.out.println("moved down");
           }

           else if(miner.getX() == spot.getX() && miner.getY()-1 == spot.getY()) {
               while(miner.getDirection() != "up") {
                   miner.rotate();
                   System.out.println("rotated");
                   ctrR++;
               }
               miner.move(this);
               ctrM++;
               System.out.println("moved up");
           }
           db.getMovesCtr().setText(String.valueOf(ctrM+1));
           db.getRotateCtr().setText(String.valueOf(ctrR));
            repaint();
       }
        System.out.println("------------------------");
    }

    private List<Path> backtrackPath(Path cur) {
        List<Path> path = new ArrayList<>();
        Path iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getParent();
        }

        return path;
    }

    private List<Path> solve() {
        LinkedList<Path> nextToVisit = new LinkedList<>();
        Path start = new Path(0,0);
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Path cur = nextToVisit.remove();

            if( cur.getX() < 0 || cur.getX() >= size || cur.getY() < 0 || cur.getY() >= size)
                continue;

            if (miner.getSpot(cur.getX(), cur.getY()) == 'P')
                continue;

            if (miner.getSpot(cur.getX(), cur.getY()) == 'G') {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
                Path coordinate = new Path(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                nextToVisit.add(coordinate);
            }
        }
        return Collections.emptyList();
    }
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
}