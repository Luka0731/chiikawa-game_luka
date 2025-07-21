package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.Component;
import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.chiikawagameengine.MouseListener;
import ch.noseryoung.chiikawagameengine.Window;
import ch.noseryoung.util.Settings;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

// not an actual class, more so for organization
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
            holdingObject.transform.position.x = (int)(holdingObject.transform.position.x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH;
            holdingObject.transform.position.y = (int)(holdingObject.transform.position.y / Settings.GRID_HEIGHT) * Settings.GRID_HEIGHT;

            if (MouseListener.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }

}
