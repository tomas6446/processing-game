package lt.vu.mif;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import static lt.vu.mif.Startup.PLAYER_HEIGHT;
import static lt.vu.mif.Startup.WALL_HEIGHT;
import static lt.vu.mif.Startup.WALL_WIDTH;

@Getter
@Setter
public class Exit extends GameElement {
    private PImage exitSpriteSheet;
    private PImage exitSprite;
    private int xPos;
    private int yPos;

    public Exit(PImage exitSprite, int xPos, int yPos) {
        this.exitSpriteSheet = exitSprite;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public boolean collision(GameElement element) {
        if (element instanceof Player player) {
            return collision(player);
        }
        return false;
    }

    private boolean collision(Player player) {
        return player.getXPos() >= xPos &&
                player.getXPos() <= xPos + WALL_WIDTH &&
                player.getYPos() + PLAYER_HEIGHT >= yPos &&
                player.getYPos() <= yPos + WALL_HEIGHT;
    }

    @Override
    public void render() {

    }

    @Override
    public void update(int xDelta, int yDelta) {
        xPos += xDelta;
        yPos += yDelta;
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
        xPos -= xDelta;
        yPos -= yDelta;
    }


}
