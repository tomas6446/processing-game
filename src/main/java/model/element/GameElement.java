package model.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.type.ObjectType;

/**
 * @author Tomas Kozakas, 1 grupe
 * Holds object texture, type, position, width, height, as well as move method
 * to move every object on the map if player collided
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class GameElement {

    private Texture texture;
    private ObjectType objectType;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public void move(int xDelta, int yDelta) {
        xPos += xDelta;
        yPos += yDelta;
    }
}
