package lt.vu.mif;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import static lt.vu.mif.Startup.PLAYER_HEIGHT;
import static lt.vu.mif.Startup.PLAYER_WIDTH;
import static lt.vu.mif.Startup.p;

@Getter
@Setter
class Player extends GameElement {
    private static final int UP = 0;
    private static final int LEFT = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    public static final int SPEED = 4;

    // player position
    private int xPos;
    private int yPos;
    // player sprite sheets
    private final int spriteRows = 9;
    private final int spriteColumns = 4;
    private final PImage[][] sprite;
    private final PImage spriteSheet;
    // player sprite state
    private boolean inMotion;
    private int currentDirection;
    private double currentFrame;

    public Player(PImage spriteSheet, int xPos, int yPos) {
        this.spriteSheet = spriteSheet;
        this.sprite = new PImage[spriteColumns][spriteRows];
        this.xPos = xPos;
        this.yPos = yPos;
        init();
    }

    @Override
    public void render() {
        animate();
    }

    @Override
    public void update(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.7) % 8);
        inMotion = true;
        if (xDelta == 0 && yDelta == 0) {
            inMotion = false;
        } else if (xDelta == -SPEED) {
            currentDirection = RIGHT;
        } else if (xDelta == SPEED) {
            currentDirection = LEFT;
        } else if (yDelta == -SPEED) {
            currentDirection = DOWN;
        } else if (yDelta == SPEED) {
            currentDirection = UP;
        }
    }

    @Override
    public boolean collision(GameElement element) {
        return false;
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
    }

    private void init() {
        for (int i = 0; i < spriteRows; i++) {
            for (int j = 0; j < spriteColumns; j++) {
                sprite[j][i] = spriteSheet.get(i * 64, j * 64, 64, 64);
            }
        }
    }

    private void animate() {
        if (inMotion) {
            p.image(sprite[currentDirection][1 + (int) currentFrame], xPos, yPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        } else {
            p.image(sprite[currentDirection][0], xPos, yPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        }
    }

}