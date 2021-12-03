package util;

public enum ElementEnum {
    Bonus("bonus",1),
    Trap("trap1",1),
    Wall("wall",3),
    Slide("slide",1),

    Water("water0",0),
    Water1("water1",0),
    Water2("water2",0),

    Road("grass0",0),
    RoadBot("grass1",0),
    RoadMid("grass2",0),
    RoadTop("grass3",0),

    BergeTop("bergeTop",0),
    BergeBot("bergeBot",0),

    HerissonLeft("herissonLeft",2),
    HerissonRight("herissonRight",2),
    SnakeLeft("snakeLeft",2),
    SnakeRight("snakeRight",2),

    NenupharRight("nenupharRight",2),
    NenupharLeft("nenupharRight",2),
    TurtleLeft("turtleLeft",2),
    TurtleRight("turtleRight",2),

    FrogPlayer("frogger",3);

    private final String name;
    private final int zorder;
    ElementEnum(String name, int zorder) {
        this.name = name;
        this.zorder = zorder;
    }

    public String getName() {
        return name;
    }

    public int getZorder() {
        return zorder;
    }
}
