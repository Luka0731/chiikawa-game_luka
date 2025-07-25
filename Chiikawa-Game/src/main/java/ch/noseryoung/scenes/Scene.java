package ch.noseryoung.scenes;

import ch.noseryoung.chiikawagameengine.Camera;
import ch.noseryoung.components.Component;
import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.components.Sprite;
import ch.noseryoung.components.SpriteRenderer;
import ch.noseryoung.renderer.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject = null;
    protected boolean isLevelLoaded = false;

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        if (!isRunning) {
            gameObjects.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            gameObject.start();
            this.renderer.add(gameObject);
        }
    }

    public void updateImGuiInScene() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imGui();
            ImGui.end();
        }
        imGui();
    }


    // |--- serialization ---|
    // todo: make whole system better

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("level.txt");
            fw.write(gson.toJson(gameObjects));
            fw.close();
        } catch (IOException e) {
            assert false : "Failed to save game objects to level.txt: " + e.getMessage();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            assert false : "Failed to read from level.txt: " + e.getMessage();
        }
        if (!inFile.isEmpty()) {
            int maxGameObjectId = -1;
            int maxComponentId = -1;
            GameObject[] gameObjects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < gameObjects.length; i++) {
                addGameObjectToScene(gameObjects[i]);

                SpriteRenderer sr = gameObjects[i].getComponent(SpriteRenderer.class);
                if (sr != null) {
                    sr.setDirty();  // <<< Wichtig!
                }

                for (Component component : gameObjects[i].getComponents()) {
                    if(component.getUid() > maxComponentId) {
                        maxComponentId = component.getUid();
                    }
                }
                if(gameObjects[i].getUid() > maxGameObjectId) {
                    maxGameObjectId =  gameObjects[i].getUid();
                }
            }
            maxGameObjectId++;
            maxComponentId++;
            GameObject.init(maxGameObjectId);
            Component.init(maxComponentId);
            this.isLevelLoaded = true;
        }
    }


    // |--- empty methods to override ---|

    public void init() {}

    public abstract void update(float dt);

    public void imGui() {}


    // |--- getters & setters ---|

    public Camera getCamera() {
        return camera;
    }
}