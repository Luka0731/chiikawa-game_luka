package ch.noseryoung.chiikawagameengine;

import ch.noseryoung.components.Component;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.annotations.JsonAdapter;

import java.util.ArrayList;
import java.util.List;

@JsonAdapter(GameObjectTypeAdapter.class)
public class GameObject {
    private static int ID_COUNTER = 0;
    private int uid = -1;
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;

    // todo: make init methods
    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;
        this.uid = ID_COUNTER++; // todo: may cause problems in the future
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void imGui () {
        for (Component component : components) {
            component.imGui();
        }
    }


    // |--- component handling ---|

    public void addComponent(Component component) {
        component.generateId();
        components.add(component);
        component.gameObject = this;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) { // done like this, cause not to run into concurrent modification errors
            Component component = components.get(i);
            if (componentClass.isAssignableFrom(components.get(i).getClass())) {
                components.remove(i);
                return;
                // todo maby: check if there is multiple same components
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                try {
                    return componentClass.cast(component);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component" ;
                }
            }
        }
        return null;
    }


    // |--- getters & setters ---|

    public int getZIndex() {
        return zIndex;
    }

    public List<Component> getComponents() {
        return components;
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
