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
abstract public class MapObject extends GameElement {
    private PImage sprite;
    private int xPos;
    private int yPos;
    private boolean collidable;
    private int id;

    public MapObject(PImage sprite, int xPos, int yPos, boolean collidable, int id) {
        this.sprite = sprite;
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        this.collidable = collidable;
    }

    public void update(int xDelta, int yDelta) {

    }
}
