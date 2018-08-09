package hamburg.remme.lwjgl.engine.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

public class FragmentShader extends Shader {

    public FragmentShader(String source) {
        super(GL_FRAGMENT_SHADER, source);
    }

}
