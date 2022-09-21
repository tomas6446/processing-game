package controller;

import lombok.Getter;
import lombok.Setter;
import model.HealthBar;
import model.Map;
import model.element.Enemy;
import model.element.Obstacle;
import model.element.Player;
import model.element.Spell;
import model.element.StaticObject;
import model.type.ObjectType;

import java.util.List;

/**
 * @author tomas
 * The controller updates model's state,
 * handles collision between objects
 * updates positions,
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

    public boolean isAllowedKey(int key) {
        return key == 'a' || key == 'w' || key == 's' || key == 'd';
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
        if (!a.isAssignableFrom(Player.class) || !b.isAssignableFrom(StaticObject.class)) {
            if (a.isAssignableFrom(Spell.class) && b.isAssignableFrom(StaticObject.class)) {
                List<StaticObject> staticObjects = map.getStaticObjects();
                return staticObjects.stream().filter(StaticObject::isCollidable)
                        .anyMatch(staticObject -> map.getObstacles().stream()
                                .anyMatch(obstacle -> obstacle.getSpellList()
                                        .removeIf(spell -> checkCollision(spell.getXPos(), spell.getYPos(), spell.getWidth(), spell.getHeight(), staticObject.getXPos(), staticObject.getYPos(), staticObject.getWidth(), staticObject.getHeight() / 2))));
            } else if (a.isAssignableFrom(Player.class) && b.isAssignableFrom(Enemy.class)) {
                Player player = map.getPlayer();
                return map.getEnemies().stream()
                        .anyMatch(enemy -> checkCollision(enemy.getXPos(), enemy.getYPos(), enemy.getWidth(), enemy.getHeight() / 2, player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight()));
            } else if (a.isAssignableFrom(Spell.class) && b.isAssignableFrom(Player.class)) {
                Player player = map.getPlayer();
                return map.getObstacles().stream()
                        .anyMatch(obstacle -> obstacle.getSpellList()
                                .removeIf(spell -> checkCollision(spell.getXPos(), spell.getYPos(), spell.getWidth() / 2, spell.getHeight() / 2, player.getXPos(), player.getYPos(), player.getWidth() / 2, player.getHeight() / 2)));
            }
        } else {
            List<StaticObject> staticObjects = map.getStaticObjects();
            for (StaticObject staticObject : staticObjects) {
                if (staticObject.isCollidable()) {
                    Player player = map.getPlayer();
                    if (checkCollision(player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight(), staticObject.getXPos(), staticObject.getYPos(), map.getTileSize(), map.getTileSize() / 2)) {
                        if (staticObject.getObjectType() == ObjectType.EXIT) {
                            map.setNextStage(true);
                        }
                        return true;
                    }
                }
            }
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


    public void center() {
        /* TODO center map to make player at the center */
    }
}
