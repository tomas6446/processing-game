package lt.vu.mif;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import static lt.vu.mif.Startup.p;

@Setter
@Getter
class Background extends GameElement {
    private final PImage background;
    private final int backgroundWidth;
    private final int backgroundHeight;
    private int xPos;
    private int yPos;

    public Background(PImage background, int xPos, int yPos, int backgroundWidth, int backgroundHeight) {
        this.background = background;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
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
        return false;
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

    @Override
    public void render() {
        p.image(background, xPos, yPos, backgroundWidth, backgroundHeight);
    }
}