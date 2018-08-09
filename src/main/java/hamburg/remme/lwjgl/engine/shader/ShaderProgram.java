package hamburg.remme.lwjgl.engine.shader;

import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import lombok.val;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;

public class ShaderProgram {

    private static final FloatBuffer M4_BUFFER = createFloatBuffer(16);

    private final Map<String, Integer> uniformLocationCache = new HashMap<>();
    // TODO: maybe a set cache with hash codes is good enough and faster
    private final Map<String, Integer> uniformValueCache = new HashMap<>();
    private final int pointer;
    private final VertexShader vertexShader;
    private final FragmentShader fragmentShader;

    public static void halt() {
        glUseProgram(0);
    }

    public ShaderProgram(VertexShader vertexShader, FragmentShader fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        pointer = glCreateProgram();
        glAttachShader(pointer, vertexShader.getPointer());
        glAttachShader(pointer, fragmentShader.getPointer());
        glLinkProgram(pointer);

        val infoLog = glGetProgramInfoLog(pointer, glGetProgrami(pointer, GL_INFO_LOG_LENGTH));
        if (glGetProgrami(pointer, GL_LINK_STATUS) == GL_FALSE) {
            delete();
            throw new RuntimeException("Error linking program:\n" + infoLog);
        }
    }

    public void use() {
        glUseProgram(pointer);
    }

    public void setUniform(String uniform, boolean value) {
        setUniform(uniform, value ? 1 : 0);
    }

    // TODO: maybe use uniform objects
    public void setUniform(String uniform, int value) {
        if (invalidateUniformCache(uniform, value)) {
            glUniform1i(getUniformLocation(uniform), value);
        }
    }

    // TODO: maybe use uniform objects
    public void setUniform(String uniform, Vector3fc value) {
        if (invalidateUniformCache(uniform, value)) {
            glUniform3f(getUniformLocation(uniform), value.x(), value.y(), value.z());
        }
    }

    // TODO: maybe use uniform objects
    public void setUniform(String uniform, Matrix4fc value) {
        if (invalidateUniformCache(uniform, value)) {
            // TODO: is FloatBuffer faster than array?
            glUniformMatrix4fv(getUniformLocation(uniform), false, value.get(M4_BUFFER));
        }
    }

    public void delete() {
        glDetachShader(pointer, vertexShader.getPointer());
        glDetachShader(pointer, fragmentShader.getPointer());
        glDeleteProgram(pointer);
        vertexShader.delete();
        fragmentShader.delete();
    }

    private boolean invalidateUniformCache(String uniform, int value) {
        if (!uniformValueCache.containsKey(uniform)) return true;
        if (value == uniformValueCache.get(uniform)) return false;
        uniformValueCache.put(uniform, value);
        return true;
    }

    private boolean invalidateUniformCache(String uniform, Object value) {
        if (!uniformValueCache.containsKey(uniform)) return true;
        if (value.hashCode() == uniformValueCache.get(uniform).hashCode()) return false;
        uniformValueCache.put(uniform, value.hashCode());
        return true;
    }

    private int getUniformLocation(String uniform) {
        var location = uniformLocationCache.get(uniform);
        if (location != null) return location;
        location = glGetUniformLocation(pointer, uniform);
        uniformLocationCache.put(uniform, location);
        return location;
    }

}
