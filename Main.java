package ShapeFiller;

import javax.swing.*;

import ShapeFiller.Panels.*;

import java.awt.*;

public class Main
{
    public static void main(String[] args) {
        // The object panel is the panel on the right of the screen that contains information about the current selected object
        ObjectPanel objectPanel = new ObjectPanel();
        // The screen panel is the board on which objects are drawn
        ScreenPanel screenPanel = new ScreenPanel();
        // The top panel contains the object list and options to select, add and delete objects
        TopPanel topPanel = new TopPanel();

        // Passing around panels so they can communicate with each other
        objectPanel.screenPanel = screenPanel;
        objectPanel.topPanel = topPanel;
        screenPanel.objectPanel = objectPanel;
        screenPanel.topPanel = topPanel;
        topPanel.objectPanel = objectPanel;
        topPanel.screenPanel = screenPanel;

        // Frame
        JFrame frame = new JFrame();
        frame.setTitle("ShapeFiller");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(11, 32, 39));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        frame.setLayout(new GridBagLayout());
        frame.add(topPanel, topPanel.c);
        frame.add(screenPanel, screenPanel.c);
        frame.add(objectPanel, objectPanel.c);
        
        frame.setVisible(true);
    }
}