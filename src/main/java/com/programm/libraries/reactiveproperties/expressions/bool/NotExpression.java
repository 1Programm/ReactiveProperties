package com.programm.libraries.reactiveproperties.expressions.bool;

import com.programm.libraries.reactiveproperties.ObservableValue;

public class NotExpression extends BoolExpression {

    private final ObservableValue<Boolean> value;

    public NotExpression(ObservableValue<Boolean> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Boolean get() {
        return !value.get();
    }
}
