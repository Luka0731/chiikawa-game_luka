package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.components.Sprite;
import ch.noseryoung.components.SpriteRenderer;
import ch.noseryoung.util.AssetPool;
import org.joml.Vector2f;

public class RoomEditorScene extends Scene {

    public RoomEditorScene() {}

    @Override
    public void init() {
        loadResources();

        // testing
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj2 = new GameObject("Object 2, green qube",
                new Transform(new Vector2f(100, 100), new Vector2f(100, 200)), 2);
        obj2.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/blendImage2.png")
        )));
        this.addGameObjectToScene(obj2);

        GameObject obj1 = new GameObject("Object 1, red qube",
                new Transform(new Vector2f(20, 100), new Vector2f(100, 100)), 1);
        obj1.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/blendImage1.png")
        )));
        this.addGameObjectToScene(obj1);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        System.out.println("FPS: " + (1.0f / dt));

        for (GameObject gameObject: this.gameObjects) {
            gameObject.update(dt);
        }

        this.renderer.render();
    }
}
