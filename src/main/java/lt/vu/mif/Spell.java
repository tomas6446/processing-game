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
    // spell sprite sheets
    private final PImage spriteSheet;
    private final PImage[] sprite;
    private final int SPRITE_ROW = 8;
    private int initialXpos;
    private int initialYpos;
    // spell position
    private List<Integer> xPos = new ArrayList<>();
    private List<Integer> yPos = new ArrayList<>();
    // spell sprite state
    private double currentFrame;
    private int offset;

    public Spell(PImage spriteSheet, int initialXpos, int initialYpos, int offset) {
        this.spriteSheet = spriteSheet;
        this.initialXpos = initialXpos;
        this.initialYpos = initialYpos;
        this.offset = offset;
        this.sprite = new PImage[SPRITE_ROW];
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
                    player.getXPos() + PLAYER_WIDTH / 2 <= xPos.get(j) + SPELL_SIZE &&
                    player.getYPos() + PLAYER_HEIGHT / 2 >= yPos.get(j) &&
                    player.getYPos() + PLAYER_HEIGHT / 2 <= yPos.get(j) + SPELL_SIZE) {
                xPos.set(j, initialXpos);
                yPos.set(j, initialYpos);
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
                        map.getYPos(i) + WALL_HEIGHT / 2 >= yPos.get(j) &&
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
        for (int i = 0; i < SPRITE_ROW; i++) {
            sprite[i] = spriteSheet.get(i * 256, 0, 256, 256);
        }
    }

    private void initPos() {
        for (int i = 0; i < 8; i++) {
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
        yPos.set(0, yPos.get(0) - offset);

        xPos.set(1, xPos.get(1) + offset);
        yPos.set(1, yPos.get(1) - offset);

        xPos.set(2, xPos.get(2) - offset);

        xPos.set(3, xPos.get(3) - offset);
        yPos.set(3, yPos.get(3) - offset);

        yPos.set(4, yPos.get(4) + offset);

        xPos.set(5, xPos.get(5) + offset);
        yPos.set(5, yPos.get(5) + offset);

        xPos.set(6, xPos.get(6) + offset);

        xPos.set(7, xPos.get(7) - offset);
        yPos.set(7, yPos.get(7) + offset);
    }

    private void animate() {
        for (int i = 0; i < 8; i++) {
            p.image(sprite[(int) currentFrame], xPos.get(i), yPos.get(i), SPELL_SIZE, SPELL_SIZE);
        }
    }

    @Override
    public void update(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.40) % SPRITE_ROW);
        for (int i = 0; i < 8; i++) {
            xPos.set(i, xPos.get(i) + xDelta);
            yPos.set(i, yPos.get(i) + yDelta);
        }
        initialXpos += xDelta;
        initialYpos += yDelta;
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.40) % SPRITE_ROW);
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