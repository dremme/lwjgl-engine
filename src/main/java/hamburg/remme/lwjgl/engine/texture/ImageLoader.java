package hamburg.remme.lwjgl.engine.texture;

import static java.lang.Math.round;
import static java.nio.file.Files.readAllBytes;
import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryStack.stackPush;

import hamburg.remme.lwjgl.engine.shader.ShaderFactory;

import java.nio.ByteBuffer;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass public class ImageLoader {

    public Image load(String imageFile) {
        val raw = readFile(imageFile);
        try (val stack = stackPush()) {
            val pWidth = stack.mallocInt(1);
            val pHeight = stack.mallocInt(1);
            val pComp = stack.mallocInt(1);

            stbi_info_from_memory(raw, pWidth, pHeight, pComp);
            val data = stbi_load_from_memory(raw, pWidth, pHeight, pComp, 0);

            // Pre-multiply alpha values for correctness if there is an alpha channel
            if (pComp.get() == 4) premultiplyAlpha(data, pWidth.get(0), pHeight.get(0));

            return new Image(pWidth.get(0), pHeight.get(0), pComp.get(0), data);
        }
    }

    @SneakyThrows private ByteBuffer readFile(String file) {
        val uri = ImageLoader.class.getResource(file).toURI();
        val bytes = readAllBytes(Paths.get(uri));
        val buffer = createByteBuffer(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    private void premultiplyAlpha(ByteBuffer data, int w, int h) {
        val stride = w * 4;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                val i = y * stride + x * 4;
                val alpha = (data.get(i + 3) & 0xFF) / 255.0f;
                data.put(i + 0, (byte) round(((data.get(i + 0) & 0xFF) * alpha)));
                data.put(i + 1, (byte) round(((data.get(i + 1) & 0xFF) * alpha)));
                data.put(i + 2, (byte) round(((data.get(i + 2) & 0xFF) * alpha)));
            }
        }
    }

}
