package lt.vu.mif;

import processing.core.PApplet;

public class Startup extends PApplet {

    public static PApplet p;

    // TODO Properties file
//    try (
//    InputStream input = new FileInputStream("game.properties")) {
//        Properties prop = new Properties();
//        prop.load(input);
//        WALL_WIDTH = parseInt(prop.getProperty("WALL_WIDTH"));
//        WALL_HEIGHT = parseInt(prop.getProperty("WALL_HEIGHT"));
//        SCREEN_WIDTH = parseInt(prop.getProperty("SCREEN_WIDTH"));
//        SCREEN_HEIGHT = parseInt(prop.getProperty("SCREEN_HEIGHT"));
//        PLAYER_WIDTH = parseInt(prop.getProperty("PLAYER_WIDTH"));
//        PLAYER_HEIGHT = parseInt(prop.getProperty("PLAYER_HEIGHT"));
//        ENEMY_WIDTH = parseInt(prop.getProperty("ENEMY_WIDTH"));
//        ENEMY_HEIGHT = parseInt(prop.getProperty("ENEMY_HEIGHT"));
//        SPELL_WIDTH = parseInt(prop.getProperty("SPELL_WIDTH"));
//        SPELL_HEIGHT = parseInt(prop.getProperty("SPELL_HEIGHT"));
//    } catch (
//    IOException ex) {
//        ex.printStackTrace();
//    }
    // param
    public static final int WALL_WIDTH = 64;
    public static final int WALL_HEIGHT = 64;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int PLAYER_WIDTH = 64;
    public static final int PLAYER_HEIGHT = 64;
    public static final int ENEMY_WIDTH = 64;
    public static final int ENEMY_HEIGHT = 64;
    public static final int SPELL_WIDTH = 64;
    public static final int SPELL_HEIGHT = 64;

    private int STAGE = 0;

    private Game game;

    public Startup() {
        p = this;
    }

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public static void main(String[] args) {
        PApplet.main("lt.vu.mif.Startup");
    }

    // TODO read from json file
    // map configuration
    // 1 - wall
    // 2 - player position
    // 3 - enemy
    // 4 - exit
    int[][][] map = {
            {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 2, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                    {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 4},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 3, 1, 0, 4},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 3, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            },
            {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            }
    };

    public void gameInit() {
        game = new Game(
                loadImage("stoneTiles.png"), /* floor */
                loadImage("wall.png"),      /* wall */
                loadImage("dragon.png"),    /* enemy list */
                loadImage("fireball.png"),  /* enemy spell */
                loadImage("player.png"),    /* player */
                loadImage("healthBar.png"), /* health bar */
                loadImage("exit.png"),      /* exit */
                map[STAGE]
        );
    }

    public void setup() {
        frameRate(60);
        gameInit();
    }

    public void draw() {
        clear();
        if (game.isNextStage()) {
            STAGE++;
            gameInit();
        } else if (game.isGameOver()) {
            STAGE = 0;
            gameInit();
        } else {
            game.render();
        }

    }

    public void keyPressed() {
        if (game.isAllowed(key)) {
            game.getKeys()[key] = true;
        }

    }

    public void keyReleased() {
        if (game.isAllowed(key)) {
            game.getKeys()[key] = false;
        }
    }

}
