package com.esiee.java_avance_lot_1.singleton;

public final class Popup {
    private static Popup popup;
    private String TextValue = "";

    private Popup(String TextValue) {
        this.TextValue = TextValue;
    }

    public static Popup getPopup(String TextValue) {
        if(popup == null) {
            popup = new Popup(TextValue);
        }
        return popup;
    }

    public static Popup getPopup() {
        if(popup != null) {
            return popup;
        }
        return null;
    }

    public String getTextValue() {
        return TextValue;
    }

    public void clearPopupValue() {
        popup = null;
    }
}
