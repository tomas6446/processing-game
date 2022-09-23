package view;

import model.Map;
import model.element.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author tomas
 * Gets notified about the changes and render the map's current state
 */
public class MapRenderer implements Renderer {
    private static final int SPAWN_DELTA = 5000;
    private final Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    @Override
    public void render(PApplet pApplet) {
        pApplet.clear();
        /* render background */
        renderSky(pApplet);
        /* render panel */
        renderPanel(pApplet);
        /* render objects */
        renderObjects(pApplet);

        if (map.getEnemies() != null) {
            /* render enemy */
            renderEnemy(pApplet);
            /* render spell */
            renderSpell(pApplet);
        }

        if (map.getPlayer() != null) {
            /* render player */
            renderPlayer(pApplet);
            /* render health bar */
            renderHealthBar(pApplet);
        }

    }

    private void renderPanel(PApplet pApplet) {
        map.getPanel()
                .forEach(panel -> renderImage(pApplet, panel.getTexture().sprite(), panel.getXPos(), panel.getYPos(), panel.getWidth(), panel.getHeight()));
    }

    private void renderEnemy(PApplet pApplet) {
        map.getEnemies()
                .forEach(enemy -> renderImage(pApplet, enemy.getTexture().sprite(), enemy.getXPos(), enemy.getYPos(), enemy.getWidth(), enemy.getHeight()));
    }

    private void renderHealthBar(PApplet pApplet) {
        renderImage(pApplet, map.getHealthBar().getSprite()[map.getHealthBar().getHealthCount()], 0, 0, map.getHealthBar().getWidth(), map.getHealthBar().getHeight());
    }

    private void renderSpell(PApplet pApplet) {
        map.getWaves()
                .forEach(obstacle -> {
                    if (pApplet.millis() - obstacle.getLastObstacleSpawn() > SPAWN_DELTA) {
                        obstacle.spawnObstacle();
                        obstacle.setLastObstacleSpawn(pApplet.millis());
                    }
                    obstacle.getSpellList().forEach(spell -> renderImage(pApplet, spell.getTexture().sprite(), spell.getXPos(), spell.getYPos(), spell.getWidth(), spell.getHeight()));
                });
    }

    private void renderPlayer(PApplet pApplet) {
        Player player = map.getPlayer();
        PImage[][] playerSprite = player.getPlayerSprite();
        int currentDirection = player.getCurrentDirection();
        if (player.isInMotion()) {
            renderImage(pApplet, playerSprite[currentDirection][1 + (int) player.getCurrentFrame()], player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight());
        } else {
            renderImage(pApplet, playerSprite[currentDirection][0], player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight());
        }
    }

    private void renderObjects(PApplet pApplet) {
        map.getStaticObjects()
                .forEach(obj -> renderImage(pApplet, obj.getTexture().sprite(), obj.getXPos(), obj.getYPos(), obj.getWidth(), obj.getHeight()));
    }

    private void renderSky(PApplet pApplet) {
        pApplet.fill(135, 206, 235); // blue rgb
        pApplet.rect(-2000, -2000, 4000, 4000);

        renderImage(pApplet, map.getSky().getTexture().sprite(), map.getSky().getXPos(), map.getSky().getYPos(), map.getSky().getWidth(), map.getSky().getHeight());
    }

    private void renderImage(PApplet pApplet, PImage obj, int xPos, int yPos, int width, int height) {
        pApplet.image(obj, xPos, yPos, width, height);
    }

}
