package model.element;

import lombok.Getter;
import lombok.Setter;
import model.type.ObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Obstacle extends GameElement {

    private final List<Spell> spellList = new ArrayList<>();

    public Obstacle(Texture texture, int xPos, int yPos, int width, int height) {
        super(texture, ObjectType.OBSTACLE, xPos, yPos, width, height);
        spawnObstacle();
    }

    public void spawnObstacle() {
        Texture texture = getTexture();
        int xPos = getXPos();
        int yPos = getYPos();
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 0, -1)); /* direction up */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 1, -1)); /* up right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -1, 0)); /* left */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -1, -1)); /* up left */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 0, 1));  /* down */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 1, 1)); /* down right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 1, 0)); /* right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -1, 1)); /* down left */
    }
}
