package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.Component;
import ch.noseryoung.chiikawagameengine.GameObject;
import ch.noseryoung.chiikawagameengine.MouseListener;
import ch.noseryoung.chiikawagameengine.Window;

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
            holdingObject.transform.position.x = MouseListener.getOrthoX() - 16;
            holdingObject.transform.position.y = MouseListener.getOrthoY() - 16;

            if (MouseListener.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }

}
