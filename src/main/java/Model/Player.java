package Model;

/**
 * @author tomas
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import processing.core.PImage;

@Getter
@Setter
@NoArgsConstructor
public class Player extends GameElement {
    private PImage[][] sprite;
    private boolean inMotion;
    private double currentFrame;
    private int currentDirection;
    private int xPos;
    private int yPos;
    private int row;
    private int column;
    private int speed = 3;

    public Player(PImage spriteSheet, int xPos, int yPos, int row, int column) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.row = row;
        this.column = column;
        this.sprite = new PImage[column][row];
        init(spriteSheet);
    }

    private void init(PImage spriteSheet) {
        int left = 14;
        int top = 4;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                sprite[j][i] = spriteSheet.get(i * 64 + left, j * 64 + top, 36, 64);
            }
        }
    }

    public void updateDirection(int xDelta, int yDelta) {
        currentFrame = ((currentFrame + 0.4) % 8);
        inMotion = true;
        if (xDelta == 0 && yDelta == 0) {
            inMotion = false;
        } else if (xDelta == -speed) {
            currentDirection = 3;
        } else if (xDelta == speed) {
            currentDirection = 1;
        } else if (yDelta == -speed) {
            currentDirection = 2;
        } else if (yDelta == speed) {
            currentDirection = 0;
        }
    }
}