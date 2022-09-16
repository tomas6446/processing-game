package View;

import Model.Enemy;
import Model.Map;
import Model.MapObject;
import Model.Obstacle;
import Model.Player;
import Model.Spell;
import processing.core.PApplet;

/**
 * @author tomas
 */
public class MapRenderer implements Renderer {
    private final Map map;

    private int lastObstacleSpawn;
    private final int spawnDeltaTime = 10;

    public MapRenderer(Map map) {
        this.map = map;
    }

    @Override
    public void render(PApplet p) {
        p.clear();
        /* render background */
        p.fill(135, 206, 235); // blue rgb
        p.rect(-2000, -2000, 4000, 4000);

        /* render objects */
        for (MapObject obj : map.getObjects()) {
            p.image(obj.getSprite(),
                    obj.getXPos(),
                    obj.getYPos(),
                    map.getTileSize(),
                    map.getTileSize()
            );
        }

        /* render spell */
        for (Obstacle obstacle: map.getObstacles()) {
            if (obstacle.getSpellList().size() < 10 &&
                    p.millis() - lastObstacleSpawn > spawnDeltaTime) {
                obstacle.spawnObstacle();
                lastObstacleSpawn = p.millis();
            }
            for (Spell spell : obstacle.getSpellList()) {
                p.image(spell.getSprite(),
                        spell.getXPos(),
                        spell.getYPos(),
                        map.getTileSize(),
                        map.getTileSize()
                );
            }
        }

        /* render enemy */
        for (Enemy enemy: map.getEnemies()) {
            p.image(enemy.getSprite(),
                    enemy.getXPos(),
                    enemy.getYPos(),
                    map.getTileSize(),
                    map.getTileSize()
            );
        }

        /* render player */
        Player player = map.getPlayer();
        if (player.isInMotion()) {
            p.image(player.getSprite()[player.getCurrentDirection()][1 + (int) player.getCurrentFrame()],
                    player.getXPos(),
                    player.getYPos(),
                    map.getTileSize(),
                    map.getTileSize()
            );
        } else {
            p.image(player.getSprite()[player.getCurrentDirection()][0],
                    player.getXPos(),
                    player.getYPos(),
                    map.getTileSize(),
                    map.getTileSize()
            );
        }

        /* render health bar */
        p.image(map.getHealthBar().getSprite()[map.getHealthBar().getHealthCount()],
                0,
                0,
                map.getTileSize() * 2,
                map.getTileSize() * 2
        );

    }
}
