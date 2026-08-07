package com.contour.client.mixin;

import com.contour.client.ContourConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class ContourLevelRendererMixin {

    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void setOutlineColor(CallbackInfo ci) {
        if (!ContourConfig.enabled) return;

        Minecraft mc = Minecraft.getInstance();

        mc.renderBuffers()
                .outlineBufferSource()
                .setColor(ContourConfig.getOutlineColor());
    }
}