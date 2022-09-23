package model.element;

import lombok.Getter;
import processing.core.PImage;

/**
 * @author tomas
 * A simple Texture class to be used as a DTO
 */

@Getter
public class Texture {
    private final PImage sprite;
    private final int top;
    private final int left;
    private final int width;
    private final int height;

    public Texture(PImage sprite, int top, int left, int width, int height) {
        this.sprite = sprite;
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }
}
