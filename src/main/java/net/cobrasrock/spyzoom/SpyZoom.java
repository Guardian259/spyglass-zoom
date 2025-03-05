package net.cobrasrock.spyzoom;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;

public class SpyZoom implements ModInitializer {
	public static float zoom = 0.1f;

	public static Minecraft instance = Minecraft.getInstance();

	@Override
	public void onInitialize() {}
}
