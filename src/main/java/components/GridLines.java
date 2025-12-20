package components;

import cjsgameengine.Window;
import renderer.DebugDraw;
import util.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * This component creates a Grind over the whole screen.
 */
public class GridLines extends Component {

    @Override
    public void update(float dt) {
        Vector2f cameraPos = Window.getCurrentScene().getCamera().position;
        Vector2f projectionSize = Window.getCurrentScene().getCamera().getProjectionSize();

        int firstX = ((int)(cameraPos.x / Settings.Grid.WIDTH) - 1) * Settings.Grid.HEIGHT;
        int firstY = ((int)(cameraPos.y / Settings.Grid.HEIGHT) - 1) * Settings.Grid.HEIGHT;

        int numVtLines = (int)(projectionSize.x / Settings.Grid.WIDTH) + 2;
        int numHzLines = (int)(projectionSize.y / Settings.Grid.HEIGHT) + 2;

        int height = (int)projectionSize.y + Settings.Grid.HEIGHT * 2;
        int width = (int)projectionSize.x + Settings.Grid.WIDTH * 2;

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i=0; i < maxLines; i++) {
            int x = firstX + (Settings.Grid.WIDTH * i);
            int y = firstY + (Settings.Grid.HEIGHT * i);

            if (i < numVtLines) {
                DebugDraw.add2DLine(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }

            if (i < numHzLines) {
                DebugDraw.add2DLine(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}