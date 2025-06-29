package ch.noseryoung.components;

import ch.noseryoung.chiikawagameengine.Component;

public class FontRenderer extends Component {
    public FontRenderer() {
        gameObject = null;
    }

    @Override
    public void start() {
        if (gameObject.getComponents(SpriteRenderer.class) != null) {
            System.out.println("Found Font Renderer");
        }
    }

    @Override
    public void update(float dt) {

    }
}
