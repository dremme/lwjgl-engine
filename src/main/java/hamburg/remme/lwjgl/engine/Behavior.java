package hamburg.remme.lwjgl.engine;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PUBLIC;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter(PACKAGE)
public abstract class Behavior<T extends Application<T>> {

    @Setter(PUBLIC) private boolean enabled = true;
    @NonNull private GameObject<T> gameObject;
    @NonNull private T application;
    @NonNull private Window window;
    @NonNull private Renderer renderer;

    public void start() {
    }

    public void update() {
    }

    public void lateUpdate() {
    }

    public void stop() {
    }

}
