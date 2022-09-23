package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.type.ObjectType;

/**
 * @author tomas
 */
@Getter
@Setter
@NoArgsConstructor
public class Enemy extends GameElement {
    public Enemy(Texture texture, int xPos, int yPos, int width, int height) {
        super(texture, ObjectType.ENEMY, xPos, yPos, width, height);
    }
}
