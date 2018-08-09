package hamburg.remme.lwjgl.engine.shader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;

import java.nio.file.Paths;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass public class ShaderFactory {

    public ShaderProgram create(String vertexFile, String fragmentFile) {
        return new ShaderProgram(
                new VertexShader(readFile(vertexFile)),
                new FragmentShader(readFile(fragmentFile)));
    }

    @SneakyThrows private String readFile(String file) {
        val uri = ShaderFactory.class.getResource(file).toURI();
        return new String(readAllBytes(Paths.get(uri)), UTF_8);
    }

}
