package ShapeFiller.Panels;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ShapeFiller.Config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ObjectPanel extends JPanel implements ChangeListener, ActionListener
{
    // Panels
    public ScreenPanel screenPanel;
    public TopPanel topPanel;

    public GridBagConstraints c = new GridBagConstraints(); 

    // Options
    public JSlider slider;
    public JButton outlineButton, fillButton;
    public JCheckBox checkBox;
    public JComboBox objectsCB;

    // OBJECT PANEL
    public ObjectPanel()
    {
        // SETUP
        this.setBackground(new Color(64, 121, 140));
        this.setLayout(new GridLayout(12, 2, 5, 20));

        // LAYOUT
        Config.NewTextLabel("Shape", this);
        objectsCB = new JComboBox(new String[] {"Rectangle", "Oval", "Line"});
        objectsCB.addActionListener(this);
        this.add(objectsCB);

        Config.NewTextLabel("Outline Thickness", this);
        slider = new JSlider(0, 20, 2);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        this.add(slider);

        outlineButton = new JButton("Outline Color");
        outlineButton.setBackground(Config.outlineColor);
        outlineButton.addActionListener(this);
        this.add(outlineButton);

        fillButton = new JButton("Fill Color");
        fillButton.setBackground(Config.fillColor);
        fillButton.addActionListener(this);
        this.add(fillButton);

        checkBox = new JCheckBox("Is Filled");
        checkBox.setBackground(null);
        checkBox.setForeground(Color.white);
        checkBox.addActionListener(this);
        this.add(checkBox);

        // CONTRAINTS
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 5;
        c.weightx = c.weighty = 0.75;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (Config.currentSelectedShape != null)
        {
            Config.currentSelectedShape.thickness = slider.getValue();
            screenPanel.repaint();
        }
        Config.outlineThickness = slider.getValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == outlineButton)
        {
            Color newColor = JColorChooser.showDialog(this, "Pick Outline Color", Config.outlineColor);
            if (newColor != null)
            {
                if (Config.currentSelectedShape != null)
                {
                    Config.currentSelectedShape.outlineColor = newColor;
                }
                Config.outlineColor = newColor;
                outlineButton.setBackground(Config.outlineColor);
            }
        }

        else if (e.getSource() == fillButton)
        {
            Color newColor = JColorChooser.showDialog(this, "Pick Fill Color", Config.fillColor);
            if (newColor != null)
            {
                if (Config.currentSelectedShape != null)
                {
                    Config.currentSelectedShape.fillColor = newColor;
                }
                Config.fillColor = newColor;
                fillButton.setBackground(Config.fillColor);
            }
        }

        else if (e.getSource() == checkBox)
        {
            if (checkBox.isSelected())
            {
                if (Config.currentSelectedShape != null)
                {
                    Config.currentSelectedShape.isFilled = true;
                }
                Config.isFilled = true;
            }
            else
            {
                if (Config.currentSelectedShape != null)
                {
                    Config.currentSelectedShape.isFilled = false;
                }
                Config.isFilled = false;
            }
        }

        else if (e.getSource() == objectsCB)
        {
            if (Config.currentSelectedShape != null)
            {
                Config.currentSelectedShape.type = objectsCB.getSelectedItem().toString();
            }
            Config.currentShapeType = objectsCB.getSelectedItem().toString();    
        }
        screenPanel.repaint();
    }
}
