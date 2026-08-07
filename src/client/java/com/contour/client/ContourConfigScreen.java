package com.contour.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ContourConfigScreen extends Screen {

    private final Screen parent;

    private static final int[] COLORS = {
            0xFFFF3232,
            0xFF32FF32,
            0xFF3232FF,
            0xFFFFFF32,
            0xFFFF32FF,
            0xFF32FFFF,
            0xFFFFFFFF
    };

    private static final String[] COLOR_NAMES = {
            "Red", "Green", "Blue", "Yellow", "Pink", "Cyan", "White"
    };

    private int colorIndex = 0;

    public ContourConfigScreen(Screen parent) {
        super(Component.literal("Contour Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i] == ContourConfig.getOutlineColor()) {
                colorIndex = i;
                break;
            }
        }

        this.addRenderableWidget(new RGBSlider(
                this.width / 2 - 100,
                60,
                "Red",
                ContourConfig.red
        ));

        this.addRenderableWidget(new RGBSlider(
                this.width / 2 - 100,
                90,
                "Green",
                ContourConfig.green
        ));

        this.addRenderableWidget(new RGBSlider(
                this.width / 2 - 100,
                120,
                "Blue",
                ContourConfig.blue
        ));

        this.addRenderableWidget(Button.builder(
                Component.literal("Outline: " + (ContourConfig.enabled ? "ON" : "OFF")),
                button -> {
                    ContourConfig.enabled = !ContourConfig.enabled;
                    button.setMessage(Component.literal(
                            "Outline: " + (ContourConfig.enabled ? "ON" : "OFF")
                    ));
                }
        ).pos(this.width / 2 - 100, 170).size(200, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.literal("Color: " + COLOR_NAMES[colorIndex]),
                button -> {
                    colorIndex = (colorIndex + 1) % COLORS.length;

                    int color = COLORS[colorIndex];

                    ContourConfig.red = (color >> 16) & 0xFF;
                    ContourConfig.green = (color >> 8) & 0xFF;
                    ContourConfig.blue = color & 0xFF;

                    button.setMessage(Component.literal(
                            "Color: " + COLOR_NAMES[colorIndex]
                    ));
                }
        ).pos(this.width / 2 - 100, 200).size(200, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.literal("Done"),
                button -> this.onClose()
        ).pos(this.width / 2 - 100, 230).size(200, 20).build());
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}