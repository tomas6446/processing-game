package controller;

import lombok.Getter;
import model.HealthBar;
import model.Map;
import model.element.Enemy;
import model.element.Player;
import model.element.Spell;
import model.element.StaticObject;
import model.type.ObjectType;
import processing.core.PApplet;
import processing.core.PImage;
import view.MapRenderer;
import view.Renderer;

import java.util.List;

/**
 * @author tomas
 */
@Getter
public class GameEngine {
    private final Map map;
    private final Renderer renderer;
    private final Controller controller;

    public GameEngine(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.map = new Map(spriteSheet, grid, tileSize);
        this.renderer = new MapRenderer(map);
        this.controller = new Controller(map);

        this.controller.center();
    }

    public void handleEvent() {
        controller.setXDelta(0);
        controller.setYDelta(0);

        if (controller.getKeys()['a']) {
            controller.setXDelta(controller.getXDelta() + map.getPlayer().getSpeed());
        }
        if (controller.getKeys()['w']) {
            controller.setYDelta(controller.getYDelta() + map.getPlayer().getSpeed());
        }
        if (controller.getKeys()['d']) {
            controller.setXDelta(controller.getXDelta() - map.getPlayer().getSpeed());
        }
        if (controller.getKeys()['s']) {
            controller.setYDelta(controller.getYDelta() - map.getPlayer().getSpeed());
        }

        update();
    }

    private void update() {
        controller.update(1);

        map.getPlayer().updateDirection(controller.getXDelta(), controller.getYDelta());
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
            controller.update(-1);
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

    public void render(PApplet pApplet) {
        renderer.render(pApplet);
    }
}
