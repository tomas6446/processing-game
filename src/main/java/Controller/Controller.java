package Controller;

import Model.Map;
import Model.MapObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tomas
 */
@Getter
@Setter
public class Controller {
    private Map map;
    private boolean[] keys;
    private int xDelta;
    private int yDelta;

    public Controller(Map map) {
        this.map = map;
        this.keys = new boolean[128];
    }

    public void update(int offset) {
        for (MapObject obj : map.getObjects()) {
            obj.setXPos(obj.getXPos() + xDelta * offset);
            obj.setYPos(obj.getYPos() + yDelta * offset);
        }
    }
}
