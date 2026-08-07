package com.contour.client;

import eu.midnightdust.lib.config.MidnightConfig;

public class ContourConfig extends MidnightConfig {

    public static final String CATEGORY = "contour";

    @Entry(category = CATEGORY)
    public static boolean enabled = true;

    @Entry(category = CATEGORY, isSlider = true, min = 0, max = 255)
    public static int red = 255;

    @Entry(category = CATEGORY, isSlider = true, min = 0, max = 255)
    public static int green = 50;

    @Entry(category = CATEGORY, isSlider = true, min = 0, max = 255)
    public static int blue = 50;

    public static int getOutlineColor() {
        return (255 << 24)
                | (red << 16)
                | (green << 8)
                | blue;
    }
}