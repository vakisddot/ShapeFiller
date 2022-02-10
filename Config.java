package ShapeFiller;

import javax.swing.*;

import ShapeFiller.Panels.ObjectPanel;

import java.awt.*;
import java.util.LinkedList;

public class Config 
{
    // Global vars

    // Stores all shapes that have been added by user
    public static LinkedList<Shape1> shapesList = new LinkedList<Shape1>();

    public static Color backgroundColor = new Color(11, 32, 39);

    // false - Add Mode, true - Select Mode
    public static boolean selectMode = true;

    public static Shape1 currentSelectedShape = null;

    // Shape outline
    public static Color outlineColor = Color.white;
    public static int outlineThickness = 2;

    public static String currentShapeType = "Rectangle";

    // Shape Fill
    public static boolean isFilled = false;
    public static Color fillColor = Color.white;

    // Model of objects combobox
    public static DefaultComboBoxModel model;

    // Helper function that generates a text label
    public static void NewTextLabel(String text, JPanel panel)
    {
        JLabel newText = new JLabel();
        newText.setText(text);
        newText.setForeground(Color.white);
        newText.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(newText);
    }

    // This method sets the currentSelectedShape field to its proper object
    public static void SetCurrentSelectedShape(ObjectPanel objectPanel)
    {
        for (Shape1 shape : shapesList)
        {
            if (shape.name == model.getSelectedItem())
            {
                currentSelectedShape = shape;
                
                // Updates the object panel and config vars to the values of the selected object
                outlineThickness = shape.thickness;
                objectPanel.slider.setValue(shape.thickness);

                outlineColor = shape.outlineColor;
                objectPanel.outlineButton.setBackground(shape.outlineColor);

                fillColor = shape.fillColor;
                objectPanel.fillButton.setBackground(shape.fillColor);

                isFilled = shape.isFilled;
                objectPanel.checkBox.setSelected(shape.isFilled);
                
                currentShapeType = shape.type;
                objectPanel.objectsCB.setSelectedItem(shape.type);
            }
        }
    }
}
