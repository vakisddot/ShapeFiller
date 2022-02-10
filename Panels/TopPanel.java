package ShapeFiller.Panels;

import javax.swing.*;

import ShapeFiller.Config;

import java.awt.event.*;
import java.awt.*;

public class TopPanel extends JPanel implements ActionListener
{
    // Panels   
    public ObjectPanel objectPanel;
    public ScreenPanel screenPanel;

    public GridBagConstraints c = new GridBagConstraints();

    DefaultComboBoxModel model;

    // Settings
    JButton bgColorButton, selButton, addButton, delButton;
    JComboBox comboBox;

    // TOP PANEL
    public TopPanel()
    {
        // SETUP
        this.setBackground(new Color(112, 169, 161));
        this.setLayout(new GridLayout(1, 1, 35, 20));

        // LAYOUT
        bgColorButton = new JButton("Background Color");
        bgColorButton.setForeground(Color.white);
        bgColorButton.setBackground(Config.backgroundColor);
        bgColorButton.addActionListener(this);
        this.add(bgColorButton);

        Config.NewTextLabel("Current Object", this);
        model = new DefaultComboBoxModel();
        Config.model = model;
        comboBox = new JComboBox(model);
        comboBox.addActionListener(this);
        this.add(comboBox);

        selButton = new JButton("Select");
        selButton.addActionListener(this);
        this.add(selButton);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        this.add(addButton);

        delButton = new JButton("Delete");
        delButton.addActionListener(this);
        this.add(delButton);

        // CONTRAINTS
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = c.weighty = 0.25;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Changes background color
        if (e.getSource() == bgColorButton)
        {
            Color newColor = JColorChooser.showDialog(this, "Pick Background Color", Config.backgroundColor);
            if (newColor != null)
            {
                Config.backgroundColor = newColor;
                bgColorButton.setBackground(newColor);
                screenPanel.repaint();
            }
        }
        // Deletes current selected shape
        else if (e.getSource() == delButton)
        {
            if (Config.currentSelectedShape == null)
                return;
            // Update Model
            Config.model.setSelectedItem(null);
            Config.model.removeElement(Config.currentSelectedShape.name);
            // Update List
            Config.shapesList.remove(Config.currentSelectedShape);
            Config.currentSelectedShape = null;
            
            screenPanel.repaint();
        }
        // Clicking on add just deselects the current shape
        else if (e.getSource() == addButton)
        {
            Config.selectMode = false;
            Config.model.setSelectedItem(null);
            Config.currentSelectedShape = null;

            screenPanel.repaint();
        }
        else if (e.getSource() == selButton)
        {
            Config.selectMode = true;
            Config.SetCurrentSelectedShape(objectPanel);

            screenPanel.repaint();
        }
        else if (e.getSource() == comboBox)
        {
            Config.SetCurrentSelectedShape(objectPanel);

            screenPanel.repaint();
        }
    }
}
