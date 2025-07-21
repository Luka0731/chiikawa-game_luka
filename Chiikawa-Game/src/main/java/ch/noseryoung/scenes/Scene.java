package ch.noseryoung.scenes;

import ch.noseryoung.chiikawagameengine.Camera;
import ch.noseryoung.chiikawagameengine.Component;
import ch.noseryoung.chiikawagameengine.GameObject;
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
    protected List<GameObject> gameObjects = new ArrayList<GameObject>();
    protected GameObject activeGameObject = null;
    protected boolean isLevelLoaded = false;

    public Scene() {}

    public void init() {}

    public abstract void update(float dt);

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

    public void sceneImGui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imGui();
            ImGui.end();
        }
        imGui();
    }

    public void imGui() {}

    // todo: make whole system better
    public void saveExit() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("level.txt");
            fw.write(gson.toJson(gameObjects));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace(); // todo: make this better
        }
    }

    // todo: make whole system better
    // todo: fix multiple loading
    public void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace(); // todo: better error message
        }

        if (!inFile.isEmpty()) {
            int maxGameObjectId = -1;
            int maxComponentId = -1;
            GameObject[] gameObjects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < gameObjects.length; i++) {
                addGameObjectToScene(gameObjects[i]);

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

    public Camera getCamera() {
        return camera;
    }
}