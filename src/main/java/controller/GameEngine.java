package controller;

import lombok.Getter;
import model.Map;
import processing.core.PApplet;
import processing.core.PImage;
import view.MapRenderer;
import view.Renderer;

/**
 * @author tomas
 * Is responsible for telling the MapRenderer what to render
 */
@Getter
public class GameEngine {
    private final Map map;
    private final Renderer renderer;
    private final Controller controller;

    public GameEngine(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.map = new Map(spriteSheet, grid, tileSize);
        this.renderer = new MapRenderer(map);
        this.controller = new Controller(map);
    }

    public void render(PApplet pApplet) {
        controller.handleEvent();
        renderer.render(pApplet);
    }
}
