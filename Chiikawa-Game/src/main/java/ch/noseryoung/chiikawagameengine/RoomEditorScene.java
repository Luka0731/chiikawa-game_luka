package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.components.Sprite;
import ch.noseryoung.components.SpriteRenderer;
import ch.noseryoung.components.Spritesheet;
import ch.noseryoung.util.AssetPool;
import org.joml.Vector2f;

public class RoomEditorScene extends Scene {

    // testing
    private GameObject obj1;
    private Spritesheet sprites;

    public RoomEditorScene() {}

    @Override
    public void init() {
        loadResources();

        // testing
        this.camera = new Camera(new Vector2f(-250, 0));

         sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1, Mario from a Spritesheet",
                new Transform(new Vector2f(-240, 10), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(17)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2, Chiikawa Gang",
                new Transform(new Vector2f(100, 400), new Vector2f(600, 315)));
        obj2.addComponent(new SpriteRenderer(
                new Sprite(AssetPool.getTexture("assets/images/ChiikawaGang.png"))));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                16, 16, 26, 0));

    }

    // testing
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {

        // testing
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
             spriteFlipTimeLeft = spriteFlipTime;
             spriteIndex++;
             if (spriteIndex > 8) {
                 spriteIndex = 0;
             }
             obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        System.out.println("FPS: " + (1.0f / dt));

        for (GameObject gameObject: this.gameObjects) {
            gameObject.update(dt);
        }

        this.renderer.render();
    }
}
