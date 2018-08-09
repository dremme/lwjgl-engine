package hamburg.remme.lwjgl.engine;

import hamburg.remme.lwjgl.engine.scene.Model;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@NoArgsConstructor @AllArgsConstructor @Getter public class GameObject<T extends Application<T>> {

    private final Set<Behavior<T>> behaviors = new HashSet<>();
    private final Vector3f position = new Vector3f();
    private final Vector3f scale = new Vector3f(1);
    private final Quaternionf rotation = new Quaternionf();
    private Model model;

    public boolean hasModel() {
        return model != null;
    }

}
