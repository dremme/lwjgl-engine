package hamburg.remme.lwjgl.game;

import static hamburg.remme.lwjgl.engine.Window.BUTTON_LEFT;
import static hamburg.remme.lwjgl.engine.Window.BUTTON_RIGHT;

import hamburg.remme.lwjgl.engine.Behavior;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.joml.Vector3f;

@RequiredArgsConstructor public class ParticleBehavior extends Behavior<ParticleGame> {

    private final Vector3f velocity = new Vector3f();
    private final Vector3f force = new Vector3f();
    private final float drag;
    private final float acceleration;

    @Override public void update() {
        val position = getGameObject().getPosition();
        val mousePosition = getWindow().getMousePosition();
        val leftButton = getWindow().getButton(BUTTON_LEFT);
        val rightButton = getWindow().getButton(BUTTON_RIGHT);

        if (leftButton || rightButton) {
            val dirX = mousePosition.x() - position.x;
            val dirY = mousePosition.y() - position.y;
            force.set(dirX, dirY, 0).normalize().mul(acceleration);
            velocity.add(rightButton ? force.negate() : force);
        }

        position.add(velocity); // acceleration
        velocity.mul(drag); // drag

        keepInsideBounds();
    }

    private void keepInsideBounds() {
        val position = getGameObject().getPosition();
        val width = getWindow().getWidth();
        val height = getWindow().getHeight();
        if (position.x < 0 && velocity.x < 0 || position.x > width && velocity.x > 0) velocity.x = -velocity.x;
        if (position.y < 0 && velocity.y < 0 || position.y > height && velocity.y > 0) velocity.y = -velocity.y;
    }

}
