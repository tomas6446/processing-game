package Model;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

/**
 * @author tomas
 */

@Getter
@Setter
public class HealthBar {
    private PImage[] sprite;
    private int healthCount = 6;

    public HealthBar(PImage spriteSheet) {
        this.sprite = new PImage[healthCount+1];
        for (int i = 0; i < healthCount+1; i++) {
            sprite[i] = spriteSheet.get(i * 70, i, 70, 70);
        }
    }

    public void removeHealth() {
        if(healthCount > 0) {
            healthCount--;
        }
    }
}
