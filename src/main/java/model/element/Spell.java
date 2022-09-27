package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.type.ObjectType;

/**
 * @author Tomas Kozakas, 1 grupe
 */

@Getter
@Setter
@NoArgsConstructor
public class Spell extends GameElement {
    private int initialXPos;
    private int initialYPos;
    private int xDelta;
    private int yDelta;
    private int speed;

    public Spell(Texture texture, int xPos, int yPos, int width, int height, int xDelta, int yDelta) {
        super(texture, ObjectType.SPELL, xPos, yPos, width, height);
        this.initialXPos = xPos;
        this.initialYPos = yPos;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    public void move() {
        setXPos(getXPos() + xDelta + speed);
        setYPos(getYPos() + yDelta + speed);
    }
}
