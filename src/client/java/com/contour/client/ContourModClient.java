package com.contour.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ContourModClient implements ClientModInitializer {

	public static KeyMapping toggleKey;

	@Override
	public void onInitializeClient() {
		KeyMapping.Category contourCategory = KeyMapping.Category.register(
				Identifier.fromNamespaceAndPath("contour", "keys")
		);

		toggleKey = new KeyMapping(
				"key.contour.toggle",
				GLFW.GLFW_KEY_P,
				contourCategory
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.consumeClick()) {
				ContourConfig.enabled = !ContourConfig.enabled;
			}
		});
	}
}