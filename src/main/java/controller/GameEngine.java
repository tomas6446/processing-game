package controller;

import lombok.Getter;
import model.Map;
import model.element.Texture;
import processing.core.PApplet;
import view.MapRenderer;
import view.Renderer;

import java.util.List;

/**
 * @author tomas
 * Is responsible for telling the MapRenderer what to render
 */
@Getter
public class GameEngine {
    private final Map map;
    private final Renderer renderer;
    private final Controller controller;

    public GameEngine(List<Texture> textures, int[][] grid, int tileSize) {
        this.map = new Map(textures, grid, tileSize);
        this.renderer = new MapRenderer(map);
        this.controller = new Controller(map);
    }

    public void render(PApplet pApplet) {
        controller.handleEvent();
        renderer.render(pApplet);
    }
}
