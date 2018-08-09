package hamburg.remme.lwjgl.engine.texture;

import java.nio.ByteBuffer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter public class Image {

    private final int width;
    private final int height;
    private final int channels;
    private final ByteBuffer data;

}
