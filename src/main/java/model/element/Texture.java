package model.element;

import processing.core.PImage;

/**
 * @author tomas
 * A simple Texture class to be used as a DTO
 */

public record Texture(PImage sprite, int top, int left, int width, int height) {
    public Texture(PImage sprite, int top, int left, int width, int height) {
        this.sprite = sprite.get(top, left, width, height);
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }
}
