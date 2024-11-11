package net.cobrasrock.spyzoom.mixin;

import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cobrasrock.spyzoom.SpyZoom;

@Mixin(SpyglassItem.class)
public class SpyglassItemMixin {
  
  @Inject(method = "stopUsing", at = @At("HEAD"))
  private void resetZoom(CallbackInfo ci) {
    SpyZoom.zoom = 0.1f;
  }
}
