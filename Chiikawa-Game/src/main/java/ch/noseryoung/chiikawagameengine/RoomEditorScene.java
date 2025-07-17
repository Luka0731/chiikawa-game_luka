package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.components.Sprite;
import ch.noseryoung.components.SpriteRenderer;
import ch.noseryoung.util.AssetPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class RoomEditorScene extends Scene {

    private SpriteRenderer obj2Sprite; // testing

    @Override
    public void init() {
        loadResources();

        // testing
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Object 1, red qube",
                new Transform(new Vector2f(20, 100), new Vector2f(220, 231)), 1);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        Sprite obj1Sprite = new Sprite();
        obj1Sprite.setTexture(AssetPool.getTexture("assets/images/BlowMeChiikawa.png"));
        obj1SpriteRenderer.setSprite(obj1Sprite);
        obj1.addComponent(obj1SpriteRenderer);
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2, green qube",
                new Transform(new Vector2f(220, 100), new Vector2f(100, 200)), 2);
        obj2Sprite = new SpriteRenderer();
        obj2.addComponent(obj2Sprite);
        obj2Sprite.setColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        this.addGameObjectToScene(obj2);

        this.activeGameObject = obj2;

//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .registerTypeAdapter(Component.class, new ComponentTypeAdapter())
//                .registerTypeAdapter(GameObject.class, new GameObjectTypeAdapter())
//                .create();
//        String serialized = gson.toJson(obj2Sprite);
//        System.out.println(serialized);
//
//        GameObject obj = gson.fromJson(serialized, GameObject.class);
//        System.out.println(obj);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        // System.out.println("FPS: " + (1.0f / dt)); // testing

        for (GameObject gameObject: this.gameObjects) {
            gameObject.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imGui() {
        // testing
        ImGui.begin("Test window");
        ImGui.text("Oi oi Baka sus");
        ImGui.end();
    }
}
