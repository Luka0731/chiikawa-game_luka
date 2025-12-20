package cjsgameengine;

import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener mouseListener;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[9]; // list for all mouse buttons
    private boolean isDragging;

    public MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastY = 0.0;
        this.lastX = 0.0;
    }

    public static MouseListener getMouseListener() {
        if (MouseListener.mouseListener == null) {
            MouseListener.mouseListener = new MouseListener();
        }
        return MouseListener.mouseListener;
    }


    // |--- callbacks ---|

    public static void mousePosCallback(long window, double xPos, double yPos)
    {
        // saves the last mouse position first
        getMouseListener().lastX = getMouseListener().xPos;
        getMouseListener().lastY = getMouseListener().yPos;

        getMouseListener().xPos = xPos;
        getMouseListener().yPos = yPos;

        // this callback will be get executed when the mouse is moving,
        // and if your holding down your mouse, it sets isDragging on true
        getMouseListener().isDragging = getMouseListener().mouseButtonPressed[0]
                || getMouseListener().mouseButtonPressed[1] || getMouseListener().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods)
    {
        if (action == GLFW_PRESS) {
            if (button < getMouseListener().mouseButtonPressed.length) {
                getMouseListener().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < getMouseListener().mouseButtonPressed.length) {
                getMouseListener().mouseButtonPressed[button] = false;
                getMouseListener().isDragging = false;
            }
        }
    }

    public static void scrollCallback(long window, double xOffset, double yOffset)
    {
        getMouseListener().scrollX = xOffset;
        getMouseListener().scrollY = yOffset;
    }

    public static void endFrame() {
        getMouseListener().scrollX = 0;
        getMouseListener().scrollY = 0;
        getMouseListener().lastX = getMouseListener().xPos;
        getMouseListener().lastY = getMouseListener().yPos;
    }


    // |--- getters & setters ---|

    public static float getX() {
        return (float)getMouseListener().xPos;
    }

    public static float getY() {
        return (float)getMouseListener().yPos;
    }

    public static float getOrthoX() {
        float currentX = getX();
        currentX = (currentX / (float)Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Window.getCurrentScene().getCamera().getInverseProjectionMatrix()).mul(Window.getCurrentScene().getCamera().getInverseViewMatrix());
        currentX = tmp.x;
        return currentX;
    }

    public static float getOrthoY() {
        float currentY = Window.getHeight() - getY();
        currentY = (currentY / (float)Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        tmp.mul(Window.getCurrentScene().getCamera().getInverseProjectionMatrix()).mul(Window.getCurrentScene().getCamera().getInverseViewMatrix()) ;
        currentY = tmp.y;
        return currentY;
    }

    public static float getDX() {
        return (float)(getMouseListener().lastX - getMouseListener().xPos);
    }

    public static float getDY() {
        return (float)(getMouseListener().lastY - getMouseListener().yPos);
    }

    public static float getScrollX() {
        return (float)getMouseListener().scrollX;
    }

    public static float getScrollY() {
        return (float)getMouseListener().scrollY;
    }

    public static boolean isDragging() {
        return getMouseListener().isDragging;
    }

    public static boolean isMouseButtonDown(int button) {
        if (button < getMouseListener().mouseButtonPressed.length) {
            return getMouseListener().mouseButtonPressed[button];
        }
        System.err.println("MouseListener ERROR: Button index " + button + " is out of bounds!");
        return false;
    }
}