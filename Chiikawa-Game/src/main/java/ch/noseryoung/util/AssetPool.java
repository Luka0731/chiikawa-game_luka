package ch.noseryoung.util;

import ch.noseryoung.components.Spritesheet;
import ch.noseryoung.renderer.Shader;
import ch.noseryoung.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader addOrGetShader(String resourceName) {
        File file = new File(resourceName);
        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        }
        Shader shader = new Shader(resourceName);
        shader.compileAndLinkShader();
        AssetPool.shaders.put(file.getAbsolutePath(), shader);
        return shader;
    }

    public static Texture addOrGetTexture(String resourceName) {
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
            spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet (String resourceName) {
        File file = new File(resourceName);
        assert spritesheets.containsKey(file.getAbsolutePath()) : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
        return spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}