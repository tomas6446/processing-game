package view;

import model.Map;
import model.element.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author tomas
 */
public class MapRenderer implements Renderer {
    private static final int SPAWN_DELTA = 15;
    private final Map map;
    private int lastObstacleSpawn;
    private final int obstacleSpawnCount = 10;

    public MapRenderer(Map map) {
        this.map = map;
    }

    @Override
    public void render(PApplet pApplet) {
        pApplet.clear();
        /* render background */
        pApplet.fill(135, 206, 235); // blue rgb
        pApplet.rect(-2000, -2000, 4000, 4000);

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
                .forEach(enemy -> renderImage(pApplet, enemy.getSprite(), enemy.getXPos(), enemy.getYPos(), 64, 64));
    }

    private void renderHealthBar(PApplet pApplet) {
        renderImage(pApplet, map.getHealthBar().getSprite()[map.getHealthBar().getHealthCount()], 0, 0, map.getTileSize() * 2, map.getTileSize() * 2);
    }

    private void renderSpell(PApplet pApplet) {
        map.getObstacles()
                .forEach(obstacle -> {
                    if (obstacle.getSpellList().size() < obstacleSpawnCount && pApplet.millis() - lastObstacleSpawn > SPAWN_DELTA) {
                        obstacle.spawnObstacle();
                        lastObstacleSpawn = pApplet.millis();
                    }
                    obstacle.getSpellList()
                            .forEach(spell -> renderImage(pApplet, spell.getSprite(), spell.getXPos(), spell.getYPos(), 32, 32));
                });
    }

    private void renderPlayer(PApplet pApplet) {
        Player player = map.getPlayer();
        PImage[][] playerSprite = player.getPlayerSprite();
        int currentDirection = player.getCurrentDirection();
        if (player.isInMotion()) {
            renderImage(pApplet, playerSprite[currentDirection][1 + (int) player.getCurrentFrame()], player.getXPos(), player.getYPos(), 36, 64);
        } else {
            renderImage(pApplet, playerSprite[currentDirection][0], player.getXPos(), player.getYPos(), 36, 64);
        }
    }

    private void renderObjects(PApplet pApplet) {
        map.getStaticObjects()
                .forEach(obj -> renderImage(pApplet, obj.getSprite(), obj.getXPos(), obj.getYPos(), map.getTileSize(), map.getTileSize()));
    }

    private void renderImage(PApplet pApplet, PImage obj, int xPos, int yPos, int width, int height) {
        pApplet.image(obj, xPos, yPos, width, height);
    }

}
