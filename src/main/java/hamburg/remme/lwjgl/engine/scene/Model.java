package hamburg.remme.lwjgl.engine.scene;

import hamburg.remme.lwjgl.engine.geometry.Mesh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor @Getter public class Model {

    @NonNull private final Mesh mesh;
    @Setter @NonNull private Material material;

}
