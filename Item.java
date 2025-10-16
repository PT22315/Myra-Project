
package projectmyra;
//real
public class Item extends Actor {
    private boolean active = true;

    public Item(String path, int x, int y, int width, int height) {
        super(path, x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        active = value;
    }

    @Override
    public void update() {
    }
}
