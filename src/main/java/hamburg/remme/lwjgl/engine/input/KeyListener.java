package hamburg.remme.lwjgl.engine.input;

import lombok.NonNull;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyListener extends GLFWKeyCallback {

    @NonNull private final InputCallback callback;

    public KeyListener(InputCallback callback) {
        this.callback = callback;
    }

    @Override public void invoke(long window, int key, int scancode, int action, int mods) {
        callback.call(key, action);
    }

}
