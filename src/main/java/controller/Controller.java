package controller;

import lombok.Getter;
import lombok.Setter;
import model.Map;
import model.element.Obstacle;

import java.util.List;

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
        map.getSky().move(xDelta * offset, yDelta * offset);

        List<Obstacle> obstacles = map.getObstacles();
        obstacles.forEach(obstacle -> {
            obstacle.setXPos(obstacle.getXPos() + xDelta * offset);
            obstacle.setYPos(obstacle.getYPos() + yDelta * offset);
            obstacle.getSpellList().forEach(spell -> {
                spell.setXPos(spell.getXPos() + xDelta * offset);
                spell.setYPos(spell.getYPos() + yDelta * offset);
            });
        });
    }

    // TODO center map to make player at the center
    public void center() {
//        int distanceToCenter = (int) Math.sqrt((300 - map.getPlayer().getYPos()) * (300 - map.getPlayer().getYPos()) + (300 - map.getPlayer().getXPos()) * (300 - map.getPlayer().getXPos()));
//
//        map.getStaticObjects().forEach(object -> object.setXPos(object.getXPos() - distanceToCenter));
//        map.getStaticObjects().forEach(object -> object.setYPos(object.getYPos() - distanceToCenter));
//        map.getEnemies().forEach(enemy -> enemy.setXPos(enemy.getXPos() - distanceToCenter));
//        map.getEnemies().forEach(enemy -> enemy.setYPos(enemy.getYPos() - distanceToCenter));
//        map.getSky().setXPos(map.getSky().getXPos() - distanceToCenter);
//        map.getSky().setYPos(map.getSky().getYPos() - distanceToCenter);
//
//        for (Obstacle obstacle : map.getObstacles()) {
//            obstacle.setXPos(obstacle.getXPos()- distanceToCenter);
//            obstacle.setYPos(obstacle.getYPos() - distanceToCenter);
//
//            for (Spell spell : obstacle.getSpellList()) {
//                spell.setXPos(spell.getXPos() - distanceToCenter);
//                spell.setYPos(spell.getYPos() - distanceToCenter);
//            }
//
//        }
    }
}
