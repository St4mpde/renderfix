package dev.alon.renderfix.mixin;

import dev.alon.renderfix.RenderFixMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Unique private boolean renderFix$prevFocused = true;

    @Inject(method = "onWindowFocusChanged", at = @At("TAIL"))
    private void renderFix$onFocus(boolean focused, CallbackInfo ci) {
        if (focused && RenderFixMod.clientStarted) {
            RenderFixMod.pendingRefresh = true;
        }
    }

    @Inject(method = "render(Z)V", at = @At("HEAD"))
    private void renderFix$renderHead(boolean tick, CallbackInfo ci) {
        MinecraftClient mc = (MinecraftClient) (Object) this;
        Window window = mc.getWindow();

        boolean focused = GLFW.glfwGetWindowAttrib(window.getHandle(), GLFW.GLFW_FOCUSED) != 0;
        if (focused && !renderFix$prevFocused && RenderFixMod.clientStarted) {
            RenderFixMod.pendingRefresh = true;
        }
        renderFix$prevFocused = focused;

        if (!RenderFixMod.pendingRefresh) return;
        RenderFixMod.pendingRefresh = false;

        Framebuffer fb = mc.getFramebuffer();
        int w = window.getFramebufferWidth();
        int h = window.getFramebufferHeight();
        if (fb != null && w > 0 && h > 0) {
            fb.resize(w, h);
        }
    }
}
