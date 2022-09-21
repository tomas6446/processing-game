package controller;

import lombok.Getter;
import model.Map;
import model.MapBuilder;
import processing.core.PApplet;
import processing.core.PImage;
import view.MapRenderer;
import view.Renderer;

/**
 * @author tomas
 */
@Getter
public class GameEngine {
    private final MapBuilder mapBuilder;
    private final Renderer renderer;
    private final Controller controller;

    public GameEngine(PImage[] spriteSheet, int[][] grid, int tileSize) {
        this.mapBuilder = new MapBuilder(new Map(spriteSheet, grid, tileSize));
        this.renderer = new MapRenderer(mapBuilder);
        this.controller = new Controller(mapBuilder.getMap());

        this.controller.center();
    }

    public void render(PApplet pApplet) {
        controller.handleEvent();
        renderer.render(pApplet);
    }
}
