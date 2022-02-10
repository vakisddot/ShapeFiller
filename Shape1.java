package ShapeFiller;

import java.awt.Color;

public class Shape1
{
    public String type;
    public int x, y, width, height;
    public int thickness;
    public String name;
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    public Shape1(String TYPE, int X, int Y, int WIDTH, int HEIGHT, Color OUTLINECOLOR, 
                boolean ISFILLED, Color FILLCOLOR, int THICKNESS)
    {
        this.type = TYPE;
        this.x = X;
        this.y = Y;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.outlineColor = OUTLINECOLOR;
        this.isFilled = ISFILLED;
        this.fillColor = FILLCOLOR;
        this.thickness = THICKNESS;

        // Generates shape name based on its type and colors
        this.name = type + Config.shapesList.size() + "-" + Integer.toHexString(outlineColor.getRGB()).substring(2) +
                    "-" + Integer.toHexString(fillColor.getRGB()).toUpperCase().substring(2);

        Config.shapesList.add(this);
    }
}