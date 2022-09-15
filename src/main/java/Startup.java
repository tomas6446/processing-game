/**
 * @author tomas
 */


import Controller.GameEngine;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Startup extends PApplet {
    private GameEngine engine;
    private static PApplet p;

    public static int TILES;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private int STAGE = 0;

    public static void main(String[] args) {
        PApplet.main("Startup");
    }

    public Startup() {
        p = this;
    }

    public void settings() {
        config();
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void config() {
        PImage[] spriteSheet = {
                loadImage("stoneTiles.png"), /* floor */
                loadImage("wall.png"),      /* wall */
                loadImage("dragon.png"),    /* enemy list */
                loadImage("exit.png"),      /* exit */
                loadImage("player.png"),    /* player */
                loadImage("fireball.png"),  /* enemy spell */
                loadImage("healthBar.png"), /* health bar */
        };

        JSONObject json = loadJSONObject("data.json");

        /* map config variables */
        JSONObject mapConfig = json.getJSONObject("mapConfig");

        SCREEN_WIDTH = mapConfig.getInt("SCREEN_WIDTH");
        SCREEN_HEIGHT = mapConfig.getInt("SCREEN_HEIGHT");
        int TILES = mapConfig.getInt("TILES");

        /* matrix size */
        JSONArray matrix = json.getJSONArray("matrix");
        JSONArray stage = matrix.getJSONArray(0);
        JSONArray row = stage.getJSONArray(0);

        int[][][] map = new int[matrix.size()][stage.getJSONArray(0).size()][row.size()];

        /* read matrix */
        for (int s = 0; s < matrix.size(); s++) {
            stage = matrix.getJSONArray(s);
            for (int r = 0; r < stage.getJSONArray(0).size(); r++) {
                row = stage.getJSONArray(r);
                for (int c = 0; c < row.size(); c++) {
                    map[s][r][c] = row.getInt(c);
                }
            }
        }

        engine = new GameEngine(spriteSheet, map[STAGE], TILES);
    }

    public void setup() {
        frameRate(60);
    }

    public void draw() {
        clear();
        if (!engine.getMap().isNextStage()) {
            engine.render(p);
            engine.handleEvent();
        } else {
            STAGE++;
            config();
        }

    }

    public void keyPressed() {
        engine.getController().getKeys()[key] = true;
    }

    public void keyReleased() {
        engine.getController().getKeys()[key] = false;
    }
}