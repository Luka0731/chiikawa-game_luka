package ch.noseryoung.util;

public class Time {
    // static variable get initialized at the application start up, so this is the time when the progam started
    public static final long timeStarted = System.nanoTime();

    public static float getTimeSinceStart() {
        return (float)((System.nanoTime() - timeStarted) / 1E9);  // 1E9 instead of 1E-9
    }
}
