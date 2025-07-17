package ch.noseryoung.chiikawagameengine;

import java.awt.*;

public abstract class Component {

    public transient GameObject gameObject = null;

    public void start() {}

    public void update(float dt) {};

    public void imGui() {}
}
