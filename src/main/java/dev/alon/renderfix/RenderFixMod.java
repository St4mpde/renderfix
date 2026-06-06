package dev.alon.renderfix;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.lwjgl.glfw.GLFW;

public class RenderFixMod implements ClientModInitializer {

    public static volatile boolean pendingRefresh = false;
    public static volatile boolean clientStarted = false;

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            clientStarted = true;
            long handle = client.getWindow().getHandle();
            GLFW.glfwSetWindowRefreshCallback(handle, w -> pendingRefresh = true);
        });
    }
}
