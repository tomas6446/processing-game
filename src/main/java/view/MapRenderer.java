package view;

import model.Map;
import model.element.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author tomas
 */
public class MapRenderer implements Renderer {
    private static final int SPAWN_DELTA = 10;
    private final Map map;
    private int lastObstacleSpawn;
    private final int obstacleSpawnCount = 2;

    public MapRenderer(Map map) {
        this.map = map;
    }

    @Override
    public void render(PApplet pApplet) {
        pApplet.clear();
        /* render background */
        renderSky(pApplet);
        /* render objects */
        renderObjects(pApplet);
        /* render enemy */
        renderEnemy(pApplet);
        /* render player */
        renderPlayer(pApplet);
        /* render spell */
        renderSpell(pApplet);
        /* render health bar */
        renderHealthBar(pApplet);
    }

    private void renderEnemy(PApplet pApplet) {
        map.getEnemies()
                .forEach(enemy -> renderImage(pApplet, enemy.getTexture().getSprite(), enemy.getXPos(), enemy.getYPos(), enemy.getWidth(), enemy.getHeight()));
    }

    private void renderHealthBar(PApplet pApplet) {
        renderImage(pApplet, map.getHealthBar().getSprite()[map.getHealthBar().getHealthCount()], 0, 0, map.getHealthBar().getWidth(), map.getHealthBar().getHeight());
    }

    private void renderSpell(PApplet pApplet) {
        map.getObstacles()
                .forEach(obstacle -> {
                    if (obstacle.getSpellList().size() < obstacleSpawnCount && pApplet.millis() - lastObstacleSpawn > SPAWN_DELTA) {
                        obstacle.spawnObstacle();
                        lastObstacleSpawn = pApplet.millis();
                    }
                    obstacle.getSpellList()
                            .forEach(spell -> renderImage(pApplet, spell.getTexture().getSprite(), spell.getXPos(), spell.getYPos(), spell.getWidth(), spell.getHeight()));
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
                .forEach(obj -> renderImage(pApplet, obj.getTexture().getSprite(), obj.getXPos(), obj.getYPos(), obj.getWidth(), obj.getHeight()));
    }

    private void renderSky(PApplet pApplet) {
        renderImage(pApplet, map.getSky().getTexture().getSprite(), map.getSky().getXPos(), map.getSky().getYPos(), map.getSky().getWidth(), map.getSky().getHeight());
    }

    private void renderImage(PApplet pApplet, PImage obj, int xPos, int yPos, int width, int height) {
        pApplet.image(obj, xPos, yPos, width, height);
    }

}
