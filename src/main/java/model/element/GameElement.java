package model.element;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public abstract class GameElement {

    private PImage sprite;
    private ObjectType objectType;
    private int xPos;
    private int yPos;

    public void move(int xDelta, int yDelta) {
        xPos += xDelta;
        yPos += yDelta;
    }
}
