package hamburg.remme.lwjgl.engine.geometry;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.util.List;
import lombok.val;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL31;

// TODO: see if this actually works
public class InstancedMesh extends Mesh {

    private static final int V4_SIZE = 16;

    private final Buffer modelViewBuffer;
    private final int instances;

    public InstancedMesh(float[] data, short[] indices, int instances) {
        super(data, indices);
        this.instances = instances;

        // Bind matrix buffer; pre-allocating instances * mat4
        modelViewBuffer = new Buffer(GL_ARRAY_BUFFER, new float[V4_SIZE * instances]);
        // VAO remembers the bound buffer

        // Bind matrix attributes
        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 4 * V4_SIZE, 0 * V4_SIZE);
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(4, 4, GL_FLOAT, false, 4 * V4_SIZE, 1 * V4_SIZE);
        glEnableVertexAttribArray(5);
        glVertexAttribPointer(5, 4, GL_FLOAT, false, 4 * V4_SIZE, 2 * V4_SIZE);
        glEnableVertexAttribArray(6);
        glVertexAttribPointer(6, 4, GL_FLOAT, false, 4 * V4_SIZE, 3 * V4_SIZE);

        glVertexAttribDivisor(3, 1);
        glVertexAttribDivisor(4, 1);
        glVertexAttribDivisor(5, 1);
        glVertexAttribDivisor(6, 1);
    }

    public void updateModelViewBuffer(List<Matrix4fc> matrices) {
        if (matrices.size() != instances) throw new RuntimeException(); // TODO: message

        val data = new float[V4_SIZE * instances];
        for (int i = 0; i < matrices.size(); i++) matrices.get(i).get(data, V4_SIZE * i);
        modelViewBuffer.update(data);
    }

    @Override public void draw(int drawMode) {
        GL31.glDrawElementsInstanced(
                drawMode,
                getSize(),
                GL_UNSIGNED_SHORT,
                0,
                instances);
    }

    @Override public void delete() {
        super.delete();
        modelViewBuffer.delete();
    }

}
