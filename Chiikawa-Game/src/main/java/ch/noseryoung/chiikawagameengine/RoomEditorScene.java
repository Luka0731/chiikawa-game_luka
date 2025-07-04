package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.components.SpriteRenderer;
import ch.noseryoung.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class RoomEditorScene extends Scene {

    public RoomEditorScene() {}

    @Override
    public void init() {
        // testing
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Object 1, Blow Me Twin",
                new Transform(new Vector2f(100, 100), new Vector2f(220, 231)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/BlowMeChiikawa.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2, Chiikawa Gang",
                new Transform(new Vector2f(400, 100), new Vector2f(600, 315)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/ChiikawaGang.png")));
        this.addGameObjectToScene(obj2);

        GameObject obj3 = new GameObject("Object 3, Blow Me Twin",
                new Transform(new Vector2f(100, 400), new Vector2f(220, 231)));
        obj3.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/BlowMeChiikawa.png")));
        this.addGameObjectToScene(obj3);

        loadResources();
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
