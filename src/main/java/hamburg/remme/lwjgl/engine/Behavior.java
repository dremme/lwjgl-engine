package hamburg.remme.lwjgl.engine;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PUBLIC;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter public abstract class Behavior<T extends Application<T>> {

    @Setter(PUBLIC) private boolean enabled = true;
    @Setter(PACKAGE) @NonNull private GameObject gameObject;
    @Setter(PACKAGE) @NonNull private T application;
    @Setter(PACKAGE) @NonNull private Window window;
    @Setter(PACKAGE) @NonNull private Renderer renderer;

    public void start() {
    }

    public void update() {
    }

    public void lateUpdate() {
    }

    public void stop() {
    }

}
