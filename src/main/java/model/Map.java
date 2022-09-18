package model;

import lombok.Getter;
import lombok.Setter;
import model.element.Enemy;
import model.type.ObjectType;
import model.element.Obstacle;
import model.element.Player;
import model.element.StaticObject;
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
    private final List<StaticObject> staticObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private final HealthBar healthBar;
    private Player player;
    private boolean nextStage = false;
    private boolean gameOver = false;
    private int tileSize;
    private int[][] grid;

    public Map(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.tileSize = tileSize;
        this.grid = grid;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * tileSize;
                int y = i * tileSize;

                staticObjects.add(new StaticObject(spriteSheet[0], x, y, tileSize, tileSize, false, ObjectType.FLOOR)); /* floor */
                if (grid[i][j] != 0) {
                    switch (grid[i][j]) {
                        case 1 -> staticObjects.add(new StaticObject(spriteSheet[1], x, y, 128, 128, true, ObjectType.WALL)); /* wall */
                        case 2 -> {
                            obstacles.add(new Obstacle(spriteSheet[5], x, y)); /* enemy attacks */
                            enemies.add(new Enemy(spriteSheet[2], x, y)); /* enemy */
                        }
                        case 3 -> staticObjects.add(new StaticObject(spriteSheet[3], x, y, 64, 64, true, ObjectType.EXIT)); /* exit */
                        case 4 -> player = new Player(spriteSheet[4], x, y, 9, 4); /* player */
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
        healthBar = new HealthBar(spriteSheet[6]);
    }
}
