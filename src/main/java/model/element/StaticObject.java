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
public class StaticObject extends GameElement {
    private boolean collidable;

    public StaticObject(Texture texture, int xPos, int yPos, int width, int height, boolean collidable, ObjectType id) {
        super(texture, id, xPos, yPos, width, height);
        this.collidable = collidable;
    }
}
