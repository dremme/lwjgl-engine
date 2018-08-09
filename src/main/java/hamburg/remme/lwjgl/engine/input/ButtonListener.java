package hamburg.remme.lwjgl.engine.input;

import lombok.NonNull;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class ButtonListener extends GLFWMouseButtonCallback {

    @NonNull private final InputCallback callback;

    public ButtonListener(InputCallback callback) {
        this.callback = callback;
    }

    @Override public void invoke(long window, int button, int action, int mods) {
        callback.call(button, action);
    }

}
