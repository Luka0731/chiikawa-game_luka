package renderer;

import cjsgameengine.GameObject;
import components.SpriteRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Renderer class is responsible for managing and coordinating the rendering process.
 * It organizes game objects into render batches and ensures they are drawn efficiently.
 *
 * This class reduces the number of draw calls by grouping objects with similar properties,
 * such as shared textures or z-index, into batches (aka RenderBatch). It then delegates
 * the actual rendering to each corresponding RenderBatch.
 */
public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private final List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        SpriteRenderer spr = gameObject.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    // todo: make so batch renderer is not restricted to one zIndex
    private void add(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.getZIndex() == spriteRenderer.gameObject.getZIndex()) {
                Texture texture = spriteRenderer.getTexture();
                if (batch.hasTexture(texture) || batch.hasTextureRoom() || texture == null) {
                    batch.addSprite(spriteRenderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, spriteRenderer.gameObject.getZIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
            Collections.sort(batches);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}