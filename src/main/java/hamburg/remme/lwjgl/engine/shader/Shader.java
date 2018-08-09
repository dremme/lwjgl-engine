package hamburg.remme.lwjgl.engine.shader;

import static lombok.AccessLevel.PACKAGE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import lombok.Getter;
import lombok.val;

abstract class Shader {

    @Getter(PACKAGE) private final int pointer;

    Shader(int type, String source) {
        pointer = glCreateShader(type);
        glShaderSource(pointer, source);
        glCompileShader(pointer);

        val infoLog = glGetShaderInfoLog(pointer, glGetShaderi(pointer, GL_INFO_LOG_LENGTH));
        if (glGetShaderi(pointer, GL_COMPILE_STATUS) == GL_FALSE) {
            delete();
            throw new RuntimeException("Error compiling shader:\n" + infoLog);
        }
    }

    public void delete() {
        glDeleteShader(pointer);
    }

}
