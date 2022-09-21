/**
 * @author tomas
 */


import controller.GameEngine;
import model.Map;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * Reads map configuration json file,
 * initializes GameEngine.java class
 **/
public class Startup extends PApplet {
    private static PApplet pApplet;
    private GameEngine engine;
    private int screenWidth;
    private int screenHeight;
    private int level;
    private int stageCount;
    private int tiles;
    private int[][][] map;
    private PImage[] spriteSheet;

    public Startup() {
        pApplet = this;
    }

    public static void main(String[] args) {
        PApplet.main("Startup");
    }

    @Override
    public void settings() {
        config();
        size(screenWidth, screenHeight);
    }

    private void config() {
        spriteSheet = new PImage[]{
                loadImage("stoneTiles.png"), /* floor */
                loadImage("wall.png"),      /* wall */
                loadImage("dragon.png"),    /* enemy list */
                loadImage("door.png"),      /* exit */
                loadImage("player.png"),    /* player */
                loadImage("fireball.png"),  /* enemy spell */
                loadImage("healthBar.png"), /* health bar */
                loadImage("sky.png")        /* sky */
        };

        JSONObject json = loadJSONObject("data.json");
        JSONObject mapConfig = json.getJSONObject("mapConfig");
        JSONArray matrix = json.getJSONArray("matrix");
        JSONArray column = matrix.getJSONArray(0);
        JSONArray row = column.getJSONArray(0);

        map = new int[matrix.size()][column.getJSONArray(0).size()][row.size()];

        /* read matrix */
        for (int s = 0; s < matrix.size(); s++) {
            column = matrix.getJSONArray(s);
            for (int r = 0; r < column.size(); r++) {
                row = column.getJSONArray(r);
                for (int c = 0; c < row.size(); c++) {
                    map[s][r][c] = row.getInt(c);
                }
            }
        }

        screenWidth = mapConfig.getInt("SCREEN_WIDTH");
        screenHeight = mapConfig.getInt("SCREEN_HEIGHT");
        tiles = mapConfig.getInt("TILES");
        stageCount = matrix.size();
    }

    public void initGame() {
        engine = new GameEngine(spriteSheet, map[level], tiles);
    }

    @Override
    public void setup() {
        frameRate(60);
        initGame();
    }

    @Override
    public void draw() {
        Map engineMap = engine.getMapBuilder().getMap();
        if (engineMap.isNextStage()) {
            level = level < stageCount - 1 ? level + 1 : 0;
            initGame();
        } else {
            engine.render(pApplet);
            if (engineMap.isGameOver()) {
                level = 0;
                initGame();
            }
        }

    }

    @Override
    public void keyPressed() {
        if (engine.getController().isAllowedKey(key)) {
            engine.getController().getKeys()[key] = true;
        }
    }

    @Override
    public void keyReleased() {
        if (engine.getController().isAllowedKey(key)) {
            engine.getController().getKeys()[key] = false;
        }
    }
}