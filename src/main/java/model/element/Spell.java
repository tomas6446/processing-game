package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.type.ObjectType;
import processing.core.PImage;

/**
 * @author tomas
 */

@Getter
@Setter
@NoArgsConstructor
public class Spell extends GameElement {

    private int initialXPos;
    private int initialYPos;
    private int xDelta;
    private int yDelta;

    public Spell(PImage sprite, int xPos, int yPos, int xDelta, int yDelta) {
        super(sprite.get(64, 64, 128, 128), ObjectType.SPELL, xPos + 16, yPos + 16);
        this.initialXPos = xPos;
        this.initialYPos = yPos;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    public void move() {
        int xPos = getXPos();
        setXPos(xPos += xDelta);
        int yPos = getYPos();
        setYPos(yPos += yDelta);
    }
}
