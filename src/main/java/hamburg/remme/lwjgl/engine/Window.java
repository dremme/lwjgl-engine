package hamburg.remme.lwjgl.engine;

import static org.lwjgl.BufferUtils.createDoubleBuffer;
import static org.lwjgl.BufferUtils.createIntBuffer;
import static org.lwjgl.glfw.GLFW.*;

import hamburg.remme.lwjgl.engine.input.ButtonListener;
import hamburg.remme.lwjgl.engine.input.KeyListener;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Window {

    public static final int BUTTON_LEFT = GLFW_MOUSE_BUTTON_LEFT;
    public static final int BUTTON_RIGHT = GLFW_MOUSE_BUTTON_RIGHT;
    public static final int BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_MIDDLE;
    public static final int KEY_A = GLFW_KEY_A;
    public static final int KEY_B = GLFW_KEY_B;
    public static final int KEY_C = GLFW_KEY_C;
    public static final int KEY_D = GLFW_KEY_D;
    public static final int KEY_E = GLFW_KEY_E;
    public static final int KEY_F = GLFW_KEY_F;
    public static final int KEY_G = GLFW_KEY_G;
    public static final int KEY_H = GLFW_KEY_H;
    public static final int KEY_I = GLFW_KEY_I;
    public static final int KEY_J = GLFW_KEY_J;
    public static final int KEY_K = GLFW_KEY_K;
    public static final int KEY_L = GLFW_KEY_L;
    public static final int KEY_M = GLFW_KEY_M;
    public static final int KEY_N = GLFW_KEY_N;
    public static final int KEY_O = GLFW_KEY_O;
    public static final int KEY_P = GLFW_KEY_P;
    public static final int KEY_Q = GLFW_KEY_Q;
    public static final int KEY_R = GLFW_KEY_R;
    public static final int KEY_S = GLFW_KEY_S;
    public static final int KEY_T = GLFW_KEY_T;
    public static final int KEY_U = GLFW_KEY_U;
    public static final int KEY_V = GLFW_KEY_V;
    public static final int KEY_W = GLFW_KEY_W;
    public static final int KEY_X = GLFW_KEY_X;
    public static final int KEY_Y = GLFW_KEY_Y;
    public static final int KEY_Z = GLFW_KEY_Z;

    private final Set<Integer> keys = new HashSet<>();
    private final Set<Integer> buttons = new HashSet<>();
    private final Vector2f mousePosition = new Vector2f();
    private long pointer;
    @Getter private int width;
    @Getter private int height;

    private final IntBuffer pWidth = createIntBuffer(1);
    private final IntBuffer pHeight = createIntBuffer(1);
    private final DoubleBuffer pMouseX = createDoubleBuffer(1);
    private final DoubleBuffer pMouseY = createDoubleBuffer(1);

    Window() {
        GLFWErrorCallback.createPrint(System.err).set();
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1); // highest we can go on MacOS
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE); // MacOS requires this
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // TODO: change later
    }

    public Vector2fc getMousePosition() {
        return mousePosition;
    }

    public boolean getButton(int button) {
        return buttons.contains(button);
    }

    public boolean getKey(int key) {
        return keys.contains(key);
    }

    public void setVSync(boolean vSync) {
        glfwSwapInterval(vSync ? 1 : 0);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(pointer, title);
    }

    void open(int width, int height, String title) {
        pointer = glfwCreateWindow(width, height, title, 0, 0);
        glfwMakeContextCurrent(pointer);
        glfwShowWindow(pointer);
        initWindowSize();
        initListeners();
    }

    boolean isOpen() {
        return !glfwWindowShouldClose(pointer);
    }

    void update() {
        glfwSwapBuffers(pointer);
        glfwPollEvents();

        // Mouse position
        // TODO: use the callback?
        glfwGetCursorPos(pointer, pMouseX, pMouseY);
        mousePosition.x = (float) pMouseX.get(0);
        mousePosition.y = (float) pMouseY.get(0);
    }

    private void initWindowSize() {
        // TODO: call on FBO size changes
        glfwGetWindowSize(pointer, pWidth, pHeight);
        this.width = pWidth.get(0);
        this.height = pHeight.get(0);
    }

    private void initListeners() {
        glfwSetMouseButtonCallback(pointer, new ButtonListener(this::handleButtonEvent));
        glfwSetKeyCallback(pointer, new KeyListener(this::handleKeyEvent));
    }

    private void handleButtonEvent(int button, int action) {
        if (action == GLFW_PRESS) buttons.add(button);
        else if (action == GLFW_RELEASE) buttons.remove(button);
    }

    private void handleKeyEvent(int key, int action) {
        if (action == GLFW_PRESS) keys.add(key);
        else if (action == GLFW_RELEASE) keys.remove(key);
    }

}
