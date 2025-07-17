package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.renderer.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;

import java.io.BufferedReader;
import java.io.FileReader;
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

    public Camera getCamera() {
        return camera;
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
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentTypeAdapter())
                .registerTypeAdapter(GameObject.class, new GameObjectTypeAdapter())
                .create();

        try {
            FileWriter fw = new FileWriter("level.txt");
            fw.write(gson.toJson(gameObjects));
            fw.close();
            System.out.println(gson.toJson(gameObjects));
        } catch (IOException e) {
            e.printStackTrace(); // todo: make this better
        }
    }

    // todo: make whole system better
    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentTypeAdapter())
                .registerTypeAdapter(GameObject.class, new GameObjectTypeAdapter())
                .create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace(); // todo: better error message
        }

        if (!inFile.isEmpty()) {
            GameObject[] gameObjects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < gameObjects.length; i++) {
                addGameObjectToScene(gameObjects[i]);
            }
            this.isLevelLoaded = true;
        }
    }
}