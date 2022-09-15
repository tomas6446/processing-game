package Controller;

import Model.GameElement;
import Model.Map;
import Model.MapObject;
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

        map.getPlayer().updateDirection(controller.getXDelta(), controller.getYDelta());
        map.getSpells().forEach(Spell::move);

        controller.update(1);
        if (isCollision(new Player()) || isCollision(new Spell())) {
            controller.update(-1);
        }
    }

    public boolean isCollision(GameElement gameElement) {
        if (gameElement instanceof Player) {
            Player player = map.getPlayer();
            return checkCollision(player.getXPos(), player.getYPos());
        } else if (gameElement instanceof Spell) {
            List<Spell> spells = map.getSpells();
            for (int i = 0; i < spells.size(); i++) {
                if(checkCollision(spells.get(i).getXPos(), spells.get(i).getYPos())) {
                    spells.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkCollision(int xPos, int yPos) {
        for (MapObject obj : map.getObjects()) {
            if (obj.isCollidable() &&
                    xPos + map.getTileSize() - 16 > obj.getXPos() &&
                    xPos < obj.getXPos() + map.getTileSize() - 16 &&
                    yPos + map.getTileSize() > obj.getYPos() &&
                    yPos < obj.getYPos() + map.getTileSize() / 2) {
                return true;
            }
        }
        return false;
    }

    public void render(PApplet p) {
        renderer.render(p);
    }
}
