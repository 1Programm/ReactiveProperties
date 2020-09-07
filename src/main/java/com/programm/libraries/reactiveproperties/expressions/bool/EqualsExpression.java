package com.programm.libraries.reactiveproperties.expressions.bool;

import com.programm.libraries.reactiveproperties.ObservableValue;

public class EqualsExpression <T> extends BoolExpression {

    private final ObservableValue<T> observable;
    private final T value;

    public EqualsExpression(ObservableValue<T> observable, T value) {
        super(observable);
        this.observable = observable;
        this.value = value;
    }

    @Override
    public Boolean get() {
        return observable.get().equals(value);
    }
}
