package ch.noseryoung.components;

import ch.noseryoung.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

// todo: make it more flexible of reading in spritesheets (as example spritesheet with inconsistent spacings between the sprites)
public class Spritesheet {
    private Texture texture;
    private final List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();

        this.texture = texture;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for(int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float)texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float)texture.getWidth();
            float leftX = currentX / (float)texture.getWidth();
            float bottomY = currentY / (float)texture.getHeight();


            Vector2f[] textureCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };
            Sprite sprite = new Sprite();
            sprite.init(spriteWidth, spriteHeight, this.texture, textureCoords);
            sprites.add(sprite);

            currentX += spriteWidth;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }


    // |--- getters & setters ---|

    public Sprite getSprite(int spriteIndex) {
        return this.sprites.get(spriteIndex);
    }

    public int getSize() {
        return sprites.size();
    }
}