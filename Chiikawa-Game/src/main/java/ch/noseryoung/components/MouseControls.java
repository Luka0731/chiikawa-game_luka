package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.chiikawagameengine.MouseListener;
import ch.noseryoung.chiikawagameengine.Window;
import ch.noseryoung.util.Settings;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * This component makes it, so you can drag GameObjects around with the mouse.
 */
public class MouseControls extends Component {
    GameObject holdingObject = null;

    public void pickupObject(GameObject gameObject) {
        this.holdingObject = gameObject;
        Window.getCurrentScene().addGameObjectToScene(gameObject);
    }

    public void place() {
        this.holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if (holdingObject != null) {
            holdingObject.transform.position.x = MouseListener.getOrthoX();
            holdingObject.transform.position.y = MouseListener.getOrthoY();

            // todo: solve this more elegantly (rn there is a problem, where if you're dragging the block in the negative coordinates area, the object is slightly offset)
            if (holdingObject.transform.position.x < 0) {
                holdingObject.transform.position.x -= Settings.Grid.WIDTH;
            }
            if (holdingObject.transform.position.y < 0) {
                holdingObject.transform.position.y -= Settings.Grid.HEIGHT;
            }

            holdingObject.transform.position.x = (int)(holdingObject.transform.position.x / Settings.Grid.WIDTH) * Settings.Grid.WIDTH;
            holdingObject.transform.position.y = (int)(holdingObject.transform.position.y / Settings.Grid.HEIGHT) * Settings.Grid.HEIGHT;

            if (MouseListener.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}