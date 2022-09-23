package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int speed;


    public Player(Texture texture, int xPos, int yPos, int width, int height, int row, int column, int speed) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.playerSprite = new PImage[column][row];
        init(texture.getSprite(), texture.getTop(), texture.getLeft(), texture.getWidth(), texture.getHeight());
    }

    private void init(PImage spriteSheet, int top, int left, int spriteWidth, int spriteHeight) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                playerSprite[j][i] = spriteSheet.get(i * 64 + top, j * 64 + left, spriteWidth, spriteHeight);
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