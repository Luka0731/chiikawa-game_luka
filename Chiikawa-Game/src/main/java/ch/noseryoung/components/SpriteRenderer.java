package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.Component;
import ch.noseryoung.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Vector2f texCoords;
    // (0, 1) Bottom Right
    // (0, 0) Bottom Left
    // (1, 1) Top Right
    // (1, 0) Top Left
    private Texture texture;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.texture = null;
    }

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {

    }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
                new Vector2f(1.0f, 1.0f), // todo: make more efficient
                new Vector2f(1.0f, 0.0f),
                new Vector2f(0.0f, 0.0f),
                new Vector2f(0.0f, 1.0f)
        };
        return texCoords;
    }


}