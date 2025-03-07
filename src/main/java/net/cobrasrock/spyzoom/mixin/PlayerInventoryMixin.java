package net.cobrasrock.spyzoom.mixin;

import net.cobrasrock.spyzoom.SpyZoom;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.joml.Math;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {
	@Shadow @Final public Player player;
	@Shadow public int selected;
	@Inject(at = @At("HEAD"), method = "setSelectedHotbarSlot", cancellable = true)
	private void setSelectedHotbarSlot(int scrollAmount, CallbackInfo info) {
		if (SpyZoom.instance.player == null)
			return;
		if (player.isScoping() && SpyZoom.instance.options.getCameraType().isFirstPerson()) {
			//zooms in/out
			if (SpyZoom.zoom > 0.001f && ((selected == 0 && scrollAmount == 8) || (selected == 8 && scrollAmount == 7) || (scrollAmount < selected && selected != 8))) {
				SpyZoom.zoom *= 9.0f / 10.0f;
			} else if (SpyZoom.zoom < 1.0f && ((selected == 0 && scrollAmount == 1) || (selected == 8 && scrollAmount == 0) || scrollAmount > selected)) {
				SpyZoom.zoom *= 10.0f / 9f;
			}
			//prevents zooming from going OOB
			SpyZoom.zoom = Math.clamp(SpyZoom.zoom, 0.001f, 1.0f);

			info.cancel();
		}
	}
}
