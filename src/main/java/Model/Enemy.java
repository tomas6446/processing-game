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
public class Enemy extends GameElement  {
    private PImage sprite;
    private int xPos;
    private int yPos;

    public Enemy(PImage sprite, int xPos, int yPos) {
        this.sprite = sprite;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
