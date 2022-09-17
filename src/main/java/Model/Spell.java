package Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import processing.core.PImage;

/**
 * @author tomas
 */

@Getter
@Setter
@NoArgsConstructor
public class Spell extends GameElement {
    private PImage sprite;
    private int xPos;
    private int yPos;
    private int initialXPos;
    private int initialYPos;
    private int xDelta;
    private int yDelta;

    public Spell(PImage sprite, int xPos, int yPos, int xDelta, int yDelta) {
        this.sprite = sprite.get(64, 64, 128, 128);
        this.xPos = xPos + 16;
        this.yPos = yPos + 16;
        this.initialXPos = xPos;
        this.initialYPos = yPos;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }
    public void move() {
        xPos += xDelta;
        yPos += yDelta;
    }
}
