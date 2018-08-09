package hamburg.remme.lwjgl.engine.input;

@FunctionalInterface
public interface InputCallback {

    void call(int input, int action);

}
