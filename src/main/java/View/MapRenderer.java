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
        renderObjects(p);

        /* render player */
        renderPlayer(p);

        /* render spell */
        renderSpell(p);

        /* render enemy */
        renderEnemy(p);

        /* render health bar */
        renderHealthBar(p);
    }

    private void renderEnemy(PApplet p) {
        for (Enemy enemy: map.getEnemies()) {
            p.image(enemy.getSprite(),
                    enemy.getXPos(),
                    enemy.getYPos(),
                    64,
                    64
            );
        }
    }
    private void renderHealthBar(PApplet p) {
        p.image(map.getHealthBar().getSprite()[map.getHealthBar().getHealthCount()],
                0,
                0,
                map.getTileSize() * 2,
                map.getTileSize() * 2
        );
    }
    private void renderSpell(PApplet p) {
        for (Obstacle obstacle: map.getObstacles()) {
            if (obstacle.getSpellList().size() < 2 &&
                    p.millis() - lastObstacleSpawn > spawnDeltaTime) {
                obstacle.spawnObstacle();
                lastObstacleSpawn = p.millis();
            }
            for (Spell spell : obstacle.getSpellList()) {
                p.image(spell.getSprite(),
                        spell.getXPos(),
                        spell.getYPos(),
                        32,
                        32
                );
            }
        }
    }
    private void renderPlayer(PApplet p) {
        Player player = map.getPlayer();
        if (player.isInMotion()) {
            p.image(player.getSprite()[player.getCurrentDirection()][1 + (int) player.getCurrentFrame()],
                    player.getXPos(),
                    player.getYPos(),
                    36,
                    64
            );
        } else {
            p.image(player.getSprite()[player.getCurrentDirection()][0],
                    player.getXPos(),
                    player.getYPos(),
                    36,
                    64
            );
        }
    }
    private void renderObjects(PApplet p) {
        for (MapObject obj : map.getObjects()) {
            p.image(obj.getSprite(),
                    obj.getXPos(),
                    obj.getYPos(),
                    map.getTileSize(),
                    map.getTileSize()
            );
        }
    }

}
