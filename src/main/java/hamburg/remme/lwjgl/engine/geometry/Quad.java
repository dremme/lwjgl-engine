package hamburg.remme.lwjgl.engine.geometry;

public class Quad extends Mesh {

    private static final float[] DATA = {
            -1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f
    };
    private static final short[] INDICES = {
            0, 1, 2, 2, 3, 0 // FIXME: optimize index order, "k-sorting"
    };

    public Quad() {
        super(DATA, INDICES);
    }

}
