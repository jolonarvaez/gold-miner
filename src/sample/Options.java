package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Options extends JPanel implements ItemListener {

    private JButton update;
    private JToggleButton algo;
    private JTextField gridSize, gold, pit, beacon;
    private JLabel size, gL, pL, bL, state;
    public Options()
    {
        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);
        update = new JButton("Update");
        add(update);

        size = new JLabel("Size: ");
        size.setForeground(Color.WHITE);
        add(size);

        gridSize = new JTextField("",2);
        add(gridSize);

        gL = new JLabel("Gold: ");
        gL.setForeground(Color.WHITE);
        add(gL);

        gold = new JTextField("",5);
        add(gold);

        pL = new JLabel("Pit: ");
        pL.setForeground(Color.WHITE);
        add(pL);

        pit = new JTextField("",10);
        add(pit);

        bL = new JLabel("Beacon: ");
        bL.setForeground(Color.WHITE);
        add(bL);

        beacon = new JTextField("",10);
        add(beacon);

        setToggleButton();
        setAction();

    }

    private void setToggleButton()
    {
        algo = new JToggleButton("Random");
        add(algo);
    }

    private void setAction()
    {
        algo.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent eve)
    {
        if(algo.isSelected())
            algo.setText("Smart");

        else
            algo.setText("Random");

    }

    public JToggleButton getToggleButton() {return algo;}

    public JButton getUpdate() {return update;}

    public JToggleButton getAlgo() {return algo;}

    public String getGridSize() {return gridSize.getText();}

    public String getGoldText(){
        return gold.getText();
    }

    public String[] getPitText() {return pit.getText().split(" ");}

    public String[] getBeaconText() {return beacon.getText().split(" ");}

    public String getPit() {return pit.getText();}

    public String getBeacon() {return beacon.getText();}


}
