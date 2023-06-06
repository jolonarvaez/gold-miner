package sample;

import javax.swing.*;
import java.awt.*;

public class DashBoard extends JPanel {

    private JButton start, reset;
    private JLabel moves, rotate, scan;
    private JTextField movesCtr, rotateCtr, scanCtr;


    public DashBoard()
    {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 2);

        start = new JButton("Start");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(start, gbc);

        moves = new JLabel("Moves: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(moves, gbc);

        rotate = new JLabel("Rotate: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(rotate, gbc);

        scan = new JLabel("Scans: ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(scan, gbc);
        
        movesCtr = new JTextField("0", 2);
        movesCtr.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(movesCtr, gbc);

        rotateCtr = new JTextField("0", 2);
        rotateCtr.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(rotateCtr, gbc);

        scanCtr = new JTextField("0", 2);
        scanCtr.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(scanCtr, gbc);

        reset = new JButton("Reset");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(reset, gbc);
      }

    public JButton getStart() {return start;}

    public JButton getReset() {return reset;}

    public JTextField getMovesCtr() {return movesCtr;}

    public JTextField getRotateCtr() {return rotateCtr;}

    public JTextField getScanCtr() {return scanCtr;}
}
