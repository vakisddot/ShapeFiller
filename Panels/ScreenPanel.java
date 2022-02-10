package ShapeFiller.Panels;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import ShapeFiller.Config;
import ShapeFiller.Shape1;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ScreenPanel extends JPanel implements MouseInputListener
{   
    // Panels
    public ObjectPanel objectPanel;
    public TopPanel topPanel;

    public GridBagConstraints c = new GridBagConstraints(); 

    Point mouseStart, mouseEnd, dragLineCurrent;
    boolean drawDragLine = false, movingShape = false;

    // SCREEN PANEL
    public ScreenPanel()
    {
        // SETUP
        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // CONTRAINTS
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 4;
        c.weightx = c.weighty = 4;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        this.setBackground(Config.backgroundColor);

        // Draws shapes
        if (!Config.shapesList.isEmpty())
        {   
            for (Shape1 shape : Config.shapesList)
            {
                // Sets thickness
                g2D.setStroke(new BasicStroke(shape.thickness));
                
                // Draws fill of shape
                if (shape.isFilled)
                {
                    g2D.setColor(shape.fillColor);
                    if (shape.type == "Rectangle")
                        g2D.fillRect(shape.x, shape.y, shape.width, shape.height);
                    else if (shape.type == "Oval")
                        g2D.fillOval(shape.x, shape.y, shape.width, shape.height);
                }
                
                // Draws outline of shape
                g2D.setColor(shape.outlineColor);

                if (shape.type == "Rectangle")
                    g2D.drawRect(shape.x, shape.y, shape.width, shape.height);
                else if (shape.type == "Oval")
                    g2D.drawOval(shape.x, shape.y, shape.width, shape.height);
                else
                    g2D.drawLine(shape.x, shape.y, shape.width, shape.height);
            }
        }

        // This draws the X on the selected shape (or the line if the object is a line)
        if (Config.selectMode && Config.currentSelectedShape != null)
        {
            int x = Config.currentSelectedShape.x, y = Config.currentSelectedShape.y, 
                	width = Config.currentSelectedShape.width, height = Config.currentSelectedShape.height;
            
            g2D.setStroke(new BasicStroke(2));

            if (Config.currentSelectedShape.type == "Line")
            {
                g2D.setColor(new Color(Color.white.getRGB() - Config.currentSelectedShape.outlineColor.getRGB()));

                g2D.drawLine(x, y, width, height);
            }
            else
            {
                g2D.setColor(new Color(Color.white.getRGB() - Config.currentSelectedShape.fillColor.getRGB()));

                float d = 1.5f;
                g2D.drawLine(x + (int)(width / d), y + (int)(height / d), 
                            x + width - (int)(width / d), y + height - (int)(height / d));
                g2D.drawLine(x + width - (int)(width / d), y + (int)(height / d), 
                            x + (int)(width / d), y + height - (int)(height / d));
            }
        }

        // This draws the drag line
        if (drawDragLine)
        {
            g2D.setStroke(new BasicStroke(1));

            // Sets color of drag line to be the inverted color of the background color
            g2D.setColor(new Color(Color.white.getRGB() - Config.backgroundColor.getRGB()));

            g2D.drawLine(mouseStart.x, mouseStart.y, dragLineCurrent.x, dragLineCurrent.y);
        }
    }

    public int[] CalculateDimensions(int startX, int startY, int endX, int endY)
    {
        int rX, rY, rW, rH = 0;
        if (endX > startX)
            {
                if (endY > startY)
                {
                    rX = startX;
                    rY = startY;
                    rW = endX - startX;
                    rH = endY - startY;
                }
                else
                {
                    rX = startX;
                    rY = endY;
                    rW = endX - startX;
                    rH = startY - endY;
                }
            }
        else
            {
                if (endY > startY)
                {
                    
                    rX = endX;
                    rY = startY;
                    rW = startX - endX;
                    rH = endY - startY;
                }
                else
                {
                    rX = endX;
                    rY = endY;
                    rW = startX - endX;
                    rH = startY - endY;
                }
            }
            
            int[] arr = {rX, rY, rW, rH};
            return arr;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) 
    {
        // Gets current position of cursor
        mouseStart = e.getPoint();

        // Selects shape
        if (Config.selectMode && !Config.shapesList.isEmpty())
        {
            // Iterates through all shapes and checks if user has clicked on one of them 
            // (Doesn't work for lines)
            for (int i = Config.shapesList.size() - 1; i >= 0; i--)
            {
                Shape1 shape = Config.shapesList.get(i);

                // Checks if cursor was on shape when it was clicked
                if (mouseStart.getX() >= shape.x && mouseStart.getX() <= shape.x + shape.width &&
                    mouseStart.getY() >= shape.y && mouseStart.getY() <= shape.y + shape.height && shape.type != "Line")
                {
                    // If shape is currently selected then activate "moving" mode
                    if (Config.currentSelectedShape == shape)
                    {
                        movingShape = true;
                    }
                    else
                    {
                        Config.model.setSelectedItem(shape.name);
                        Config.SetCurrentSelectedShape(objectPanel);
                    }
                    break;
                }
                // If no shape was found (this is the last loop iteration)
                else if (i == 0)
                {
                    Config.model.setSelectedItem(null);
                    Config.currentSelectedShape = null;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        // Gets current position of cursor
        mouseEnd = e.getPoint();

        // Creates new shape
        if (!Config.selectMode)
        {
            int[] shapeArea;

            // Calculates the shape x, y, width and height based on mouse positions
            if (Config.currentShapeType != "Line")
                shapeArea = CalculateDimensions(mouseStart.x, mouseStart.y, mouseEnd.x, mouseEnd.y); 
            else
                shapeArea = new int[]{mouseStart.x, mouseStart.y, mouseEnd.x, mouseEnd.y};

            Shape1 newShape;
            newShape = new Shape1(Config.currentShapeType, shapeArea[0], shapeArea[1], shapeArea[2], shapeArea[3], 
                        Config.outlineColor, Config.isFilled, Config.fillColor, Config.outlineThickness);

            // Adds new shape to combobox list and sets it as the current selected item
            Config.model.addElement(newShape.name);
            Config.model.setSelectedItem(newShape.name);
        }
        // Moves shape
        else if (Config.currentSelectedShape != null && movingShape)
        {
            int[] shapeArea = CalculateDimensions(mouseEnd.x, mouseEnd.y, 
                            mouseEnd.x + Config.currentSelectedShape.width, mouseEnd.y + Config.currentSelectedShape.height);

            Config.currentSelectedShape.x = shapeArea[0] - shapeArea[2] / 2;
            Config.currentSelectedShape.y = shapeArea[1] - shapeArea[3] / 2;
            Config.currentSelectedShape.width = shapeArea[2];
            Config.currentSelectedShape.height = shapeArea[3];

            movingShape = false;
        }
        drawDragLine = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        // Draws the drag line
        drawDragLine = true;
        dragLineCurrent = e.getPoint();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
