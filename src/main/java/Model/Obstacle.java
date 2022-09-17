package Model;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Obstacle {
    private int xPos;
    private int yPos;
    private final PImage sprite;
    private final List<Spell> spellList = new ArrayList<>();

    public Obstacle(PImage sprite, int xPos, int yPos) {
        this.sprite = sprite;
        this.xPos = xPos;
        this.yPos = yPos;

        spawnObstacle();
    }

    public void spawnObstacle() {
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
