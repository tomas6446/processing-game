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

    public Spell(PImage sprite, int xPos, int yPos, int width, int height, int xDelta, int yDelta) {
        this.sprite = sprite.get(0, 0, width, height);
        this.xPos = xPos;
        this.yPos = yPos;
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
