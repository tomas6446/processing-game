package model.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import processing.core.PImage;

/**
 * @author Tomas Kozakas, 1 grupe
 */
@Getter
@Setter
@NoArgsConstructor
public class Player extends GameElement {
    private PImage[][] playerSprite;
    private boolean inMotion;
    private double currentFrame;
    private int currentDirection;
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int speed;
    private Texture texture;

    public Player(Texture texture, int xPos, int yPos, int width, int height, int row, int column, int speed) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.texture = texture;
        this.playerSprite = new PImage[column][row];
        init(row, column);
    }

    private void init(int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                playerSprite[j][i] = texture.getSprite().get(i * 64 + texture.getTop(), j * 64 + texture.getLeft(), texture.getWidth(), texture.getHeight());
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