package Controller;

import Model.Map;
import Model.Obstacle;
import Model.Spell;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tomas
 */
@Getter
@Setter
public class Controller {
    private Map map;
    private boolean[] keys;
    private int xDelta;
    private int yDelta;

    public Controller(Map map) {
        this.map = map;
        this.keys = new boolean[128];
    }

    public void update(int offset) {
        map.getObjects().forEach(object -> object.update(xDelta * offset, yDelta * offset));
        map.getEnemies().forEach(enemy -> enemy.update(xDelta * offset, yDelta * offset));

        for (Obstacle obstacle : map.getObstacles()) {
            for (Spell spell : obstacle.getSpellList()) {
                spell.setXPos(spell.getXPos() + xDelta * offset);
                spell.setYPos(spell.getYPos() + yDelta * offset);
            }
            obstacle.setXPos(obstacle.getXPos() + xDelta * offset);
            obstacle.setYPos(obstacle.getYPos() + yDelta * offset);
        }
    }
}
