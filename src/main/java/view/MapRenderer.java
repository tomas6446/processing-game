package view;

import model.Map;
import model.MapBuilder;
import model.element.Player;
import model.element.Texture;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author tomas
 */
public class MapRenderer implements Renderer {
    private static final int SPAWN_DELTA = 10;
    private MapBuilder mapBuilder;
    private final int obstacleSpawnCount = 2;
    private int lastObstacleSpawn;

    public MapRenderer(MapBuilder mapBuilder) {
        this.mapBuilder = mapBuilder;
    }

    @Override
    public void render(PApplet pApplet) {
        pApplet.clear();
        /* render background */
        renderSky(pApplet);
        /* render builder */
        renderBuilder(pApplet);
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

    private void renderBuilder(PApplet pApplet) {
        for (int i = 0; i < 3; i++) {
            Texture texture = mapBuilder.getMap().getTextures().get(i);
            pApplet.image(texture.getSprite(), i * 64, 50, 32, 32);
        }
    }

    private void renderEnemy(PApplet pApplet) {
        mapBuilder.getMap().getEnemies()
                .forEach(enemy -> renderImage(pApplet, enemy.getTexture().getSprite(), enemy.getXPos(), enemy.getYPos(), enemy.getWidth(), enemy.getHeight()));
    }

    private void renderHealthBar(PApplet pApplet) {
        renderImage(pApplet, mapBuilder.getMap().getHealthBar().getSprite()[mapBuilder.getMap().getHealthBar().getHealthCount()], 0, 0, mapBuilder.getMap().getHealthBar().getWidth(), mapBuilder.getMap().getHealthBar().getHeight());
    }

    private void renderSpell(PApplet pApplet) {
        mapBuilder.getMap().getObstacles()
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
        Player player = mapBuilder.getMap().getPlayer();
        PImage[][] playerSprite = player.getPlayerSprite();
        int currentDirection = player.getCurrentDirection();
        if (player.isInMotion()) {
            renderImage(pApplet, playerSprite[currentDirection][1 + (int) player.getCurrentFrame()], player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight());
        } else {
            renderImage(pApplet, playerSprite[currentDirection][0], player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight());
        }
    }

    private void renderObjects(PApplet pApplet) {
        mapBuilder.getMap().getStaticObjects()
                .forEach(obj -> renderImage(pApplet, obj.getTexture().getSprite(), obj.getXPos(), obj.getYPos(), obj.getWidth(), obj.getHeight()));
    }

    private void renderSky(PApplet pApplet) {
        pApplet.fill(135, 206, 235); // blue rgb
        pApplet.rect(-2000, -2000, 4000, 4000);

        renderImage(pApplet, mapBuilder.getMap().getSky().getTexture().getSprite(), mapBuilder.getMap().getSky().getXPos(), mapBuilder.getMap().getSky().getYPos(), mapBuilder.getMap().getSky().getWidth(), mapBuilder.getMap().getSky().getHeight());
    }

    private void renderImage(PApplet pApplet, PImage obj, int xPos, int yPos, int width, int height) {
        pApplet.image(obj, xPos, yPos, width, height);
    }

}
