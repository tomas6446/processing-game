package model;

import lombok.Getter;
import lombok.Setter;
import model.element.*;
import model.type.ObjectType;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tomas Kozakas, 1 grupe
 * Contains, groups, initializes the models together
 */
@Getter
@Setter
public class Map {
    /* param of map objects */
    public static final int SPEED = 4;
    private static final int PLAYER_WIDTH = 36;
    private static final int PLAYER_HEIGHT = 60;
    private static final int ENEMY_WIDTH = 64;
    private static final int ENEMY_HEIGHT = 64;
    private static final int FLOOR_WIDTH = 64;
    private static final int FLOOR_HEIGHT = 64;
    private static final int WALL_WIDTH = 64;
    private static final int WALL_HEIGHT = 64;
    private static final int OBSTACLE_WIDTH = 32;
    private static final int OBSTACLE_HEIGHT = 32;
    private static final int OBSTACLE_SPEED = 2;
    private static final int EXIT_WIDTH = 64;
    private static final int EXIT_HEIGHT = 64;
    private static final int BAR_WIDTH = 64;
    private static final int BAR_HEIGHT = 64;
    private static final int BACKGROUND_WIDTH = 1920;
    private static final int BACKGROUND_HEIGHT = 1200;
    private final List<StaticObject> panel = new ArrayList<>();/* panel to modify map */
    /* next stage for the game (add new matrix in the data.json file) */
    private boolean nextStage = false;
    /* to identify if the game is over */
    private boolean gameOver = false;
    /* the size of the block in the matrix */
    private int tileSize;
    /* map shift variables */
    private int offsetX = 100;
    private int offsetY = 100;
    private int[][] grid;
    private List<Texture> textures;                                 /* every texture is stored in a texture list */
    private List<Wave> waves = new ArrayList<>();                   /* waves of enemy attacks */
    private List<Enemy> enemies = new ArrayList<>();                /* enemy list */
    private List<StaticObject> staticObjects = new ArrayList<>();   /* static object list */
    private Player player;
    private HealthBar healthBar;
    private StaticObject sky;

    public Map(List<Texture> textures, int[][] grid, int tileSize) {
        this.staticObjects = Arrays.asList(new StaticObject[grid.length * grid[0].length]);
        this.textures = textures;
        this.tileSize = tileSize;
        this.grid = grid;

        initPanel();
        initMap();
    }

    public void initPanel() {
        PImage playerSprite = textures.get(5).getSprite().get(14, 4, 36, 60);
        Texture playerTexture = new Texture(playerSprite, 14, 4, 35, 60);
        panel.add(new StaticObject(playerTexture, offsetX, 16, getTileSize(), getTileSize(), false, ObjectType.PLAYER));
        panel.add(new StaticObject(textures.get(3), offsetX * 2, 16, getTileSize(), getTileSize(), false, ObjectType.ENEMY));
        panel.add(new StaticObject(textures.get(1), offsetX * 3, 16, getTileSize(), getTileSize(), false, ObjectType.FLOOR));
        panel.add(new StaticObject(textures.get(2), offsetX * 4, 16, getTileSize(), getTileSize(), false, ObjectType.WALL));
        panel.add(new StaticObject(textures.get(4), offsetX * 5, 16, getTileSize(), getTileSize(), false, ObjectType.EXIT));
        panel.add(new StaticObject(textures.get(0), offsetX * 6, 16, getTileSize(), getTileSize(), false, ObjectType.TILE));
    }

    /* the function goes through every matrix element to add object to the map.
     * matrix is configured in data.json file */
    public void initMap() {
        addObject(ObjectType.SKY, -grid[0].length * tileSize / 2, 0, 0, 0);
        addObject(ObjectType.HEALTH, 0, 0, 0, 0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * tileSize + offsetX;
                int y = i * tileSize + offsetY;
                addObject(ObjectType.TILE, x, y, i, j);
                if (grid[i][j] != 0) {
                    switch (grid[i][j]) {
                        case 1 -> addObject(ObjectType.FLOOR, x, y, i, j);
                        case 2 -> addObject(ObjectType.WALL, x, y, i, j);
                        case 4 -> addObject(ObjectType.EXIT, x, y, i, j);
                        case 5 -> addObject(ObjectType.PLAYER, x, y, i, j);
                        case 3 -> addObject(ObjectType.ENEMY, x, y, i, j);

                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
    }

    /* function to add or replace object on the map */
    public void addObject(ObjectType type, int x, int y, int i, int j) {
        int index = i + (j * grid[0].length);
        switch (type) {
            case WALL ->
                    staticObjects.set(index, new StaticObject(textures.get(2), x, y, WALL_WIDTH, WALL_HEIGHT, true, ObjectType.WALL)); /* wall */
            case TILE ->
                    staticObjects.set(index, new StaticObject(textures.get(0), x, y, tileSize, tileSize, true, ObjectType.TILE));
            case HEALTH ->
                    healthBar = new HealthBar(textures.get(7), BAR_WIDTH, BAR_HEIGHT);
            case FLOOR ->
                    staticObjects.set(index, new StaticObject(textures.get(1), x, y, FLOOR_WIDTH, FLOOR_HEIGHT, false, ObjectType.FLOOR)); /* floor */
            case SKY ->
                    sky = new StaticObject(textures.get(8), -grid[0].length * tileSize / 2, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, false, ObjectType.SKY);
            case EXIT ->
                    staticObjects.set(index, new StaticObject(textures.get(4), x, y, EXIT_WIDTH, EXIT_HEIGHT, true, ObjectType.EXIT)); /* exit */
            case PLAYER -> {
                staticObjects.set(index, new StaticObject(textures.get(1), x, y, FLOOR_WIDTH, FLOOR_HEIGHT, false, ObjectType.FLOOR)); /* floor */
                player = new Player(textures.get(5), x + tileSize / 2 - PLAYER_WIDTH / 2, y + tileSize / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, 9, 4, SPEED); /* player */
            }
            case ENEMY -> {
                staticObjects.set(index, new StaticObject(textures.get(1), x, y, FLOOR_WIDTH, FLOOR_HEIGHT, false, ObjectType.FLOOR)); /* floor */
                enemies.add(new Enemy(textures.get(3), x, y, ENEMY_WIDTH, ENEMY_HEIGHT)); /* enemy */
                waves.add(new Wave(textures.get(6), x + tileSize / 2 - OBSTACLE_WIDTH / 2, y + tileSize / 2 - OBSTACLE_HEIGHT / 2, OBSTACLE_WIDTH, OBSTACLE_HEIGHT, OBSTACLE_SPEED)); /* enemy attacks */
            }
            default -> throw new IllegalStateException("Unexpected type: " + type);
        }
    }
}
