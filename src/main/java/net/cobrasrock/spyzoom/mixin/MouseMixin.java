package net.cobrasrock.spyzoom.mixin;

import net.cobrasrock.spyzoom.SpyZoom;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseMixin {
    //lowers mouse sensitivity
    @Inject(at = @At("HEAD"), method = "grabMouse", cancellable = true)
    private void updateMouse(CallbackInfo info) {
        LocalPlayer player = SpyZoom.instance.player;
        Options options = SpyZoom.instance.options;
        if (player == null)
            return;
        if (player.isScoping() && options.getCameraType().isFirstPerson()) {

            double f = options.mouseWheelSensitivity().get() * 0.6000000238418579D + 0.20000000298023224D;
            double g = f * f * f;

            //janky calculation that probably isn't right but works
            double zoom = ((-20.0/9 * (SpyZoom.zoom * SpyZoom.zoom)) + (92.0/9 * SpyZoom.zoom));

            g *= zoom;

            double o = getCursorDeltaX() * g;
            double p = getCursorDeltaY() * g;

            setCursorDeltaX(0.0D);
            setCursorDeltaY(0.0D);

            int q = 1;
            if (options.invertYMouse().get()) {
                q = -1;
            }

            SpyZoom.instance.getTutorial().onMouse(o, p);

            player.turn(o, p * (double)q);

            info.cancel();
        }
    }

    @Accessor(value = "xpos")
    public abstract double getCursorDeltaX();

    @Accessor(value = "ypos")
    public abstract double getCursorDeltaY();

    @Accessor(value = "xpos")
    public abstract void setCursorDeltaX(double x);

    @Accessor(value = "ypos")
    public abstract void setCursorDeltaY(double y);
}
