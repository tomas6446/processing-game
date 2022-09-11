package lt.vu.mif;

import processing.core.PApplet;

public class Startup extends PApplet {

    public static PApplet p;

    // TODO Properties file
    // param
    public static final int WALL_WIDTH = 110;
    public static final int WALL_HEIGHT = 110;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int PLAYER_WIDTH = 70;
    public static final int PLAYER_HEIGHT = 60;
    public static final int ENEMY_WIDTH = 120;
    public static final int ENEMY_HEIGHT = 120;
    public static final int SPELL_SIZE = 70;

    private int STAGE = 0;

    private Game game;

    public Startup() {
        p = this;
    }

    @Override
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
                loadImage("exit.png"),      /* exit */
                map[STAGE]
        );
    }

    @Override
    public void setup() {
        frameRate(120);
        gameInit();
    }

    @Override
    public void draw() {
        clear();
        if (game.isNextStage()) {
            STAGE++;
            gameInit();
        } else {
            game.render();
        }

    }

    @Override
    public void keyPressed() {
        game.getKeys()[key] = true;
    }

    @Override
    public void keyReleased() {
        game.getKeys()[key] = false;
    }

}
