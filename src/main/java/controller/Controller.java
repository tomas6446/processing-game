package controller;

import model.Map;
import model.element.Obstacle;
import model.element.Spell;
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
        map.getStaticObjects().forEach(object -> object.move(xDelta * offset, yDelta * offset));
        map.getEnemies().forEach(enemy -> enemy.move(xDelta * offset, yDelta * offset));

        for (Obstacle obstacle : map.getObstacles()) {
            for (Spell spell : obstacle.getSpellList()) {
                spell.setXPos(spell.getXPos() + xDelta * offset);
                spell.setYPos(spell.getYPos() + yDelta * offset);
            }
            obstacle.setXPos(obstacle.getXPos() + xDelta * offset);
            obstacle.setYPos(obstacle.getYPos() + yDelta * offset);
        }
    }

    public void center() {
        //map.getObjects().forEach(object -> object.setXPos(object.getXPos() - ((map.getGrid().length * 64) / 2) + map.getPlayer().getXPos() + 36));
        //map.getObjects().forEach(object -> object.setYPos(object.getYPos() - ((map.getGrid()[0].length * 64) / 2) + map.getPlayer().getYPos() / 2 + 96));
    }
}
