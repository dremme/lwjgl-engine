package hamburg.remme.lwjgl.game;

import static java.lang.Math.random;
import static java.lang.Math.toRadians;

import hamburg.remme.lwjgl.engine.Application;
import hamburg.remme.lwjgl.engine.GameObject;
import hamburg.remme.lwjgl.engine.geometry.Quad;
import hamburg.remme.lwjgl.engine.scene.Material;
import hamburg.remme.lwjgl.engine.scene.Model;
import hamburg.remme.lwjgl.engine.shader.ShaderFactory;

import lombok.val;
import org.joml.Vector3f;

public final class ParticleGame extends Application<ParticleGame> {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Particle Game";

    public static void main(String[] args) {
        new ParticleGame().launch(WIDTH, HEIGHT, TITLE);
    }

    @Override protected ParticleGame getThis() {
        return this;
    }

    @Override protected void start() {
        // Init engine
        getWindow().setVSync(false);

        // Init mesh & shader
        val mesh = new Quad();
        val shader = ShaderFactory.create("/standard.vert.glsl", "/standard.frag.glsl");

        // Add some objects to render queue
        for (int i = 0; i < 10000; i++) {
            val m = new Material(shader);
            randomColor(m);

            val o = new GameObject<ParticleGame>(new Model(mesh, m));
            o.getBehaviors().add(new ParticleBehavior(0.98f, 1));
            o.getScale().set(1.5f, 1.5f, 1);
            positionObject(o);

            add(o);
        }

        positionCamera();
    }

    @Override protected void stop() {
        // do nothing
    }

    private void randomColor(Material material) {
        material.getTint().set(
                (float) random() * .5f + .5f,
                (float) random() * .5f + .5f,
                (float) random() * .5f + .5f);
    }

    private void positionObject(GameObject gameObject) {
        gameObject.getPosition().set(
                (float) random() * getWindow().getWidth(),
                (float) random() * getWindow().getHeight(),
                0);
        gameObject.getRotation().rotateZ((float) toRadians(random() * 90));
    }

    private void positionCamera() {
        getRenderer().getCamera()
//                .setPerspective(
//                        (float) toRadians(45),
//                        getWindow().getWidth() / (float) getWindow().getHeight(),
//                        0.1f,
//                        100f)
                .setOrtho(
                        0,
                        getWindow().getWidth(),
                        getWindow().getHeight(),
                        0,
                        .1f,
                        100f)
                .lookAt(
                        new Vector3f(0, 0, 4),
                        new Vector3f(0, 0, 0),
                        new Vector3f(0, 1, 0));
    }

}
