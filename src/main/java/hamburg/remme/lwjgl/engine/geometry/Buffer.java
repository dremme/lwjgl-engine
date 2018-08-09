package hamburg.remme.lwjgl.engine.geometry;

import static org.lwjgl.opengl.GL15.*;

public class Buffer {

    private final int pointer;
    private final int type;

    public static void free(int type) {
        glBindBuffer(type, 0);
    }

    private Buffer(int type) {
        this.type = type;
        pointer = glGenBuffers();
        glBindBuffer(type, pointer);
    }

    public Buffer(int type, float[] data) {
        this(type);
        glBufferData(type, data, GL_STATIC_DRAW);
    }

    public Buffer(int type, short[] data) {
        this(type);
        glBufferData(type, data, GL_STATIC_DRAW);
    }

    // TODO: needed? we usually do not use buffers alone
    public void bind() {
        glBindBuffer(type, pointer);
    }

    public void update(float[] data) {
        glBufferSubData(type, 0, data);
    }

    public void update(short[] data) {
        glBufferSubData(type, 0, data);
    }

    public void delete() {
        glDeleteBuffers(pointer);
    }

}
