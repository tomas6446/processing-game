package model;

import lombok.Getter;
import lombok.Setter;
import model.element.Enemy;
import model.element.Obstacle;
import model.element.Player;
import model.element.StaticObject;
import model.element.Texture;
import model.type.ObjectType;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Map {
    private final List<StaticObject> staticObjects = new ArrayList<>();
    private final HealthBar healthBar;
    private final StaticObject sky;
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private boolean nextStage = false;
    private boolean gameOver = false;
    private int tileSize;
    private int[][] grid;

    public Map(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.sky = new StaticObject(new Texture(spriteSheet[7], 0, 0, 1920, 1200), -grid[0].length * tileSize / 2, 0, 1920, 1200, false, ObjectType.SKY);
        this.healthBar = new HealthBar(spriteSheet[6], 128, 128);

        this.tileSize = tileSize;
        this.grid = grid;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * tileSize;
                int y = i * tileSize;

                staticObjects.add(new StaticObject(new Texture(spriteSheet[0], 0, 0, tileSize, tileSize), x, y, 64, 64, false, ObjectType.FLOOR)); /* floor */
                if (grid[i][j] != 0) {
                    switch (grid[i][j]) {
                        case 1 ->
                                staticObjects.add(new StaticObject(new Texture(spriteSheet[1], 0, 0, 128, 128), x, y, 64, 64, true, ObjectType.WALL)); /* wall */
                        case 2 -> {
                            obstacles.add(new Obstacle(new Texture(spriteSheet[5], 64, 64, 128, 128), x + 16, y + 16, 32, 32)); /* enemy attacks */
                            enemies.add(new Enemy(new Texture(spriteSheet[2], 0, 0, 118, 118), x, y, 64, 64)); /* enemy */
                        }
                        case 3 ->
                                staticObjects.add(new StaticObject(new Texture(spriteSheet[3], 0, 0, 64, 64), x, y, 64, 64, true, ObjectType.EXIT)); /* exit */
                        case 4 -> player = new Player(spriteSheet[4], 14, 4, 36, 60, x, y, 9, 4); /* player */
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
    }
}
