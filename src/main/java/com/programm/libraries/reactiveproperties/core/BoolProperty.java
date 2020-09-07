package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.AbstractProperty;

public abstract class BoolProperty extends AbstractProperty<Boolean> implements ObservableBool {

    public void invert(){
        set(!get());
    }

}
