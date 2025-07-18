package ch.noseryoung.components;

import ch.noseryoung.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {

    float width, height;
    private Texture texture = null;
    private Vector2f[] textureCoords = {
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 0.0f),
            new Vector2f(0.0f, 1.0f),
    };

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTextureCoords() {
        return textureCoords;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureId() {
        return texture == null ? -1 : texture.getId();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTextureCoords(Vector2f[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
