package graphicalElements;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Sprite {
    public static Map<String,BufferedImage> map = new HashMap<>();
    static String path =  "../resources/img/";
    public static void init() {
        map.put( "bergeBot" , ImageLoader.loadImage(path + "bergeBot.png"));
        map.put("bergeTop" , ImageLoader.loadImage(path + "bergeTop.png"));

        map.put("trap1" , ImageLoader.loadImage(path + "trap1.png"));
        map.put("bonus" , ImageLoader.loadImage(path + "bonus.png"));
        map.put("slide", ImageLoader.loadImage(path + "slide.png"));
        map.put("wall", ImageLoader.loadImage(path + "wall.png"));

        map.put("water0" , ImageLoader.loadImage(path + "water0.png"));
        map.put("water1" , ImageLoader.loadImage(path + "water1.png"));
        map.put("water2" , ImageLoader.loadImage(path + "water2.png"));


        map.put("grass0" , ImageLoader.loadImage(path + "grass0.png"));
        map.put("grass1" , ImageLoader.loadImage(path + "grass1.png"));
        map.put("grass2" , ImageLoader.loadImage(path + "grass2.png"));
        map.put("grass3" , ImageLoader.loadImage(path + "grass3.png"));

        map.put("snakeRight" , ImageLoader.loadImage(path + "snakeRight.png"));
        map.put("herissonRight" , ImageLoader.loadImage(path + "herissonRight.png"));
        map.put("snakeLeft" , ImageLoader.loadImage(path + "snakeLeft.png"));
        map.put("herissonLeft" , ImageLoader.loadImage(path + "herissonLeft.png"));

        map.put("nenupharRight" , ImageLoader.loadImage(path + "nenupharRight.png"));
        map.put("turtleRight" , ImageLoader.loadImage(path + "turtleRight.png"));
        map.put("nenupharLeft" , ImageLoader.loadImage(path + "nenupharLeft.png"));
        map.put("turtleLeft" , ImageLoader.loadImage(path + "turtleLeft.png"));

        map.put("frogger" , ImageLoader.loadImage(path + "frogger.png"));
    }


}
