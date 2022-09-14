package Model;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

/**
 * @author tomas
 */
@Getter
@Setter
public class Object extends MapObject {
    private final PImage sprite;
    private int xPos;
    private int yPos;
    private boolean collidable;
    private int id;

    public Object(PImage sprite, int xPos, int yPos, int width, int height, boolean collidable, int id) {
        this.sprite = sprite.get(0, 0, width, height);
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        this.collidable = collidable;
    }
}
