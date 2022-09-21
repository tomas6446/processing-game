package model;

import lombok.Getter;

@Getter
public class MapBuilder {
    private final Map map;

    public MapBuilder(Map map) {
        this.map = map;
    }
}
