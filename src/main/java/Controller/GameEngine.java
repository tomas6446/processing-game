package Controller;

import Model.Enemy;
import Model.GameElement;
import Model.Map;
import Model.MapObject;
import Model.Object;
import Model.Obstacle;
import Model.Player;
import Model.Spell;
import View.MapRenderer;
import View.Renderer;
import lombok.Getter;
import processing.core.PApplet;
import processing.core.PImage;

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
        isCollision(new Spell(), new Object());
        if (isCollision(new Spell(), new Player())) {
            map.getHealthBar().removeHealth();
            if (map.getHealthBar().getHealthCount() == 0) {
                map.setGameOver(true);
            }
        }
        if (isCollision(new Player(), new Object()) || isCollision(new Player(), new Enemy())) {
            controller.update(-1);
        }
    }

    public boolean isCollision(GameElement a, GameElement b) {
        /* Spell / Player collision with wall */
        if (b instanceof Object) {
            for (MapObject obj : map.getObjects()) {
                if (obj.isCollidable()) {
                    if (a instanceof Player) {
                        Player player = map.getPlayer();
                        if (checkCollision(player.getXPos(),
                                player.getYPos(),
                                36,
                                64,
                                obj.getXPos(),
                                obj.getYPos(),
                                map.getTileSize(),
                                map.getTileSize() - 48)) {
                            if(obj.getId() == 3) {
                                map.setNextStage(true);
                            }
                            return true;
                        }
                    } else if (a instanceof Spell) {
                        for (Obstacle obstacle : map.getObstacles()) {
                            List<Spell> spellList = obstacle.getSpellList();
                            for (int i = 0; i < spellList.size(); i++) {
                                if (checkCollision(spellList.get(i).getXPos(),
                                        spellList.get(i).getYPos(),
                                        32,
                                        32,
                                        obj.getXPos(),
                                        obj.getYPos(),
                                        map.getTileSize(),
                                        map.getTileSize())) {
                                    spellList.remove(i);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } else if (a instanceof Player && b instanceof Enemy) {
            Player player = map.getPlayer();
            for (Enemy enemy : map.getEnemies()) {
                if (checkCollision(
                        enemy.getXPos(),
                        enemy.getYPos(),
                        64,
                        64,
                        player.getXPos(),
                        player.getYPos(),
                        36,
                        64)) {
                    return true;
                }
            }
        } else {
            if (a instanceof Spell && b instanceof Player) {
                Player player = map.getPlayer();
                for (Obstacle obstacle : map.getObstacles()) {
                    List<Spell> spellList = obstacle.getSpellList();
                    for (int i = 0; i < spellList.size(); i++) {
                        if (checkCollision(spellList.get(i).getXPos(),
                                spellList.get(i).getYPos(),
                                32,
                                32 / 2,
                                player.getXPos(),
                                player.getYPos(),
                                36 / 2,
                                64)) {
                            obstacle.getSpellList().remove(i);
                            return true;
                        }
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

    public void render(PApplet p) {
        renderer.render(p);
    }
}
