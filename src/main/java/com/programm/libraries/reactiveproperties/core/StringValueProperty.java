package com.programm.libraries.reactiveproperties.core;

public class StringValueProperty extends StringProperty {

    private String value;

    public StringValueProperty() {
        this("");
    }

    public StringValueProperty(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    protected void setDirectly(String value) {
        this.value = value;
    }
}
