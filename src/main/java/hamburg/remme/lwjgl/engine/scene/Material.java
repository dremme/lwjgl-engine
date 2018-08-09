package hamburg.remme.lwjgl.engine.scene;

import hamburg.remme.lwjgl.engine.shader.ShaderProgram;
import hamburg.remme.lwjgl.engine.texture.Texture;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;

@Getter public class Material {

    private final Vector3f tint = new Vector3f(1);
    @Setter @NonNull private ShaderProgram shader;
    @Setter private Texture texture;

    public Material(ShaderProgram shader) {
        this(shader, null);
    }

    public Material(ShaderProgram shader, Texture texture) {
        this.shader = shader;
        this.texture = texture;
    }

    public boolean hasTexture() {
        return texture != null;
    }

}
