package lt.vu.mif;

import lombok.Getter;
import processing.core.PImage;

import static lt.vu.mif.Startup.PLAYER_HEIGHT;
import static lt.vu.mif.Startup.PLAYER_WIDTH;
import static lt.vu.mif.Startup.WALL_HEIGHT;
import static lt.vu.mif.Startup.WALL_WIDTH;
import static lt.vu.mif.Startup.p;

@Getter
class Map extends GameElement {
    private final PImage texture;
    // wall positions
    private final int[] xPos;
    private final int[] yPos;
    private final int[][] matrix;
    private int positionSize;

    public Map(PImage texture, int[][] matrix) {
        this.texture = texture;
        this.matrix = matrix;
        this.xPos = new int[matrix.length * matrix[0].length];
        this.yPos = new int[matrix.length * matrix[0].length];
    }

    public int getXPos(int index) {
        return xPos[index];
    }

    public int getYPos(int index) {
        return yPos[index];
    }

    public void pushPositions(int x, int y) {
        xPos[positionSize] = x;
        yPos[positionSize] = y;
        positionSize++;
    }

    @Override
    public boolean collision(GameElement element) {
        if (element instanceof Player player) {
            return collision(player);
        }
        return false;
    }

    private boolean collision(Player player) {
        for (int i = 0; i < getPositionSize(); i++) {
            if (player.getXPos() + PLAYER_WIDTH - 16 >= getXPos(i) &&
                    player.getXPos() <= getXPos(i) + WALL_WIDTH - 16 &&
                    player.getYPos() + PLAYER_HEIGHT >= getYPos(i) &&
                    player.getYPos() <= getYPos(i) + WALL_HEIGHT - PLAYER_HEIGHT / 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render() {
        for (int i = 0; i < positionSize; i++) {
            p.image(texture, xPos[i], yPos[i], WALL_WIDTH, WALL_HEIGHT);
        }
    }

    @Override
    public void update(int xDelta, int yDelta) {
        for (int i = 0; i < positionSize; i++) {
            xPos[i] += xDelta;
            yPos[i] += yDelta;
        }
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
        for (int i = 0; i < positionSize; i++) {
            xPos[i] -= xDelta;
            yPos[i] -= yDelta;
        }
    }
}