package hamburg.remme.lwjgl.engine;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;

import hamburg.remme.lwjgl.engine.geometry.Mesh;
import hamburg.remme.lwjgl.engine.shader.ShaderProgram;
import hamburg.remme.lwjgl.engine.texture.Texture;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Renderer {

    public static final int DRAW_POINTS = GL_POINTS;
    public static final int DRAW_LINES = GL_LINES;
    public static final int DRAW_TRIANGLES = GL_TRIANGLES;

    private final Set<GameObject> renderQueue = new HashSet<>();
    private final Matrix4f mvp = new Matrix4f();
    private ShaderProgram shader;
    private Texture texture;
    private Mesh mesh;
    @Getter private final Matrix4f camera = new Matrix4f();
    @Getter private final Vector3f clearColor = new Vector3f(0);
    @Getter @Setter private int drawMode = DRAW_TRIANGLES;

    Renderer() {
        createCapabilities();
    }

    public void enableTransparency() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void disableTransparency() {
        glDisable(GL_BLEND);
    }

    public void enableTextures() {
        glEnable(GL_TEXTURE_2D);
    }

    public void disableTextures() {
        glDisable(GL_TEXTURE_2D);
    }

    public void enableDepthTest() {
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS); // FIXME: use proper func; default?
    }

    public void disableDepthTest() {
        glDisable(GL_DEPTH_TEST);
    }

    void add(GameObject gameObject) {
        renderQueue.add(gameObject);
    }

    void remove(GameObject gameObject) {
        renderQueue.remove(gameObject);
    }

    void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
        renderQueue.forEach(this::render);
    }

    private void render(GameObject gameObject) {
        val material = gameObject.getModel().getMaterial();

        use(material.getShader());
        use(material.getTexture());

        shader.setUniform("world_matrix", calculateMvpMatrix(gameObject));
        shader.setUniform("tint", material.getTint());
        shader.setUniform("has_albedo", material.hasTexture());
        shader.setUniform("albedo_map", 0); // TODO: move to shader init?

        useAndDraw(gameObject.getModel().getMesh());
    }

    private Matrix4f calculateMvpMatrix(GameObject gameObject) {
        // TODO: can be optimized by creating a camera class
        return mvp.set(camera)
                // TODO: can be optimized by moving this to model and memorizing it
                .translate(gameObject.getPosition())
                .rotate(gameObject.getRotation())
                .scale(gameObject.getScale());
    }

    private void use(ShaderProgram shader) {
        if (this.shader != shader) {
            this.shader = shader;
            shader.use();
        }
    }

    private void use(Texture texture) {
        if (this.texture != texture) {
            this.texture = texture;
            if (texture == null) {
                Texture.free(0);
            } else {
                texture.bind(0); // TODO
            }
        }
    }

    private void useAndDraw(Mesh mesh) {
        if (this.mesh != mesh) {
            this.mesh = mesh;
            mesh.bind();
        }
        mesh.draw(drawMode);
    }

}
