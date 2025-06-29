package ch.noseryoung.chiikawagameengine;

import java.awt.*;

public abstract class Component {

    public GameObject gameObject;

    public abstract void update(float dt);

    public void start() {}
}
