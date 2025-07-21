package ch.noseryoung.scenes;

import ch.noseryoung.chiikawagameengine.Camera;
import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.chiikawagameengine.Transform;
import ch.noseryoung.components.*;
import ch.noseryoung.renderer.DebugDraw;
import ch.noseryoung.util.AssetPool;
import ch.noseryoung.util.Prefabs;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RoomEditorScene extends Scene {

    private Spritesheet sprites;

    MouseControls mouseControls = new MouseControls();

    @Override
    public void init() {
        loadResources();

        // testing
        this.camera = new Camera(new Vector2f(-250, -50));
        sprites = AssetPool.getSpritesheet("assets/images/decorationsAndBlocks.png");
        if (isLevelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            DebugDraw.addLine2D(new Vector2f(-130, 0), new Vector2f(-100, 600), new Vector3f(1, 0, 0), 800);
            return;
        }

        GameObject obj1 = new GameObject("Object 1, object with image",
                new Transform(new Vector2f(20, 100), new Vector2f(300, 157)), 0);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        Sprite obj1Sprite = new Sprite();
        obj1Sprite.setTexture(AssetPool.getTexture("assets/images/ChiikawaGang.png"));
        obj1SpriteRenderer.setSprite(obj1Sprite);
        obj1.addComponent(obj1SpriteRenderer);
        obj1.addComponent(new Rigidbody());
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2, cube",
                new Transform(new Vector2f(300, 100), new Vector2f(100, 200)), 1);
        SpriteRenderer obj2Sprite = new SpriteRenderer();
        obj2.addComponent(obj2Sprite);
        obj2Sprite.setColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        this.addGameObjectToScene(obj2);

        this.activeGameObject = obj1;
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.getTexture("assets/images/ChiikawaGang.png");
    }

    @Override
    public void update(float dt) {
        mouseControls.update(dt);

        for (GameObject gameObject: this.gameObjects) {
            gameObject.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imGui() {
        // testing
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowsX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.getSize(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTextureId();
            Vector2f[] textureCoords = sprite.getTextureCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, textureCoords[2].x, textureCoords[0].y, textureCoords[0].x, textureCoords[2].y)) {
                GameObject gameObject = Prefabs.generateSpriteObject(sprite, spriteWidth, spriteHeight);
                mouseControls.pickupObject(gameObject);
            }
            ImGui.popID();

            ImVec2 lastButtonPosition = new ImVec2();
            ImGui.getItemRectMax(lastButtonPosition);
            float lastButtonX2 = lastButtonPosition.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.getSize() && nextButtonX2 < windowsX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
