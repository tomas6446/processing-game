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
public class StaticObject extends GameElement {
    private boolean collidable;

    public StaticObject(PImage sprite, int xPos, int yPos, int width, int height, boolean collidable, ObjectType id) {
        super(sprite.get(0, 0, width, height), id, xPos, yPos);
        this.collidable = collidable;
    }
}
