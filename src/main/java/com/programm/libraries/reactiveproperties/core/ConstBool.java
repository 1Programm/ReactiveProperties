package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.ChangeListener;

public class ConstBool implements ObservableBool{

    private final boolean value;

    public ConstBool() {
        this(false);
    }

    public ConstBool(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public void addListener(ChangeListener listener) {

    }

    @Override
    public void removeListener(ChangeListener listener) {

    }

    @Override
    public void addWeakListener(ChangeListener listener) {

    }
}
