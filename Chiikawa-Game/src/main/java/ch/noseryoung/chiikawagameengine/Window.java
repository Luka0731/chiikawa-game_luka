package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.renderer.DebugDraw;
import ch.noseryoung.scenes.RoomEditorScene;
import ch.noseryoung.scenes.RoomScene;
import ch.noseryoung.scenes.Scene;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    int width, height;
    String title;
    private static Window window = null; // singleton
    private long glfwWindow; // the memory space location of the window (pointer)
    private ImGuiLayer imGuiLayer;
    private static Scene currentScene = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Chiikawa Game!";
    }

    // todo: not making this with int, but passing trought the actual scene
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new RoomEditorScene();
                break;
            case 1:
                currentScene = new RoomScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'.";
        }
        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    public static Window getWindow() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static Scene getCurrentScene() {
        return getWindow().currentScene;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!!");

        init();
        loop();

        // free memory space
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set(); // sets up an error callback

        // initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);   // make the window not visible until it's done
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);  // make it resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);  // when the window starts, it's in the maximized position

        // create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // lambda expressions, so GLFW can implement its code here
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::scrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) ->  {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // make OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync (refresh rate as fast as the monitor, not waiting)
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        /*
        * This line is critical for LWJGL's interoperation with GLFW's
        * OpenGL context, or any context that is managed externally.
        * LWJGL detects the context that is current in the current thread,
        * creates the GLCapabilities instance and makes the OpenGL
        * bindings available for use.
        */
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        Window.changeScene(0); // starting scene
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events (input events)
            glfwPollEvents();

            DebugDraw.beginFrame();

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT); // clears the color with the color that was made one line up

            if(dt >= 0) {
                DebugDraw.draw();
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow); // swaps the front and back buffer (frame update)

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        currentScene.saveExit();
    }

    public static int getWidth() {
        return getWindow().width;
    }

    public static int getHeight() {
        return getWindow().height;
    }

    public static void setWidth(int newWidth) {
        getWindow().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        getWindow().height = newHeight;
    }
}