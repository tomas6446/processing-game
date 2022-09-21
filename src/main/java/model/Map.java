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
import java.util.Arrays;
import java.util.List;

/**
 * @author tomas
 */
@Getter
@Setter
public class Map {
    private PImage[] spriteSheet;
    private final List<Texture> textures;
    private final List<StaticObject> panel = new ArrayList<>();
    private List<StaticObject> staticObjects;
    private HealthBar healthBar;
    private StaticObject sky;
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private boolean nextStage = false;
    private boolean gameOver = false;
    private int tileSize;
    private int offsetX = 100;
    private int offsetY = 100;
    private int[][] grid;

    private static final int PLAYER_WIDTH = 36;
    private static final int PLAYER_HEIGHT = 60;
    private static final int ENEMY_WIDTH = 48;
    private static final int ENEMY_HEIGHT = 48;
    private static final int FLOOR_WIDTH = 48;
    private static final int FLOOR_HEIGHT = 48;
    private static final int WALL_WIDTH = 48;
    private static final int WALL_HEIGHT = 48;
    private static final int OBSTACLE_WIDTH = 32;
    private static final int OBSTACLE_HEIGHT = 32;
    private static final int EXIT_WIDTH = 48;
    private static final int EXIT_HEIGHT = 48;
    private static final int BAR_WIDTH = 48;
    private static final int BAR_HEIGHT = 48;
    private static final int BACKGROUND_WIDTH = 1920;
    private static final int BACKGROUND_HEIGHT = 1200;
    public static final int SPEED = 3;


    public Map(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.staticObjects = Arrays.asList(new StaticObject[grid.length * grid[0].length]);

        this.spriteSheet = spriteSheet;
        this.tileSize = tileSize;
        this.grid = grid;

        addObject(ObjectType.SKY, -grid[0].length * tileSize / 2, 0, 0, 0);
        addObject(ObjectType.HEALTH, 0, 0, 0, 0);
        this.textures = new ArrayList<>() {{
            add(new Texture(spriteSheet[0], 0, 0, 512, 512));/* tiles */
            add(new Texture(spriteSheet[1], 0, 0, tileSize, tileSize));/* floor */
            add(new Texture(spriteSheet[2], 0, 0, 128, 128));  /* wall */
            add(new Texture(spriteSheet[3], 0, 0, 118, 118)); /* enemy */
            add(new Texture(spriteSheet[4], 0, 0, 64, 96)); /* exit */
            add(new Texture(spriteSheet[5], 14, 4, 36, 60)); /* player */
            add(new Texture(spriteSheet[6], 64, 64, 128, 128)); /* spell */
        }};

        initPanel();
        initMap();
    }

    public void initPanel() {
        panel.add(new StaticObject(textures.get(5), offsetX, 16, getTileSize(), getTileSize(), false, ObjectType.PLAYER));
        panel.add(new StaticObject(textures.get(3), offsetX * 2, 16, getTileSize(), getTileSize(), false, ObjectType.ENEMY));
        panel.add(new StaticObject(textures.get(1), offsetX * 3, 16, getTileSize(), getTileSize(), false, ObjectType.FLOOR));
        panel.add(new StaticObject(textures.get(2), offsetX * 4, 16, getTileSize(), getTileSize(), false, ObjectType.WALL));
        panel.add(new StaticObject(textures.get(4), offsetX * 5, 16, getTileSize(), getTileSize(), false, ObjectType.EXIT));
        panel.add(new StaticObject(textures.get(0), offsetX * 6, 16, getTileSize(), getTileSize(), false, ObjectType.TILE));
    }

    public void initMap() {
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
                        case 3 -> {
                            addObject(ObjectType.OBSTACLE, x, y, i, j);
                            addObject(ObjectType.ENEMY, x, y, i, j);
                        }
                        default -> throw new IllegalStateException("Unexpected value in the matrix: " + grid[i][j]);
                    }
                }
            }
        }
    }

    public void addObject(ObjectType type, int x, int y, int i, int j) {
        int index = i + (j * grid[0].length);
        switch (type) {
            case WALL ->
                    staticObjects.set(index, new StaticObject(textures.get(2), x, y, WALL_WIDTH, WALL_HEIGHT, true, ObjectType.WALL)); /* wall */
            case TILE ->
                    staticObjects.set(index, new StaticObject(textures.get(0), x, y, tileSize, tileSize, false, ObjectType.TILE));
            case HEALTH -> healthBar = new HealthBar(spriteSheet[7], BAR_WIDTH, BAR_HEIGHT);
            case FLOOR ->
                    staticObjects.set(index, new StaticObject(textures.get(1), x, y, FLOOR_WIDTH, FLOOR_HEIGHT, false, ObjectType.FLOOR)); /* floor */
            case PLAYER ->
                    player = new Player(spriteSheet[5], 14, 4, 36, 60, PLAYER_WIDTH, PLAYER_HEIGHT, x, y, 9, 4, SPEED); /* player */
            case EXIT ->
                    staticObjects.set(index, new StaticObject(textures.get(4), x, y, EXIT_WIDTH, EXIT_HEIGHT, true, ObjectType.EXIT)); /* exit */
            case SKY ->
                    sky = new StaticObject(new Texture(spriteSheet[8], 0, 0, 1920, 1200), -grid[0].length * tileSize / 2, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, false, ObjectType.SKY);
            case ENEMY -> {
                enemies.add(new Enemy(textures.get(3), x, y, ENEMY_WIDTH, ENEMY_HEIGHT)); /* enemy */
                obstacles.add(new Obstacle(textures.get(6), x, y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT)); /* enemy attacks */
            }
            default -> throw new IllegalStateException("Unexpected type: " + type);
        }
    }
}
