package net.cobrasrock.spyzoom;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Unique;

public class SpyZoom implements ModInitializer {
	public static float zoom = 0.1f;

	public static Minecraft instance = Minecraft.getInstance();

	@Override
	public void onInitialize() {}
}
