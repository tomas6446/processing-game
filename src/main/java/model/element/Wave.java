package model.element;

import lombok.Getter;
import lombok.Setter;
import model.type.ObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Kozakas, 1 grupe
 * Wave is an object that holds and spawns enemy spells
 */
@Getter
@Setter
public class Wave extends GameElement {
    private final List<Spell> spellList = new ArrayList<>();
    private int lastObstacleSpawn;
    private int speed;
    public Wave(Texture texture, int xPos, int yPos, int width, int height, int speed) {
        super(texture, ObjectType.OBSTACLE, xPos, yPos, width, height);
        this.speed = speed;
        spawnObstacle();
    }

    public void spawnObstacle() {
        Texture texture = getTexture();
        int xPos = getXPos();
        int yPos = getYPos();
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 0, -speed)); /* direction up */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), speed, -speed)); /* up right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -speed, 0)); /* left */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -speed, -speed)); /* up left */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), 0, speed));  /* down */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), speed, speed)); /* down right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), speed, 0)); /* right */
        spellList.add(new Spell(texture, xPos, yPos, getWidth(), getHeight(), -speed, speed)); /* down left */

    }
}
