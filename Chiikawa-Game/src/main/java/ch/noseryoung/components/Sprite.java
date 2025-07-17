package ch.noseryoung.components;

import ch.noseryoung.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture = null;
    private Vector2f[] textureCoords = {
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 0.0f),
            new Vector2f(0.0f, 1.0f),
    };

//    public Sprite(Texture texture) {
//        this.texture = texture;
//        Vector2f[] textureCoords = {
//                new Vector2f(1.0f, 1.0f), // todo: make more efficient
//                new Vector2f(1.0f, 0.0f),
//                new Vector2f(0.0f, 0.0f),
//                new Vector2f(0.0f, 1.0f)
//        };
//        this.textureCoords = textureCoords;
//    }
//
//    public Sprite(Texture texture, Vector2f[] textureCoords) {
//        this.texture = texture;
//        this.textureCoords = textureCoords;
//    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTextureCoords() {
        return textureCoords;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTextureCoords(Vector2f[] textureCoords) {
        this.textureCoords = textureCoords;
    }
}
