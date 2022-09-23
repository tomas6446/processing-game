package model;

import lombok.Getter;
import lombok.Setter;
import model.element.Texture;
import processing.core.PImage;

import java.util.stream.IntStream;

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

    public HealthBar(Texture texture, int width, int height) {
        this.sprite = new PImage[healthCount + 1];
        this.width = width;
        this.height = height;
        IntStream.range(0, healthCount + 1)
                .forEachOrdered(i -> this.sprite[i] = texture.getSprite()
                        .get(i * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()));
    }

    public void removeHealth() {
        if (healthCount > 0) {
            healthCount--;
        }
    }
}
