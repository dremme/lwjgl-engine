package hamburg.remme.lwjgl.engine.shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class VertexShader extends Shader {

    public VertexShader(String source) {
        super(GL_VERTEX_SHADER, source);
    }

}
