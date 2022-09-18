package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.type.ObjectType;
import processing.core.PImage;

/**
 * @author tomas
 */
@Getter
@Setter
@NoArgsConstructor
public class Player extends GameElement {
    private PImage[][] playerSprite;
    private boolean inMotion;
    private double currentFrame;
    private int currentDirection;
    private int row;
    private int column;
    private int speed = 3;

    public Player(PImage spriteSheet, int xPos, int yPos, int row, int column) {
        super(spriteSheet, ObjectType.PLAYER, xPos, yPos);
        this.row = row;
        this.column = column;
        this.playerSprite = new PImage[column][row];
        init(spriteSheet);
    }

    private void init(PImage spriteSheet) {
        int left = 14;
        int top = 4;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                playerSprite[j][i] = spriteSheet.get(i * 64 + left, j * 64 + top, 36, 64);
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