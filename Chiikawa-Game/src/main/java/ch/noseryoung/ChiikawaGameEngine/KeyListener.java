package ch.noseryoung.ChiikawaGameEngine;

import org.lwjgl.glfw.GLFW;

public class KeyListener {
    private static boolean isKeyPressed;
    private static KeyListener keyListener;
    private boolean KeyPressed[] = new boolean[350]; // amount of keybindings GLFW

    public KeyListener() {}

    public static KeyListener getKeyListener() {
        if (KeyListener.keyListener == null) {
            KeyListener.keyListener = new KeyListener();
        }
        return KeyListener.keyListener;
    }


    // |--- callbacks ---|

    // todo: what if a key is pressed outside the 350 pressed range range
    public static void keyCallback(long window, int key, int scancode, int action, int mods)
    {
        if (action == GLFW.GLFW_PRESS) {
            getKeyListener().KeyPressed[key] = true;
        } else if (action == GLFW.GLFW_RELEASE) {
            getKeyListener().KeyPressed[key] = false;
        }
    }


    // |--- getters ---|

    public static boolean isKeyPressed (int button) {
        if (button < getKeyListener().KeyPressed.length) {
            return getKeyListener().KeyPressed[button];
        }
        System.err.println("KeyListener ERROR: Button index " + button + " is out of bounds!");
        return false;
    }

}
