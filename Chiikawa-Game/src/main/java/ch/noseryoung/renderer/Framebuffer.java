package ch.noseryoung.renderer;

import static org.lwjgl.opengl.GL30C.*;

public class Framebuffer {
    private int fboId;
    private Texture texture;

    public Framebuffer(int width, int height) {
        // generate framebuffer
        fboId = glGenFramebuffers();

        // create the texture to render the data to, and attach it to our framebuffer
        texture = new Texture(width, height);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.getId(), 0);

        // create renderbuffer that stores the depth info
        int rboId = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboId);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);

        // attach to framebuffer
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboId);

        // check if everything went correctly
        assert glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE
                : "Error: Framebuffer is not complete!";
        glBindFramebuffer(GL_FRAMEBUFFER, 0); // basically unbinds everything, so you can see it on the window back again
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }


    // |--- getters & setters ---|

    public int getFboId() {
        return fboId;
    }

    public int getTextureId() {
        return texture.getId();
    }
}
