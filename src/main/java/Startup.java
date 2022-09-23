/**
 * @author tomas
 */


import controller.GameEngine;
import model.Map;
import model.element.Texture;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private List<Texture> textures = new ArrayList<>();


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

        PImage[] sprite = new PImage[]{
                loadImage("tile.png"),      /* tiles */
                loadImage("stoneTiles.png"), /* floor */
                loadImage("wall.png"),      /* wall */
                loadImage("dragon.png"),    /* enemy */
                loadImage("door.png"),      /* exit */
                loadImage("player.png"),    /* player */
                loadImage("fireball.png"),  /* spell */
                loadImage("healthBar.png"), /* health bar */
                loadImage("sky.png")        /* sky */};

        textures = new ArrayList<>() {{
            add(new Texture(sprite[0], 0, 0, 512, 512, true));/* tiles */
            add(new Texture(sprite[1], 0, 0, tiles, tiles, true));/* floor */
            add(new Texture(sprite[2], 0, 0, 128, 128, true));  /* wall */
            add(new Texture(sprite[3], 0, 0, 118, 118, true)); /* enemy */
            add(new Texture(sprite[4], 0, 0, 64, 96, true)); /* exit */
            add(new Texture(sprite[5], 14, 4, 36, 60, false)); /* player */
            add(new Texture(sprite[6], 64, 64, 128, 128, true)); /* spell */
            add(new Texture(sprite[7], 0, 0, 70, 33, false)); /* health bar */
            add(new Texture(sprite[8], 0, 0, 1920, 1200, true)); /* sky */
        }};
    }

    public void initGame() {
        engine = new GameEngine(textures, map[level], tiles);
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

    @Override
    public void mouseReleased() {
        engine.getController().setMouseClicked(true);
        engine.getController().setMouseX(mouseX);
        engine.getController().setMouseY(mouseY);
    }

    @Override
    public void mousePressed() {
        engine.getController().setMouseClicked(false);
    }
}