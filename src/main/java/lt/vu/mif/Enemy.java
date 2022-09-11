package lt.vu.mif;

import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static lt.vu.mif.Startup.ENEMY_HEIGHT;
import static lt.vu.mif.Startup.ENEMY_WIDTH;
import static lt.vu.mif.Startup.PLAYER_HEIGHT;
import static lt.vu.mif.Startup.PLAYER_WIDTH;
import static lt.vu.mif.Startup.p;

@Setter
@Getter
class Enemy extends GameElement {
    private List<Spell> spellList = new ArrayList<>();
    private final PImage enemySprite;
    private final PImage spellSprite;
    private int xPos;
    private int yPos;

    public Enemy(PImage enemySprite, PImage spellSprite, int xPos, int yPos) {
        for (int i = 0; i < 4; i++) {
            spellList.add(new Spell(spellSprite, xPos + 20, yPos + 10, i));
        }
        this.enemySprite = enemySprite;
        this.spellSprite = spellSprite;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public boolean collision(GameElement element) {
        if (element instanceof Player player) {
            return collision(player);
        }
        return false;
    }

    private boolean collision(Player player) {
        return player.getXPos() + PLAYER_WIDTH - 16 >= xPos &&
                player.getXPos() <= xPos + ENEMY_WIDTH - 16 &&
                player.getYPos() + PLAYER_HEIGHT >= yPos &&
                player.getYPos() <= yPos + ENEMY_HEIGHT - PLAYER_HEIGHT / 2;
    }

    @Override
    public void update(int xDelta, int yDelta) {
        xPos += xDelta;
        yPos += yDelta;
        spellList.forEach(spell -> spell.update(xDelta, yDelta));
    }

    @Override
    public void goBack(int xDelta, int yDelta) {
        xPos -= xDelta;
        yPos -= yDelta;
        spellList.forEach(spell -> spell.goBack(xDelta, yDelta));
    }

    @Override
    public void render() {
        spellList.forEach(Spell::render);
        p.image(enemySprite, xPos, yPos, ENEMY_HEIGHT, ENEMY_HEIGHT);
    }

}