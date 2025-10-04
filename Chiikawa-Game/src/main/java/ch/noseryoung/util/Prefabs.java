package ch.noseryoung.util;

import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.chiikawagameengine.Transform;
import ch.noseryoung.components.Sprite;
import ch.noseryoung.components.SpriteRenderer;
import org.joml.Vector2f;

/**
 * This class has some useful methods, to cut some often used code in
 * other classes.
 */
public class Prefabs {

    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject block = new GameObject("Sprite_Object_Gen",
                new Transform(new Vector2f(), new Vector2f(sizeX, sizeY)), 0);
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSprite(sprite);
        block.addComponent(spriteRenderer);

        return block;
    }
}