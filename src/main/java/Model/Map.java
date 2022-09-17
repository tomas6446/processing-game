package Model;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Map {
    private List<Obstacle> obstacles = new ArrayList<>();
    private final List<Object> objects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private final HealthBar healthBar;
    private Player player;
    private boolean nextStage = false;
    private boolean gameOver = false;
    private int tileSize;

    public Map(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.tileSize = tileSize;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * tileSize;
                int y = i * tileSize;

                objects.add(new Object(spriteSheet[0], x, y, tileSize, tileSize, false, 0)); /* floor */
                if (grid[i][j] != 0) {
                    switch (grid[i][j]) {
                        case 1 -> objects.add(new Object(spriteSheet[1], x, y, 128, 128, true, 1)); /* wall */
                        case 2 -> {
                            obstacles.add(new Obstacle(spriteSheet[5], x, y)); /* enemy attacks */
                            enemies.add(new Enemy(spriteSheet[2], x, y)); /* enemy */
                        }
                        case 3 -> objects.add(new Object(spriteSheet[3], x, y, 64, 64, true, 3)); /* exit */
                        case 4 -> player = new Player(spriteSheet[4], x, y, 9, 4); /* player */
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
        healthBar = new HealthBar(spriteSheet[6]);
    }
}
