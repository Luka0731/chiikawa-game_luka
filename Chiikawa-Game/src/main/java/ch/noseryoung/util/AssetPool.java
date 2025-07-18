package ch.noseryoung.util;

import ch.noseryoung.components.Spritesheet;
import ch.noseryoung.renderer.Shader;
import ch.noseryoung.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

// todo: improve paths
// todo: also can make it so it throws error if not exists
// todo: small rework needed, cause there is a bug with the ids (episode 22)
public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        }
        Shader shader = new Shader(resourceName);
        shader.compileAndLinkShader();
        AssetPool.shaders.put(file.getAbsolutePath(), shader);
        return shader;
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        }
        Texture texture = new Texture();
        texture.init(resourceName);
        AssetPool.textures.put(file.getAbsolutePath(), texture);
        return texture;
    }

    public static void addSpritesheet (String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);
        if (!spritesheets.containsKey(file.getAbsolutePath())) {
            spritesheets.put(file.getAbsolutePath(), spritesheet); // todo: maybe fix, tutorial cinda sus
        }
    }

    public static Spritesheet getSpritesheet (String resourceName) {
        File file = new File(resourceName);
        if (!spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
        }
        return spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}

