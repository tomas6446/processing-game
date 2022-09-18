package model.element;

import lombok.Getter;
import lombok.Setter;
import model.type.ObjectType;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Obstacle extends GameElement {

    private final List<Spell> spellList = new ArrayList<>();

    public Obstacle(PImage sprite, int xPos, int yPos) {
        super(sprite, ObjectType.OBSTACLE, xPos, yPos);
        spawnObstacle();
    }

    public void spawnObstacle() {
        PImage sprite = getSprite();
        int xPos = getXPos();
        int yPos = getYPos();
        spellList.add(new Spell(sprite, xPos, yPos, 0, -1)); /* direction up */
        spellList.add(new Spell(sprite, xPos, yPos, 1, -1)); /* up right */
        spellList.add(new Spell(sprite, xPos, yPos, -1, 0)); /* left */
        spellList.add(new Spell(sprite, xPos, yPos, -1, -1)); /* up left */
        spellList.add(new Spell(sprite, xPos, yPos, 0, 1));  /* down */
        spellList.add(new Spell(sprite, xPos, yPos, 1, 1)); /* down right */
        spellList.add(new Spell(sprite, xPos, yPos, 1, 0)); /* right */
        spellList.add(new Spell(sprite, xPos, yPos, -1, 1)); /* down left */
    }
}
