package hamburg.remme.lwjgl.engine.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public abstract class Texture {

    private final int pointer;
    private final int target;

    public static void free(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, 0); // FIXME: is GL_TEXTURE_2D always valid?
    }

    public Texture(int target) {
        this.target = target;
        pointer = glGenTextures();
        glBindTexture(target, pointer);
    }

    public void bind(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(target, pointer);
    }

    public void delete() {
        glDeleteTextures(pointer);
    }

}
