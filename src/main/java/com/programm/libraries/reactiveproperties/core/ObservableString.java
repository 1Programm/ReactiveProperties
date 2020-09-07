package com.programm.libraries.reactiveproperties.core;

import com.programm.libraries.reactiveproperties.ObservableValue;

public interface ObservableString extends ObservableValue<String> {

    ObservableBool isEmpty();

    ObservableString trim();

    ObservableInt size();

}
