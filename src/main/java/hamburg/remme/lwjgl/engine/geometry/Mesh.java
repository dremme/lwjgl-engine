package hamburg.remme.lwjgl.engine.geometry;

import static lombok.AccessLevel.PROTECTED;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import lombok.Getter;

public class Mesh {

    private final int pointer;
    private final Buffer vertexBuffer;
    private final Buffer indexBuffer;
    @Getter(PROTECTED) private final int size;

    public static void free() {
        glBindVertexArray(0);
        Buffer.free(GL_ARRAY_BUFFER); // TODO: needed?
        Buffer.free(GL_ELEMENT_ARRAY_BUFFER); // TODO: needed?
    }

    public Mesh(float[] data, short[] indices) {
        size = indices.length;
        pointer = glGenVertexArrays();
        glBindVertexArray(pointer);
        vertexBuffer = new Buffer(GL_ARRAY_BUFFER, data);
        indexBuffer = new Buffer(GL_ELEMENT_ARRAY_BUFFER, indices);
        // VAO remembers the bound buffer

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(
                0,
                3,
                GL_FLOAT,
                false,
                0,
                0);
    }

    public void bind() {
        glBindVertexArray(pointer);
    }

    public void draw(int drawMode) {
        glDrawElements(
                drawMode,
                size,
                GL_UNSIGNED_SHORT,
                0);
    }

    public void delete() {
        glDeleteVertexArrays(pointer);
        vertexBuffer.delete();
        indexBuffer.delete();
    }

}
