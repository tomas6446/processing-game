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
    private final List<Texture> textures;
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

    private static int PLAYER_WIDTH = 18;
    private static int PLAYER_HEIGHT = 30;
    private static int ENEMY_WIDTH = 32;
    private static int ENEMY_HEIGHT = 32;
    private static int FLOOR_WIDTH = 32;
    private static int FLOOR_HEIGHT = 32;
    private static int WALL_WIDTH = 32;
    private static int WALL_HEIGHT = 32;
    private static int OBSTACLE_WIDTH = 16;
    private static int OBSTACLE_HEIGHT = 16;
    private static int EXIT_WIDTH = 64;
    private static int EXIT_HEIGHT = 64;
    private static int BAR_WIDTH = 64;
    private static int BAR_HEIGHT = 64;
    private static int BACKGROUND_WIDTH = 1920;
    private static int BACKGROUND_HEIGHT = 1200;


    public Map(PImage[] spriteSheet, int[][] grid, int tileSize) {

        this.sky = new StaticObject(new Texture(spriteSheet[7], 0, 0, 1920, 1200), -grid[0].length * tileSize / 2, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, false, ObjectType.SKY);
        this.healthBar = new HealthBar(spriteSheet[6], BAR_WIDTH, BAR_HEIGHT);

        this.tileSize = tileSize;
        this.grid = grid;
        int offsetX = 100;
        int offsetY = 100;

        this.textures = new ArrayList<>() {{
            add(new Texture(spriteSheet[0], 0, 0, tileSize, tileSize));/* floor texture */
            add(new Texture(spriteSheet[1], 0, 0, 128, 128));  /* wall texture */
            add(new Texture(spriteSheet[2], 0, 0, 118, 118)); /* enemy texture */
            add(new Texture(spriteSheet[3], 0, 0, 128, 128)); /* exit texture */
            add(new Texture(spriteSheet[4], 14, 4, 36, 60)); /* player texture */
            add(new Texture(spriteSheet[5], 64, 64, 128, 128)); /* spell texture */
        }};


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * tileSize + offsetX;
                int y = i * tileSize + offsetY;

                staticObjects.add(new StaticObject(textures.get(0), x, y, FLOOR_WIDTH, FLOOR_HEIGHT, false, ObjectType.FLOOR)); /* floor */
                if (grid[i][j] != 0) {
                    switch (grid[i][j]) {
                        case 1 ->
                                staticObjects.add(new StaticObject(textures.get(1), x, y, WALL_WIDTH, WALL_HEIGHT, true, ObjectType.WALL)); /* wall */
                        case 2 -> {
                            obstacles.add(new Obstacle(textures.get(5), x, y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT)); /* enemy attacks */
                            enemies.add(new Enemy(textures.get(2), x, y, ENEMY_WIDTH, ENEMY_HEIGHT)); /* enemy */
                        }
                        case 3 ->
                                staticObjects.add(new StaticObject(textures.get(3), x, y, EXIT_WIDTH, EXIT_HEIGHT, true, ObjectType.EXIT)); /* exit */
                        case 4 -> player = new Player(spriteSheet[4], 14, 4, 36, 60, PLAYER_WIDTH, PLAYER_HEIGHT, x, y, 9, 4); /* player */
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
    }
}
