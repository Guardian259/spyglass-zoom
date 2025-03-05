package net.cobrasrock.spyzoom.mixin;

import net.cobrasrock.spyzoom.SpyZoom;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    //zooms in screen
    @Inject(at = @At("HEAD"), method = "tickFov", cancellable = true)
    private void updateFovMultiplier(CallbackInfo info) {
        if(SpyZoom.instance.player != null) {
            if (SpyZoom.instance.player.isScoping() && SpyZoom.instance.options.getCameraType().isFirstPerson()) {

                setLastFovMultiplier(getFovMultiplier());
                setFovMultiplier(getFovMultiplier() + (SpyZoom.zoom - getFovMultiplier()) * 0.5F);

                info.cancel();
            }
        }
    }

    @Accessor(value = "fovModifier")
    public abstract void setFovMultiplier(float fov);

    @Accessor(value = "fovModifier")
    public abstract float getFovMultiplier();

    @Accessor(value = "oldFovModifier")
    public abstract void setLastFovMultiplier(float fov);
}
