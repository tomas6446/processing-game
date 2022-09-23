package model.element;

import lombok.Getter;
import processing.core.PImage;

/**
 * @author tomas
 * A simple Texture class to be used as a DTO
 */

@Getter
public class Texture {
    private PImage sprite;
    private int top;
    private int left;
    private int width;
    private int height;
    public Texture(PImage sprite, int top, int left, int width, int height, boolean crop) {
        if(crop) {
            this.sprite = sprite.get(top, left, width, height);
        } else {
            this.sprite = sprite;
        }
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }
}
