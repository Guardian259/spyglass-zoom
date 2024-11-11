package net.cobrasrock.spyzoom.mixin;

import net.cobrasrock.spyzoom.SpyZoom;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.joml.Math;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {
	@Shadow @Final public Player player;
	
	@Inject(at = @At("HEAD"), method = "swapPaint", cancellable = true)
	private void scrollInHotbar(double scrollAmount, CallbackInfo info) {
		if (SpyZoom.instance.player == null)
			return;
		if (player.isScoping() && SpyZoom.instance.options.getCameraType().isFirstPerson()) {
			//zooms in/out
			if (scrollAmount > 0.0D && SpyZoom.zoom > 0.001f) {
				SpyZoom.zoom *= 9.0f / 10.0f;
			}
			if (scrollAmount < 0.0D && SpyZoom.zoom < 1.0f) {
				SpyZoom.zoom *= 10.0f / 9f;
			}
			//prevents zooming from going OOB
			SpyZoom.zoom = Math.clamp(SpyZoom.zoom, 0.001f, 1.0f);

			info.cancel();
		}
	}
}
