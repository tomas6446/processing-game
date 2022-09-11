package lt.vu.mif;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MapElement {

    BACKGROUND(0),
    WALL(1),
    PLAYER(2),
    ENEMY(3),
    EXIT(4);

    private final int elementNumber;

    public MapElement fromValue(int elementNumber) {
        return Arrays.stream(MapElement.values())
                .filter(i -> i.getElementNumber() == elementNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown element number " + elementNumber));
    }
}
