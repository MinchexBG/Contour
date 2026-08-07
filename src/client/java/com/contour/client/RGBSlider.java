package com.contour.client;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class RGBSlider extends AbstractSliderButton {

    private final String name;

    public RGBSlider(int x, int y, String name, int value) {
        super(x, y, 200, 20, Component.literal(name + ": " + value), value / 255.0);
        this.name = name;
    }

    @Override
    protected void updateMessage() {
        int value = (int) (this.value * 255);
        this.setMessage(Component.literal(name + ": " + value));
    }

    @Override
    protected void applyValue() {
        int value = (int) (this.value * 255);

        switch (name) {
            case "Red" -> ContourConfig.red = value;
            case "Green" -> ContourConfig.green = value;
            case "Blue" -> ContourConfig.blue = value;
        }

        System.out.println(
                "RGB: " + ContourConfig.red + ", "
                        + ContourConfig.green + ", "
                        + ContourConfig.blue
        );
    }}