package caseSpeciale;

import java.awt.*;

public enum CaseSpecialeEnum {
    Trap(Color.RED),
    Wall(Color.DARK_GRAY),
    Slide(new Color(150, 77, 45)),
    Water(Color.PINK),
    Bonus(Color.ORANGE);
    private final Color color;
    CaseSpecialeEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
