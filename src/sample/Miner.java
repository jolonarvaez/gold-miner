package sample;

import javax.swing.*;

public class Miner extends Object{

    private int gridSize;
    private boolean right, up, down, left, gameOver;
    private char[][] map;

    public Miner(int x, int y, int size) {
        super(x, y);
        right = true;
        gameOver = false;
        gridSize = size;
    }

    public void setMap(char[][] m) { map = m; }

    public boolean getStatus() {return gameOver;}

    public void move(Grid g) {
        if(right)
            setX(getX() + 1);
        else if(left)
            setX(getX() - 1);
        else if(up)
            setY(getY() - 1);
        else if(down)
            setY(getY() + 1);

        if(map[getX()][getY()] == 'G') {
            gameOver = true;
            JOptionPane.showMessageDialog(g, "Search Successful");
        }
        else if(map[getX()][getY()] == 'P') {
            gameOver = true;
            JOptionPane.showMessageDialog(g, "Game Over");
        }
    }

    public void rotate() {
        if(right) {
            right = false;
            down = true;
        }
        else if(down) {
            down = false;
            left = true;
        }
        else if(left) {
            left = false;
            up = true;
        }
        else if(up) {
            up = false;
            right = true;
        }
    }
    public String getDirection() {
        if(right)
            return ("right");
        else if(left)
            return ("left");
        else if(up)
            return ("up");
        else if(down)
            return ("down");
        else
            return null;
    }

    public char getSpot(int x, int y) { return map[x][y]; }

    public String scan() {
        if(right) {
            for(int i = getX(); i < gridSize; i++) {
                if(map[i][getY()] == 'G')
                    return "G";
                else if(map[i][getY()] == 'P')
                    return "P";
                else if(map[i][getY()] == 'B')
                    return "B";
            }
        }
        else if(down) {
            for(int i = getY(); i < gridSize; i++) {
                if(map[getY()][i] == 'G')
                    return "G";
                else if(map[getY()][i] == 'P')
                    return "P";
                else if(map[getY()][i] == 'B')
                    return "B";
            }
        }
        else if(left) {
            for(int i = getX(); i >= 0; i--) {
                if(map[i][getY()] == 'G')
                    return "G";
                else if(map[i][getY()] == 'P')
                    return "P";
                else if(map[i][getY()] == 'B')
                    return "B";
            }
        }
        else if(up) {
            for(int i = getY(); i >= 0; i--) {
                if(map[getY()][i] == 'G')
                    return "G";
                else if(map[getY()][i] == 'P')
                    return "P";
                else if(map[getY()][i] == 'B')
                    return "B";
            }
        }
        return null;
    }





}
