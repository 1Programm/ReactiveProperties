package com.programm.libraries.reactiveproperties;

public interface SettableValue <T> extends ObservableValue<T>{
    void set(T value);
}
