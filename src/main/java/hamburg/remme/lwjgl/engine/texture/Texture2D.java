package hamburg.remme.lwjgl.engine.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture2D extends Texture {

    public Texture2D(Image image) {
        super(GL_TEXTURE_2D);

        final int format;
        if (image.getChannels() == 3) {
            // TODO: simplify?
            if ((image.getWidth() & 3) != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (image.getWidth() & 1));
            format = GL_RGB;
        } else {
            format = GL_RGBA;
        }
        glTexImage2D(GL_TEXTURE_2D, 0, format, image.getWidth(), image.getHeight(), 0, format, GL_UNSIGNED_BYTE, image.getData());

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

}
