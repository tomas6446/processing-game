package lt.vu.mif;

import lombok.Getter;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static lt.vu.mif.Startup.WALL_HEIGHT;
import static lt.vu.mif.Startup.WALL_WIDTH;

@Getter
class Game {
    private final Map map;
    private final Background background;
    private final List<Enemy> enemyList = new ArrayList<>();
    private final boolean[] keys;
    private Player player;
    private Exit exit;

    private boolean nextStage = false;

    public Game(PImage backgroundSprite, PImage wallSprite, PImage enemySprite, PImage fireballSprite, PImage playerSprite, PImage exitSprite, int[][] wallMap) {
        this.map = new Map(wallSprite, wallMap);
        this.background = new Background(backgroundSprite, 0, 0, wallMap[0].length * WALL_WIDTH, wallMap.length * WALL_HEIGHT);
        this.keys = new boolean[128];

        init(enemySprite, fireballSprite, playerSprite, exitSprite);
    }

    public void render() {
        /* draw background */
        background.render();
        /* draw map */
        map.render();
        /* draw player */
        player.render();
        /* draw enemies */
        enemyList.forEach(Enemy::render);
        /* draw exit */
        exit.render();

        move();
    }

    public void move() {
        int xDelta = 0;
        int yDelta = 0;

        if (keys['w']) {
            yDelta += Player.SPEED;
        }
        if (keys['s']) {
            yDelta -= Player.SPEED;
        }
        if (keys['a']) {
            xDelta += Player.SPEED;
        }
        if (keys['d']) {
            xDelta -= Player.SPEED;
        }

        update(xDelta, yDelta);
        collision(xDelta, yDelta);
    }

    public void update(int xDelta, int yDelta) {
        background.update(xDelta, yDelta);
        map.update(xDelta, yDelta);
        player.update(xDelta, yDelta);
        exit.update(xDelta, yDelta);
        enemyList.forEach(enemy -> enemy.update(xDelta, yDelta));
    }

    public void collision(int xDelta, int yDelta) {
        if (map.collision(player)) {
            map.goBack(xDelta, yDelta);
            background.goBack(xDelta, yDelta);
            exit.goBack(xDelta, yDelta);
            enemyList.forEach(enemy -> enemy.goBack(xDelta, yDelta));
        }
        if (exit.collision(player)) {
            System.out.println("Next stage: ...");
            nextStage = true;
        }
        for (Enemy enemy : enemyList) {
            if (enemy.collision(player)) {
                map.goBack(xDelta, yDelta);
                background.goBack(xDelta, yDelta);
                exit.goBack(xDelta, yDelta);
                enemy.goBack(xDelta, yDelta);
            } else {
                for (Spell spell : enemy.getSpellList()) {
                    if (spell.collision(player)) {
                        System.out.println("fireball hit player");
                    }
                    if(spell.collision(map)) {
                        System.out.println("fireball hit map");
                    }
                }
            }
        }
    }

    private void init(PImage enemySprite, PImage fireballSprite, PImage playerSprite, PImage exitSprite) {
        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[0].length; j++) {
                int x = j * WALL_WIDTH;
                int y = i * WALL_HEIGHT;
                if (map.getMatrix()[i][j] == MapElement.WALL.getElementNumber()) {
                    this.map.pushPositions(j * WALL_WIDTH, i * WALL_HEIGHT);
                } else if (map.getMatrix()[i][j] == MapElement.PLAYER.getElementNumber()) {
                    this.player = new Player(playerSprite);
                } else if (map.getMatrix()[i][j] == MapElement.ENEMY.getElementNumber()) {
                    this.enemyList.add(new Enemy(enemySprite, fireballSprite, x, y));
                } else if (map.getMatrix()[i][j] == MapElement.EXIT.getElementNumber()) {
                    this.exit = new Exit(exitSprite, x, y);
                }
            }
        }
    }
}