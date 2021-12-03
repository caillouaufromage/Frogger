package graphicalElements;

import util.Case;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class Element extends Case{
    public  BufferedImage image;

    public Element(int absc, int ord, String name) {
        super(absc, ord);
        image = Sprite.map.get(name);
    }

    public Element(Case c, String name) {
        super(c.absc, c.ord);
        image = Sprite.map.get(name);
    }
}
