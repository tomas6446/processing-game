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
    private final List<MapObject> objects = new ArrayList<>();
    private final HealthBar healthBar;
    private Player player;

    private boolean nextStage = false;
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
                        case 1 -> objects.add(new Object(spriteSheet[1], x, y, 128, 128, true, 1));
                        case 2 -> {
                            objects.add(new Object(spriteSheet[2], x, y, 118, 118, true, 2));
                            //objects.add(new Object(spriteSheet[3], x, y, 64, 64, true, 3));
                        }
                        case 3 -> objects.add(new Object(spriteSheet[3], x, y, 64, 64, true, 3));
                        case 4 -> player = new Player(spriteSheet[4], x, y, 9, 4);
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
        healthBar = new HealthBar(spriteSheet[6]);
    }
}
