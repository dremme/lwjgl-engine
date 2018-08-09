package hamburg.remme.lwjgl.engine;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.val;

public abstract class Application<T extends Application<T>> {

    private final Set<GameObject<T>> gameObjects = new HashSet<>();
    @Getter private Window window;
    @Getter private Renderer renderer;
    @Getter private long time = currentTimeMillis();
    @Getter private long lastTime = currentTimeMillis();
    private long lastFps = currentTimeMillis();
    private int fps = 0;

    public void launch(int width, int height, String title) {
        window = new Window();
        window.open(width, height, title);

        renderer = new Renderer();

        start();

        while (window.isOpen()) {
            // Timing and FPS stuff
            time = currentTimeMillis();
            lastTime = time;
            if (time - lastFps > 1000) {
                window.setTitle(format("%s (%d FPS)", title, fps));
                fps = 0;
                lastFps = time;
            }
            fps++;

            update();
            lateUpdate();

            renderer.update();
            window.update();
        }

        stop();
    }

    public void add(GameObject<T> gameObject) {
        gameObject.getBehaviors().forEach(b -> {
            b.setGameObject(gameObject);
            b.setApplication(getThis());
            b.setWindow(window);
            b.setRenderer(renderer);
        });
        gameObjects.add(gameObject);
        if (gameObject.hasModel()) renderer.add(gameObject);
        gameObject.getBehaviors().forEach(Behavior::start);
    }

    public void remove(GameObject<T> gameObject) {
        gameObjects.remove(gameObject);
        if (gameObject.hasModel()) renderer.remove(gameObject);
        gameObject.getBehaviors().forEach(Behavior::stop);
    }

    protected abstract T getThis();

    protected abstract void start();

    protected abstract void stop();

    private void update() {
        // FIXME: how is the performance with forEach()?
        // TODO: how is the performance multi-threaded?
        for (val o : gameObjects) for (val b : o.getBehaviors()) if (b.isEnabled()) b.update();
    }

    private void lateUpdate() {
        // FIXME: how is the performance with forEach()?
        // TODO: how is the performance multi-threaded?
        for (val o : gameObjects) for (val b : o.getBehaviors()) if (b.isEnabled()) b.lateUpdate();
    }

}
