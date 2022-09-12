package lt.vu.mif;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import static lt.vu.mif.Startup.p;

/**
 * @author tomas
 */
@Getter
@Setter
public class HealthBar extends GameElement {
    private PImage spriteSheet;
    private PImage[] sprite;

    private int healthCount = 6;

    public HealthBar(PImage spriteSheet) {
        this.spriteSheet = spriteSheet;
        this.sprite = new PImage[healthCount+1];
        init();
    }


    private void init() {
        for (int i = 0; i < healthCount+1; i++) {
            sprite[i] = spriteSheet.get(i * 70, i, 70, 70);
        }
    }

    public void removeHealth() {
        if(healthCount > 0) {
            healthCount--;
        }
    }

    @Override
    public boolean collision(GameElement element) {
        return false;
    }

    @Override
    public void render() {
        p.image(sprite[healthCount], 0, 0, 96, 96);
    }

    @Override
    public void update(int xDelta, int yDelta) {

    }

    @Override
    public void goBack(int xDelta, int yDelta) {

    }


}
