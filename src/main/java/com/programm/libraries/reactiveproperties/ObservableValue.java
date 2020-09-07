package com.programm.libraries.reactiveproperties;

import com.programm.libraries.reactiveproperties.core.ObservableBool;

public interface ObservableValue <T> {

    T get();

    void addListener(ChangeListener listener);

    void removeListener(ChangeListener listener);

    void addWeakListener(ChangeListener listener);

    default ObservableBool equalTo(T value){
        return null;
    }

}
