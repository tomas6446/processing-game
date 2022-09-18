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
public class Enemy extends GameElement {

    public Enemy(PImage sprite, int xPos, int yPos) {
        super(sprite, ObjectType.ENEMY, xPos, yPos);
    }
}
