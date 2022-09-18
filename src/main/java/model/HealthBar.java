package model;

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
    private int width;
    private int height;

    public HealthBar(PImage sprite, int width, int height) {
        this.sprite = new PImage[healthCount + 1];
        this.width = width;
        this.height = height;
        for (int i = 0; i < healthCount + 1; i++) {
            this.sprite[i] = sprite.get(i * 70, i, 70, 70);
        }
    }

    public void removeHealth() {
        if (healthCount > 0) {
            healthCount--;
        }
    }
}
