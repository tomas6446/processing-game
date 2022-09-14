/**
 * @author tomas
 */


import Controller.GameEngine;
import processing.core.PApplet;
import processing.core.PImage;

public class Startup extends PApplet {
    private GameEngine engine;
    private static PApplet p;

    private int STAGE = 0;

    private int[][][] map;
    private PImage[] spriteSheet;

    public static void main(String[] args) {
        PApplet.main("Startup");
    }

    public Startup() {
        p = this;
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        frameRate(60);

        // TODO read from json file
        PImage[] spriteSheet = {
                loadImage("stoneTiles.png"), /* floor */
                loadImage("wall.png"),      /* wall */
                loadImage("dragon.png"),    /* enemy list */
                loadImage("exit.png"),      /* exit */
                loadImage("player.png"),    /* player */
                loadImage("fireball.png"),  /* enemy spell */
                loadImage("healthBar.png"), /* health bar */
        };

        // TODO read from json file
        // map configuration
        // 0 - floor
        // 1 - wall
        // 2 - enemy
        // 3 - exit
        // 4 - player
        int[][][] map = {
                {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 4, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                        {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 3},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 1, 0, 3},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                },
                {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                },
        };
        engine = new GameEngine(spriteSheet, map[STAGE]);
    }

    public void draw() {
        clear();
        if(!engine.getMap().isNextStage()) {
            engine.render(p);
            engine.handleEvent();
        } else {
            STAGE++;
            engine = new GameEngine(spriteSheet, map[STAGE]);
        }

    }

    public void keyPressed() {
        engine.getController().getKeys()[key] = true;
    }

    public void keyReleased() {
        engine.getController().getKeys()[key] = false;
    }
}