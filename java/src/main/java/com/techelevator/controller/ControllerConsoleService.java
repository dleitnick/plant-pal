package com.techelevator.controller;

import java.awt.*;

public class ControllerConsoleService {

    private String controllerInUse;
    private Color color = new Color(204, 51, 102);

    public ControllerConsoleService(String controllerInUse) {
        this.controllerInUse = controllerInUse;
    }

    public ControllerConsoleService(String controllerInUse, Color color) {
        this.controllerInUse = controllerInUse;
        this.color = color;
    }

    public void log(String message) {
        System.out.println(stringColor(controllerInUse + ": ", color.getRed(), color.getGreen(), color.getBlue(), "bold") + stringColor(message, 204, 51, 255));
    }

    public void err(String message) {
        System.out.println(stringColor(controllerInUse + ": ", color.getRed(), color.getGreen(), color.getBlue(), "bold") + stringColor(message, 204, 0, 0));
    }

    private String stringStyle(String str, String style) {
        StringBuilder styler = new StringBuilder();
        String[] styles = style.split(",");
        for (String styletype : styles) {
            switch (styletype) {
                case "italic":
                    styler.append(";3");
                    break;
                case "underline":
                    styler.append(";4");
                    break;
                case "bold":
                    styler.append(";1");
                    break;
            }
        }
        return String.format("\u001b[0%sm%s\u001b[0m", styler, str);
    }

    private String stringColor(String str, int red, int green, int blue) {
        return String.format("\u001b[38;2;%s;%s;%sm%s\u001b[0m", red, green, blue, str);
    }

    private String stringColor(String str, int red, int green, int blue, String style) {
        StringBuilder styler = new StringBuilder();
        String[] styles = style.split(",");
        for (String styletype : styles) {
            switch (styletype) {
                case "italic":
                    styler.append(";3");
                    break;
                case "underline":
                    styler.append(";4");
                    break;
                case "bold":
                    styler.append(";1");
                    break;
            }
        }
        return String.format("\u001b[38;2;%s;%s;%s%sm%s\u001b[0m", red, green, blue, styler.toString(), str);
    }

    private String backgroundColor(int red, int green, int blue) {
        return String.format("\u001b[48;2;%s;%s;%sm", red, green, blue); // The 48 means background color
    }

}
