package controller;

import lombok.Getter;
import lombok.Setter;
import model.HealthBar;
import model.Map;
import model.element.*;
import model.type.ObjectType;

import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
/**
 The controller updates model's state, updates positions
 */
public class Controller {
    private Map map;
    private boolean[] keys;
    private int xDelta;
    private int yDelta;

    public Controller(Map map) {
        this.map = map;
        this.keys = new boolean[128];
    }

    public void updatePositions(int offset) {
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

    public void handleEvent() {
        xDelta = 0;
        yDelta = 0;

        if (keys['a']) {
            xDelta += map.getPlayer().getSpeed();
        }
        if (keys['w']) {
            yDelta += map.getPlayer().getSpeed();
        }
        if (keys['d']) {
            xDelta -= map.getPlayer().getSpeed();
        }
        if (keys['s']) {
            yDelta -= map.getPlayer().getSpeed();
        }

        handleCollision();
    }

    private void handleCollision() {
        updatePositions(1);

        map.getPlayer().updateDirection(getXDelta(), getYDelta());
        map.getObstacles().forEach(obstacle -> obstacle.getSpellList().forEach(Spell::move));

        isCollision(Spell.class, StaticObject.class);
        if (isCollision(Spell.class, Player.class)) {
            HealthBar healthBar = map.getHealthBar();
            healthBar.removeHealth();
            if (healthBar.getHealthCount() == 0) {
                map.setGameOver(true);
            }
        }
        if (isCollision(Player.class, StaticObject.class) || isCollision(Player.class, Enemy.class)) {
            updatePositions(-1);
        }
    }

    public boolean isCollision(Class<?> a, Class<?> b) {
        if (a.isAssignableFrom(Player.class) && b.isAssignableFrom(StaticObject.class)) {
            List<StaticObject> staticObjects = map.getStaticObjects();
            for (StaticObject staticObject : staticObjects) {
                if (staticObject.isCollidable()) {
                    Player player = map.getPlayer();
                    if (checkCollision(player.getXPos(), player.getYPos(), 36, 64, staticObject.getXPos(), staticObject.getYPos(), map.getTileSize(), map.getTileSize() - 48)) {
                        if (staticObject.getObjectType() == ObjectType.EXIT) {
                            map.setNextStage(true);
                        }
                        return true;
                    }
                }
            }
        } else if (a.isAssignableFrom(Spell.class) && b.isAssignableFrom(StaticObject.class)) {
            List<StaticObject> staticObjects = map.getStaticObjects();
            return staticObjects.stream().filter(StaticObject::isCollidable).
                    anyMatch(staticObject -> map.getObstacles().stream().
                            anyMatch(obstacle -> obstacle.getSpellList().
                                    removeIf(spell -> checkCollision(spell.getXPos(), spell.getYPos(), 32, 32, staticObject.getXPos(), staticObject.getYPos(), map.getTileSize(), map.getTileSize()))));
        } else if (a.isAssignableFrom(Player.class) && b.isAssignableFrom(Enemy.class)) {
            Player player = map.getPlayer();
            return map.getEnemies().stream()
                    .anyMatch(enemy -> checkCollision(enemy.getXPos(), enemy.getYPos(), 64, 64 - 48, player.getXPos(), player.getYPos(), 36, 64));
        } else if (a.isAssignableFrom(Spell.class) && b.isAssignableFrom(Player.class)) {
            Player player = map.getPlayer();
            return map.getObstacles().stream().
                    anyMatch(obstacle -> obstacle.getSpellList().
                            removeIf(spell -> checkCollision(spell.getXPos(), spell.getYPos(), 32 / 2, 32 / 2, player.getXPos(), player.getYPos(), 36 / 2, 64 / 2)));
        }
        return false;
    }

    private boolean checkCollision(int r1x, int r1y,
                                   int r1w, int r1h,
                                   int r2x, int r2y,
                                   int r2w, int r2h) {
        return r1x + r1w >= r2x &&      // r1 right edge past r2 left
                r1x <= r2x + r2w &&     // r1 left edge past r2 right
                r1y + r1h >= r2y &&     // r1 top edge past r2 bottom
                r1y <= r2y + r2h;       // r1 bottom edge past r2 top
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
