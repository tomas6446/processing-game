package Controller;

import Model.Map;
import Model.MapObject;
import Model.Player;
import View.MapRenderer;
import View.Renderer;
import lombok.Getter;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author tomas
 */
@Getter
public class GameEngine {
    private final Map map;
    private final Renderer renderer;
    private final Controller controller;

    public GameEngine(PImage[] spriteSheet, int[][] grid) {
        this.map = new Map(spriteSheet, grid);
        this.renderer = new MapRenderer(map);
        this.controller = new Controller(map);
    }

    public void handleEvent() {
        map.getPlayer().updateDirection(controller.getXDelta(), controller.getYDelta());
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

        controller.update(1);
        if (handleCollision()) {
            controller.update(-1);
        }
    }

    public boolean handleCollision() {
        Player player = map.getPlayer();
        for (MapObject obj : map.getObjects()) {
            if (obj.isCollidable() &&
                    player.getXPos() + map.getWidth() - 16 > obj.getXPos() &&
                    player.getXPos() < obj.getXPos() + map.getWidth() - 16 &&
                    player.getYPos() + map.getHeight() > obj.getYPos() &&
                    player.getYPos() < obj.getYPos() + map.getHeight() / 2) {

                // TODO id enum
                switch (obj.getId()) {
                    case 3 -> map.setNextStage(true);
                    case 5 -> map.getPlayer().hit();
                }
                if (map.getPlayer().getHitPoints() == 0) {
                    map.setNextStage(true);
                }

                return true;
            }
        }
        return false;
    }

    public void render(PApplet p) {
        renderer.render(p);
    }
}
