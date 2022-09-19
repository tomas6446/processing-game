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

    public static void main(String[] args) {
        PApplet.main("Startup");
    }

    public Startup() {
        pApplet = this;
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

        /* map config variables */
        JSONObject mapConfig = json.getJSONObject("mapConfig");

        screenWidth = mapConfig.getInt("SCREEN_WIDTH");
        screenHeight = mapConfig.getInt("SCREEN_HEIGHT");
        tiles = mapConfig.getInt("TILES");

        /* matrix size */
        JSONArray matrix = json.getJSONArray("matrix");
        JSONArray stage = matrix.getJSONArray(0);
        JSONArray row = stage.getJSONArray(0);

        map = new int[matrix.size()][stage.getJSONArray(0).size()][row.size()];

        stageCount = matrix.size();
        int rowCount = stage.getJSONArray(0).size();
        /* read matrix */
        for (int s = 0; s < matrix.size(); s++) {
            stage = matrix.getJSONArray(s);
            for (int r = 0; r < rowCount; r++) {
                row = stage.getJSONArray(r);
                for (int c = 0; c < row.size(); c++) {
                    map[s][r][c] = row.getInt(c);
                }
            }
        }
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
        Map engineMap = engine.getMap();
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
        engine.getController().getKeys()[key] = true;
    }

    @Override
    public void keyReleased() {
        engine.getController().getKeys()[key] = false;
    }
}