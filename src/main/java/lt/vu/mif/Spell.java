package lt.vu.mif;

import lombok.Setter;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static lt.vu.mif.Startup.PLAYER_HEIGHT;
import static lt.vu.mif.Startup.PLAYER_WIDTH;
import static lt.vu.mif.Startup.SPELL_SIZE;
import static lt.vu.mif.Startup.WALL_HEIGHT;
import static lt.vu.mif.Startup.WALL_WIDTH;
import static lt.vu.mif.Startup.p;

@Setter
class Spell extends GameElement {

    private static final int LEFT = 0;
    private static final int UP_LEFT = 1;
    private static final int UP = 2;
    private static final int UP_RIGHT = 3;
    private static final int RIGHT = 4;
    private static final int DOWN_RIGHT = 5;
    private static final int DOWN = 6;
    private static final int DOWN_LEFT = 7;

    // spell sprite sheets
    private final PImage spriteSheet;
    private final PImage[][] sprite;
    private final int spriteRows = 8;
    private final int spriteColumns = 8;
    private int initialXpos;
    private int initialYpos;
    // spell position
    private List<Integer> xPos = new ArrayList<>();
    private List<Integer> yPos = new ArrayList<>();
    // spell sprite state
    private double currentFrame;
    private int initialTime = p.millis();
    private int cooldown = 3;
    private int offset;

    public Spell(PImage spriteSheet, int initialXpos, int initialYpos, int offset) {
        this.spriteSheet = spriteSheet;
        this.initialXpos = initialXpos;
        this.initialYpos = initialYpos;
        this.offset = offset;
        this.sprite = new PImage[spriteColumns][spriteRows];
        initSprites();
        initPos();
    }

    @Override
    public boolean collision(GameElement element) {
        if (element instanceof Player player) {
            return collision(player);
        } else if (element instanceof Map map) {
            return collision(map);
        }
        return false;
    }

    private boolean collision(Player player) {
        for (int j = 0; j < xPos.size(); j++) {
            if (player.getXPos() + PLAYER_WIDTH / 2 >= xPos.get(j) &&
                    player.getXPos() <= xPos.get(j) + SPELL_SIZE / 2 &&
                    player.getYPos() + PLAYER_HEIGHT / 2 >= yPos.get(j) &&
                    player.getYPos() <= yPos.get(j)) {
                return true;
            }
        }
        return false;
    }

    private boolean collision(Map map) {
        for (int j = 0; j < xPos.size(); j++) {
            for (int i = 0; i < map.getPositionSize(); i++) {
                if (map.getXPos(i) + WALL_WIDTH >= xPos.get(j) &&
                        map.getXPos(i) <= xPos.get(j) + SPELL_SIZE &&
                        map.getYPos(i) + WALL_HEIGHT >= yPos.get(j) &&
                        map.getYPos(i) <= yPos.get(j) + SPELL_SIZE) {
                    xPos.set(j, initialXpos);
                    yPos.set(j, initialYpos);
                    return true;
                }
            }
        }
        return false;
    }

    private void initSprites() {
        // store all sprite pieces in a matrix
        for (int i = 0; i < spriteRows; i++) {
            for (int j = 0; j < spriteColumns; j++) {
                sprite[j][i] = spriteSheet.get(i * 64, j * 64, 64, 64);
            }
        }
    }

    private void initPos() {
        // store all fireball positions in dragon position
        for (int i = 0; i < spriteRows; i++) {
            xPos.add(initialXpos);
            yPos.add(initialYpos);
        }
    }

    @Override
    public void render() {
        move();
        animate();
    }

    private void move() {
        // spell logic
        if (cooldown < p.millis() - initialTime) {
            initialTime = p.millis();
            yPos.set(UP, yPos.get(UP) - offset);

            xPos.set(UP_RIGHT, xPos.get(UP_RIGHT) + offset);
            yPos.set(UP_RIGHT, yPos.get(UP_RIGHT) - offset);

            xPos.set(LEFT, xPos.get(LEFT) - offset);

            xPos.set(UP_LEFT, xPos.get(UP_LEFT) - offset);
            yPos.set(UP_LEFT, yPos.get(UP_LEFT) - offset);

            yPos.set(DOWN, yPos.get(DOWN) + offset);

            xPos.set(DOWN_RIGHT, xPos.get(DOWN_RIGHT) + offset);
            yPos.set(DOWN_RIGHT, yPos.get(DOWN_RIGHT) + offset);

            xPos.set(RIGHT, xPos.get(RIGHT) + offset);

            xPos.set(DOWN_LEFT, xPos.get(DOWN_LEFT) - offset);
            yPos.set(DOWN_LEFT, yPos.get(DOWN_LEFT) + offset);
        }
    }

    private void animate() {
        for (int i = 0; i < 8; i++) {
            p.image(sprite[i][1 + (int) currentFrame], xPos.get(i), yPos.get(i), SPELL_SIZE, SPELL_SIZE);
        }
    }

    @Override
    public void update(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.50) % 7);
        for (int i = 0; i < 8; i++) {
            xPos.set(i, xPos.get(i) + xDelta);
            yPos.set(i, yPos.get(i) + yDelta);
        }
        initialXpos += xDelta;
        initialYpos += yDelta;
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.50) % 7);
        for (int i = 0; i < 8; i++) {
            xPos.set(i, xPos.get(i) - xDelta);
            yPos.set(i, yPos.get(i) - yDelta);
        }
        initialXpos -= xDelta;
        initialYpos -= yDelta;
    }

    public void reset() {
        this.xPos = new ArrayList<>();
        this.yPos = new ArrayList<>();
        initSprites();
        initPos();
    }
}