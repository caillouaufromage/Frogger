package graphicalElements;

import graphicalElements.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class ImageResizer {
     public static ImageIcon resize(String path, int width, int height){
         ImageIcon imageIcon = new ImageIcon((ImageLoader.loadImage(path))); // load the image to a imageIcon
         Image image = imageIcon.getImage(); // transform it
         Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
         return imageIcon = new ImageIcon(newimg);  // transform it back
     }
}
