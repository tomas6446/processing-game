package lt.vu.mif;

abstract class GameElement {

    public abstract void update(int xDelta, int yDelta);

    public abstract void goBack(int xDelta, int yDelta);

    public abstract void render();

    public abstract boolean collision(GameElement element);

}